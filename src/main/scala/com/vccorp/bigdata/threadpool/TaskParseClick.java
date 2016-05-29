package com.vccorp.bigdata.threadpool;

import com.vccorp.bigdata.adsmobile.AnalystClick;

/**
 * Created by quanpv on 5/28/16.
 */
public class TaskParseClick implements Runnable{

    private String fileName;

    public TaskParseClick(String fileName) {
        this.fileName = fileName;
    }

    public void run() {
        try {
            System.out.println("Start parsing " + fileName);
            AnalystClick.writeClickRecordToDB();
            System.out.println("Complete parse " + fileName);
        } catch (Exception e){
            System.out.println( this.getClass() + "     " + fileName + " " + e);
        }
    }


}
