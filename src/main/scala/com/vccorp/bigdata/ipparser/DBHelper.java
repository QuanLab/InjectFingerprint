package com.vccorp.bigdata.ipparser;

import com.mysql.jdbc.PreparedStatement;
import com.sun.corba.se.impl.ior.ObjectAdapterIdArray;
import com.sun.corba.se.impl.naming.cosnaming.NamingUtils;
//import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Random;
import java.util.Vector;


public class DBHelper {

    private static final int ANDROID = 1;
    private static final int IOS = 2;
    private static final int WINDOWS_PHONE = 3;

//    final static Logger logger = Logger.getLogger(DBHelper.class);

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/cpi_matcher";

    static final String USER = "root";
    static final String PASS = "mothaiba";

    static Connection conn;



    public static void connectDB() {

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected Success!");
//            logger.info("Connetion " + DB_URL + " has established!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void closeConnection(){
        if(conn!=null){

            try{
                conn.close();

            } catch (SQLException e){
                System.out.println("Cannot close connection because: " + e);
            }
        }
    }


    public static void batchInsertRecordsIntoTableClick(Vector batchRecord, int osType){

        try{

            PreparedStatement pst = null;
            String sqlQueryAndroid = "INSERT INTO cpi_matcher.clickAndroid(id, appId, ipAddress, deviceId, osVersion, modelName, brandName) " +
                    "value (?, ?, ?, ?, ?, ?, ?);";
            String sqlQueryIOS = "INSERT INTO cpi_matcher.clickIOS(id, appId, ipAddress, deviceId, osVersion, modelName, brandName) " +
                    "value (?, ?, ?, ?, ?, ?, ?);";
            String sqlQueryWP = "INSERT INTO cpi_matcher.clickWP(id, appId, ipAddress, deviceId, osVersion, modelName, brandName) " +
                    "value (?, ?, ?, ?, ?, ?, ?);";

            try {

                connectDB();

                if (osType==ANDROID){
                    pst = (PreparedStatement)conn.prepareStatement(sqlQueryAndroid);
                } else if(osType == IOS){
                    pst = (PreparedStatement)conn.prepareStatement(sqlQueryIOS);
                } else {
                    pst = (PreparedStatement)conn.prepareStatement(sqlQueryWP);
                }
                conn.setAutoCommit(false);

                for(int i = 0; i < batchRecord.size(); i++){
                    Vector record = (Vector)batchRecord.get(i);

                    pst.setString(1, String.valueOf(generateRandom(18)));
                    pst.setString(2, (String)record.get(0));
                    pst.setString(3, (String)record.get(1));
                    pst.setString(4, (String)record.get(2));
                    pst.setString(5, (String)record.get(3));
                    pst.setString(6, (String)record.get(4));
                    pst.setString(7, (String)record.get(5));
                    pst.addBatch();

                }
                pst.executeBatch();
                conn.commit();
                System.out.println("Completed batch insertion to table!");

            } catch (SQLException e) {
                System.out.println("Error insert batch:\n" + e);
                conn.rollback();

            } finally {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }

        }catch (SQLException sqlEx){
            System.out.println("Insert batch failed!" + sqlEx);
        }

    }

    public static void batchInsertInstall(String installRecord, Vector batchRecordClick){
        try{

            PreparedStatement pst = null;
            String sqlQueryAndroid = "INSERT INTO cpi_matcher.install_clicks(install, click_id) value (?, ?);";

            try {

                connectDB();
                pst = (PreparedStatement)conn.prepareStatement(sqlQueryAndroid);

                conn.setAutoCommit(false);

                for(int i = 0; i < batchRecordClick.size(); i++){


                    pst.setString(1, installRecord);
                    pst.setString(2, (String)batchRecordClick.get(i));
                    pst.addBatch();

                }
                pst.executeBatch();
                conn.commit();
                System.out.println("Completed batch insertion to table!");

            } catch (SQLException e) {
                System.out.println("Error insert batch:\n" + e);
                conn.rollback();

            } finally {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }

        }catch (SQLException sqlEx){
            System.out.println("Insert batch failed!" + sqlEx);
        }
    }


    public static Vector getClickRecords(String appId, int osType, String osVersion, String modelName, String brandName){

        connectDB();

        String tableName;

        if(osType == ANDROID){
            tableName= "cpi_matcher.clickAndroid";

        } else if (osType==IOS){
            tableName= "cpi_matcher.clickIOS";
        }else if(osType==WINDOWS_PHONE){
            tableName= "cpi_matcher.clickWP";
        } else {
            return null;
        }

        Vector result = new Vector();

        String sqlQuery = "SELECT * FROM  " + tableName +
                " WHERE (appId = ? or ? is null)" +
                " AND (osVersion = ? or ? is null)" +
                " AND (modelName = ? or ? is null)" +
                " AND (brandName = ? or ? is null)";

        PreparedStatement pst;
        ResultSet rs;

        try{
            pst  = (PreparedStatement) conn.prepareStatement(sqlQuery);

            pst.setString(1, appId);
            pst.setString(2, appId);
            pst.setString(3, osVersion);
            pst.setString(4, osVersion);
            pst.setString(5, modelName);
            pst.setString(6, modelName);
            pst.setString(7, brandName);
            pst.setString(8, brandName);

            rs = pst.executeQuery();

            Vector click;

            while (rs.next()){

                click = new Vector();

                click.add(rs.getString("id"));
                click.add(rs.getString("appId"));
                click.add(rs.getString("ipAddress"));
                click.add(rs.getString("osVersion"));
                click.add(rs.getString("modelName"));
                click.add(rs.getString("brandName"));

                result.add(click);
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
        closeConnection();
        return result;

    }

    public static void main(String [] args){

        //1;4.2.2;samsung;GT-S7580;samsung;j7e3gxx;LMY48B;haitacbongdem.vn.msh;VIETTEL;123.20.174.162;Tiếng Việt;1461646010;samsung/j7e3gxx/j7e3g:5.1.1/LMY48B/J700HXXU1APA2:user/release-keys;353236070077972;353236070077972;1
        Vector result = getClickRecords("vn.sohagame.sg120", 2, "9.3.1", "iPad", null);

        String ipAddress;

        if(result!=null){

            Vector record;
            for(int i=0; i<result.size(); i++){
                record = (Vector)result.get(i);
                System.out.println(record);
            }
            System.out.println(result.size());
        } else {
            System.out.println("Not found result");
        }
    }


     /*
      * generate unique id for each record of click in database
     */

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

}
