package com.vccorp.bigdata.adsmobile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quanpv on 5/23/16.
 */
public class FileUtil {

    public static ArrayList<String> getListFile(String folderPath) {

        ArrayList<String> listFile = new ArrayList<String>();

        File file = new File(folderPath);

        File[] arrFile = file.listFiles();

        for (int i = 0; i < arrFile.length; i++) {
            if (arrFile[i].isFile()) {
                listFile.add(arrFile[i].getPath().toString());
            }
        }
        return listFile;
    }

    public static ArrayList<String> readFile(String file) {

        ArrayList<String> linesList = new ArrayList<String>();

        try {
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String line;

            while ((line = br.readLine()) != null) {
                linesList.add(line);
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Error while reading file: " + e + " cause by " + e);
        }
        return linesList;
    }

    public static boolean writeToFile(ArrayList<String> lines, String fileName) {

        BufferedWriter output = null;
        try {
            File file = new File(fileName);
            output = new BufferedWriter(new FileWriter(file));
            for (String line: lines){
                output.write(line + System.getProperty("line.separator"));
            }
            output.flush();
            output.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            try{
                if ( output != null ) {
                    output.close();
                }
            }catch (IOException ioe){
                System.out.println( "Error write to file " + fileName + " cause by "  + ioe);
            }
        }
        return false;
    }

}
