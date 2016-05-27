package com.vccorp.bigdata.ipparser;

import com.mysql.jdbc.PreparedStatement;
//import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;


public class DBHelper {

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

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
            String sqlQueryWP = "INSERT INTO cpi_matcher.clickWp(id, appId, ipAddress, deviceId, osVersion, modelName, brandName) " +
                    "value (?, ?, ?, ?, ?, ?, ?);";

            try {

                connectDB();

                if (osType==1){
                    pst = (PreparedStatement)conn.prepareStatement(sqlQueryAndroid);
                } else if(osType == 2){
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

}
