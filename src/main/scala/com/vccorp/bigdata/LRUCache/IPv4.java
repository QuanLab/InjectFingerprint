package com.vccorp.bigdata.LRUCache;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class IPv4 {

    public static void main(String[] args) throws UnknownHostException{

        String addr="192.168.25.38/11";
        SubnetUtils subnetUtils = new SubnetUtils(addr);
        System.out.println(subnetUtils.getInfo().toString());

        System.out.println("Netmask:            " + subnetUtils.getInfo().getNetmask());
        System.out.println("Address:            " + subnetUtils.getInfo().getAddress());
        System.out.println("NetworkAddress:     " + subnetUtils.getInfo().getNetworkAddress());
        System.out.println("BroadcastAddress:   " + subnetUtils.getInfo().getBroadcastAddress());
        System.out.println("LowAddress:         " + subnetUtils.getInfo().getLowAddress());
        System.out.println("HighAddress:        " + subnetUtils.getInfo().getHighAddress());
    }
}