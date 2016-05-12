package com.vccorp.bigdata.server;

import com.vccorp.bigdata.adsmobile.UserAgentParser;
import org.apache.thrift.TException;

/**
 * Created by quanpv on 5/12/16.
 */

public class UserAgentHandle implements UserAgentService.Iface{

    public String multiply(String n1) throws TException {

        System.out.println("Parsing user agent starting.........");

        UserAgentParser userAgentParser = new UserAgentParser(n1);

        String device_id= userAgentParser.get_device_id();
        String  device_model=userAgentParser.get_model_name();
        String  device_brand=userAgentParser.get_brand_name();
        String  device_os=userAgentParser.get_device_os();
        String  os_version=userAgentParser.get_os_version();
        String  resolution_height=String.valueOf(userAgentParser.get_resolution_height());
        String  resolution_width=String.valueOf(userAgentParser.get_resolution_width());
        String is_wiless_device = String.valueOf(userAgentParser.is_wiless_device());

        String value =device_id + "\t" + device_model+ "\t" + device_model+ "\t" + device_brand+ "\t" + device_os+ "\t" + os_version+ "\t" + resolution_height+ "\t" + resolution_width+ "\t" + is_wiless_device;

        System.out.println("Parsing user agent completed!");
        return value;
    }
}
