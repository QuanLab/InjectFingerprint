package com.vccorp.bigdata.adsmobile;

import com.vccorp.bigdata.client.MatchingCPIClient;
import com.vccorp.bigdata.ipparser.DBHelper;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Created by quanpv on 5/24/16.
 */
public class AnalystClick extends DBHelper {

    private static final int MAX_SIZE_BATCH = 1000;
    private static final String ANDROID = "Android", IOS = "iOS", WINDOWS_PHONE = "Windows Phone OS";

    public AnalystClick() {

    }

    public static void main(String[] args) {

        writeClickRecordToDB();

    }

    /**
     * filter mobile install game from data (open, close, update, install)
     * and save data to file
     **/

    public static void filterClickFromRawData() {

        String filePath = "/home/quanpv/Database/LogInstall/part-";
        String fileName;
        long countLines = 0;
        int countFiles = 0;
        ArrayList<String> lines;
        ArrayList<String> lineInstall = new ArrayList<String>(10000);

        ArrayList<String> listFile = FileUtil.getListFile("/home/quanpv/Database/3.158/");

        for (String file : listFile) {

            lines = FileUtil.readFile(file);

            for (String line : lines) {

                if (isMobileInstall(line)) {

                    lineInstall.add(line);
                    countLines++;

                    if (countLines >= 10000) {
                        System.out.println("Write to file...........................");
                        fileName = filePath + String.format("%05d", countFiles);
                        FileUtil.writeToFile(lineInstall, fileName);
                        countFiles++;
                        countLines = 0;
                        lineInstall = new ArrayList<String>(10000);
                    }

                }
            }
        }

        fileName = filePath + String.format("%05d", countFiles);
        FileUtil.writeToFile(lineInstall, fileName);

        System.out.println(countLines);
    }


    public static void writeClickRecordToDB() {

        int countRecordAndroid = 0;
        int countRecordiOS = 0;
        int countRecordWp = 0;

        ArrayList<String> lines;
        Vector record = new Vector();

        Vector batchRecordsAndroid = new Vector(MAX_SIZE_BATCH);
        Vector batchRecordsiOS = new Vector(MAX_SIZE_BATCH);
        Vector batchRecordsWp = new Vector(MAX_SIZE_BATCH);

        String userAgentParsed = null;
        String[] splits = null;
        String appId, ipAddress, devideId, osType = null, osVersion, modelName, brandName;

        ArrayList<String> listFile = FileUtil.getListFile("/home/quanpv/Database/LogClick/");

        for (String file : listFile) {

            lines = FileUtil.readFile(file);

            for (String line : lines) {

                MobileClickParser mobileClickParser = new MobileClickParser(line);

                appId = mobileClickParser.getGameId();
                ipAddress = mobileClickParser.getIpAddress();

                record.addElement(appId);
                record.addElement(ipAddress);

                try {
                    userAgentParsed = MatchingCPIClient.getUserAgentParsed(mobileClickParser.getUserAgent());
                    splits = userAgentParsed.split("\t");

                    try{
                        devideId = splits[0];
                    } catch(Exception e){
                        devideId = "-1";
                    }

                    try{
                        osType = splits[1];
                    } catch(Exception e){
                        osType = "-1";
                    }

                    try{
                        osVersion = splits[2];
                    } catch(Exception e){
                        osVersion = "-1";
                    }

                    try{
                        modelName = splits[3];
                    } catch(Exception e){
                        modelName = "-1";
                    }
                    try{
                        brandName = splits[4];
                    } catch(Exception e){
                        brandName = "-1";
                    }

                    record.addElement(devideId);
//                    record.addElement(osType);
                    record.addElement(osVersion);
                    record.addElement(modelName);
                    record.addElement(brandName);

                } catch (Exception e) {
                    System.out.println("writeClickRecordToDB" + e);
                }

                if (appId != null) {
                    if (osType.equals(ANDROID)) {

                        batchRecordsAndroid.add(record);
                        countRecordAndroid++;

                    } else if (osType.equals(IOS)) {
                        batchRecordsiOS.add(record);
                        countRecordiOS++;

                    } else {
                        batchRecordsWp.add(record);
                        countRecordWp++;
                    }
                }
                record = new Vector();

                if (countRecordAndroid >= MAX_SIZE_BATCH) {
                    System.out.println("Insert batch record to database Android.....................");
//                    printBatch(batchRecordsAndroid);
                    DBHelper.batchInsertRecordsIntoTableClick(batchRecordsAndroid, 1);
                    countRecordAndroid = 0;
                    batchRecordsAndroid = new Vector(MAX_SIZE_BATCH);
                }

                if (countRecordiOS >= MAX_SIZE_BATCH) {
                    System.out.println("Insert batch record to database iOS..........................");
//                    printBatch(batchRecordsiOS);
                    DBHelper.batchInsertRecordsIntoTableClick(batchRecordsiOS, 2);
                    countRecordiOS = 0;
                    batchRecordsiOS = new Vector(MAX_SIZE_BATCH);
                }

                if (countRecordWp >= MAX_SIZE_BATCH) {
                    System.out.println("Insert batch record to database windows phone OS.............");
//                    printBatch(batchRecordsWp);
                    DBHelper.batchInsertRecordsIntoTableClick(batchRecordsWp, 3);
                    countRecordWp = 0;
                    batchRecordsWp = new Vector(MAX_SIZE_BATCH);
                }

            }
        }

        DBHelper.batchInsertRecordsIntoTableClick(batchRecordsWp, 1);
        DBHelper.batchInsertRecordsIntoTableClick(batchRecordsWp, 2);
        DBHelper.batchInsertRecordsIntoTableClick(batchRecordsWp, 3);
    }

    /**
     * check if log is an installation of application
     **/

    public static boolean isMobileInstall(String click) {

        LogFingerPrint logFingerPrint = new LogFingerPrint(click);
        FPS fps = new FPS(logFingerPrint.getFps());

        if (logFingerPrint.getSrc() == 3 && fps.getAction() == 1) {

            return true;
        }

        return false;
    }


    public static void printBatch(Vector batchRecords) {

        for (int i = 0; i < batchRecords.size(); i++) {
            Vector record = (Vector) batchRecords.get(i);
            System.out.print("--" + record.get(0) + "\t");
            System.out.print("--" + record.get(1) + "\t");
            System.out.print("--" + record.get(2) + "\t");
            System.out.print("--" + record.get(3) + "\t");
            System.out.print("--" + record.get(4) + "\t");
            System.out.print("--" + record.get(5) + "\t");
            System.out.print("--" + record.get(6) + "\n");
        }
    }

}
