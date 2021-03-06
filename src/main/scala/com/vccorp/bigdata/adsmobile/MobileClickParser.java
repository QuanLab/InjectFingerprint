package com.vccorp.bigdata.adsmobile;

/**
 * Created by quanpv on 5/23/16.
 */
public class MobileClickParser {

    private String mobileClick;
    private String[] splits;

    public MobileClickParser(String mobileClick){

        this.mobileClick = mobileClick;

        if(this.mobileClick.length()>1) {
            try{
                splits = mobileClick.split("\t");
            }catch (Exception e){
                System.out.println("Cannot split click " + e);
            }
        }
    }

    //demo click:
    //2016-04-26 09:05:59	2016-04-26 09:05:59	1961636359245984742		14.169.109.230	e0f9af1e-f29c-57c3-459d-e25ec2fabfb3	-1	-1	-1	-1	-1	-1	Mozilla/5.0 (Linux; U; Android 4.1.2; vi-vn; Thunderbird Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30	http://ngokhong.sohagame.vn/download	480x854	Null	google,1,shg_NKTK_And_download,,1ef7562e647d4d01773fe1f1fa522f6a,vn.shg.ngokhong	adn_mob

    public String getUserAgent() {
        try {
            return splits[12];
        }catch (Exception e){
            return null;
        }
    }

    public String getRequestTime() {
        try {
            return splits[0];
        }catch (Exception e){
            return null;
        }
    }


    public String getCookieCreate() {
        try {
            return splits[1];
        }catch (Exception e){
            return null;
        }
    }


    public String getIpAddress() {
        try {
            return splits[4];
        }catch (Exception e){
            return null;
        }
    }

    public String getGameId() {
        try {
            String[] temp = splits[16].split(",");
            return temp[5].trim();
        } catch (Exception e) {
            return null;
        }
    }

}
