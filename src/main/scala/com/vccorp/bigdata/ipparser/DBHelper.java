package com.vccorp.bigdata.ipparser;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBHelper {

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

}
