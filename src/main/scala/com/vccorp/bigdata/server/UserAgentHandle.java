package com.vccorp.bigdata.server;

import com.vccorp.bigdata.adsmobile.UserAgentParser;
import org.apache.thrift.TException;


public class UserAgentHandle implements UserAgentService.Iface{


    public String multiply(String userAgentString) throws TException {

        System.out.println("Parsing user agent starting.........");

        String userAgenParsed = MatchingCPIServer.lruCache.get(userAgentString);

        if (userAgenParsed!=null){
            return userAgenParsed;
        }
        UserAgentParser userAgentParser = new UserAgentParser(userAgentString);

        userAgenParsed = userAgentParser.getUserAgentParsedToString();

        MatchingCPIServer.lruCache.set(userAgentString, userAgenParsed);

        return userAgenParsed;
    }
}
