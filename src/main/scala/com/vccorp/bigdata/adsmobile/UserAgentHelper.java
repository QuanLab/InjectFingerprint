package com.vccorp.bigdata.adsmobile;

import net.sf.uadetector.OperatingSystem;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

public class UserAgentHelper {

    public static String getOsVersion(String userAgentString) {

        UserAgentStringParser parser = UADetectorServiceFactory.getCachingAndUpdatingParser();
        ReadableUserAgent readableUserAgent= parser.parse(userAgentString);

        OperatingSystem os = readableUserAgent.getOperatingSystem();

        return os.getVersionNumber().toVersionString();
    }

    public static void main(final String[] args) throws Exception {


        String uaString = "Opera/9.80 (Android; Opera Mini/11.0.1912/37.8233; U; vi) Presto/2.12.423 Version/12.16\thttp://mangago.vn/landing";
        String version=  getOsVersion(uaString);

        System.out.println("=====" + version + "========");

        if(version.trim().equals("")){
            System.out.println("isempty");
        }
    }
}
