package com.vccorp.bigdata.adsmobile;

import com.vccorp.bigdata.LRUCache.LRUCache;
import com.vccorp.bigdata.ipparser.DBHelper;
import com.vccorp.bigdata.ipparser.ISP;
import com.vccorp.bigdata.threadpool.TaskParseClick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by quanpv on 5/24/16.
 */
public class AnalystClick extends DBHelper {

    private static final int MAX_SIZE_BATCH = 1000;
    private static final String ANDROID = "Android", IOS = "iOS", WINDOWS_PHONE = "Windows Phone OS";

    private static LRUCache lruCache = new LRUCache(10000);

    public AnalystClick() {

    }

    public static void main(String[] args) {

//        writeClickRecordToDB();
//        filterClickFromRawData();

        int cnt = 0;

        ArrayList<String> listFile = FileUtil.getListFile("/home/quanpv/Database/Install/");

        for (String file : listFile) {

            ArrayList<String> installs = FileUtil.readFile(file);

            for (String install : installs) {

                DBHelper.batchInsertInstall(install, getClickRelevant(install));
            }
            System.out.println(cnt);
        }
        System.out.println(cnt);
    }


    /**
     * filter mobile install game from data (open, close, update, install)
     * and save data to file
     **/

    public static void filterClickFromRawData() {

        String fileName;
        ArrayList<String> lines;
        ArrayList<String> linesInstall = new ArrayList<String>();

        ArrayList<String> listFile = FileUtil.getListFile("/home/quanpv/Database/3.158/");

        for (String file : listFile) {

            lines = FileUtil.readFile(file);

            for (String line : lines) {

                if (isMobileInstall(line)) {
                    linesInstall.add(line);
                }
            }
            fileName = file.replace("3.158", "Install");
            System.out.println("Write to file" + fileName);

            FileUtil.writeToFile(linesInstall, fileName);
            linesInstall = new ArrayList<String>();
        }
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

        Collections.sort(listFile);

        for (String file : listFile) {

            System.out.println(file);

            lines = FileUtil.readFile(file);

            for (String line : lines) {

                MobileClickParser mobileClickParser = new MobileClickParser(line);

                appId = mobileClickParser.getGameId();
                ipAddress = mobileClickParser.getIpAddress();

                record.addElement(appId);
                record.addElement(ipAddress);

                try {
                    userAgentParsed = getUserAgentParsedFromCache(mobileClickParser.getUserAgent());
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
        if (logFingerPrint.getSrc() == 3 && fps.getAction() == -1) {
            return true;
        }
        return false;
    }

    private static synchronized String getUserAgentParsedFromCache(String userAgentString) {

//        System.out.println("Start parse user agent string");
        String userAgenParsed = null;

        try {
            userAgenParsed = lruCache.get(userAgentString);
        } catch (NullPointerException npe) {
            System.out.println("getUserAgentParsedFromCache" + npe);
        }

        if (userAgenParsed != null) {
            System.out.println("Found in cache");
            return userAgenParsed;
        }
        userAgenParsed = UserAgentParser.getUserAgentString(userAgentString);
        lruCache.set(userAgentString, userAgenParsed);
        return userAgenParsed;
    }


    public static Vector getClickRelevant (String logInstall) {

        Vector clickRelevant = new Vector();

        LogFingerPrint logFingerPrint = new LogFingerPrint(logInstall);
        FPS fps = new FPS(logFingerPrint.getFps());

        String appId = fps.getApp_id();
        int osType = fps.getOs_type();
        String osVersion = fps.getOs_version();
        String modelName = fps.getDevice_model();
        String ipInstall = fps.getIp_address();

        Vector result = getClickRecords(appId, osType, osVersion, modelName, null);

        String ipClick;

        if(result!=null){

            Vector record;
            for(int i=0; i<result.size(); i++){

                record = (Vector)result.get(i);
                ipClick = (String)record.get(2);

                if(new ISP().isSameSubnet(ipClick, ipInstall)){
                    clickRelevant.add((String)record.get(0));
                }

            }
        } else {
            System.out.println("Not found result");
        }
        return clickRelevant;
    }


}
