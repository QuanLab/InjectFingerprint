package com.vccorp.bigdata.ipparser;

import org.apache.commons.net.util.SubnetUtils;

import java.util.ArrayList;

/**
 * Created by quanpv on 5/17/16.
 */
public class IpParser {

    public static void main(String [] args){

//        String carrier =  new ISP().getISP("118.70.123.130");
//
//        System.out.println(carrier);
//
//        SubnetUtils subnetUtils = new SubnetUtils("118.68.0.0/14");
//        boolean b = subnetUtils.getInfo().isInRange("118.70.150.254");
        boolean b = new ISP().isSameSubnet("118.70.123.130", "118.70.150.254");
        System.out.println(b);
    }
}
