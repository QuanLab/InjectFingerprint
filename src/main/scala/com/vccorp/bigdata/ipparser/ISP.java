package com.vccorp.bigdata.ipparser;

import com.mysql.jdbc.PreparedStatement;
import org.apache.commons.net.util.SubnetUtils;

import java.sql.ResultSet;
import java.util.Vector;


public class ISP {

    public static Vector vectorCarrier   = getDataCarrier();

    public ISP(){

    }


    public String getISP(String ipAddress){

        SubnetUtils subnetUtils;

        for(int i = 0; i< vectorCarrier.size(); i++){

            Vector record = (Vector) vectorCarrier.get(i);
            String cidr = (String)record.get(2);

            subnetUtils = new SubnetUtils(cidr);

            if(subnetUtils.getInfo().isInRange(ipAddress)){
                System.out.println(vectorCarrier.get(i));
                System.out.println(subnetUtils.getInfo().getLowAddress() + " -----> " + subnetUtils.getInfo().getHighAddress());
//                System.out.println("Found: " + record.get(1).toString());
                return record.get(1).toString();
            };

        }
        return null;
    }

    public boolean isSameSubnet(String ip1, String ip2){

        try{
            String subnet =  getSubnet(ip1);
            SubnetUtils subnetUtils = new SubnetUtils(subnet);
            return subnetUtils.getInfo().isInRange(ip2);

        } catch (Exception e){
            System.out.println(e);
            return  false;
        }
    }

    public String getSubnet(String ipAddress){

        SubnetUtils subnetUtils;

        for(int i = 0; i< vectorCarrier.size(); i++){

            Vector record = (Vector) vectorCarrier.get(i);

            String subnet = (String)record.get(2);

            subnetUtils = new SubnetUtils(subnet);

            if(subnetUtils.getInfo().isInRange(ipAddress)){
                return record.get(2).toString();
            };

        }
        return null;
    }

    public static Vector getDataCarrier(){

        DBHelper.connectDB();

        String sqlQuery = "SELECT * FROM carrier;";

        Vector results = new Vector();
        Vector vectorRecord;

        try{
            ResultSet rs;
            PreparedStatement ps = (PreparedStatement)DBHelper.conn.prepareStatement(sqlQuery);
            rs = ps.executeQuery();

            while(rs.next()){
                vectorRecord = new Vector();
                vectorRecord.addElement(rs.getString(1));
                vectorRecord.addElement(rs.getString(2));
                vectorRecord.addElement(rs.getString(3));
                results.add(vectorRecord);
            }

        }catch (Exception e){
            System.out.println("Cannot get data ISP: " + e);
        }

        DBHelper.closeConnection();

        return results;
    }

}
