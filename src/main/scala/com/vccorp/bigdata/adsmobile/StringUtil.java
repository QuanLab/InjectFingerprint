package com.vccorp.bigdata.adsmobile;

/**
 * Created by quanpv on 5/24/16.
 */
public class StringUtil {

    public static String rightPad(String s, int length) {
        StringBuffer sb = new StringBuffer(s);
        for (int i = length - s.length(); i > 0; i--)
            sb.append(" ");
        return sb.toString();
    }


    public static String leftPad(String s, int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = length - s.length(); i > 0; i--)
            sb.append(" ");
        sb.append(s);
        return sb.toString();
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    public static String toHexString(byte[] buf) {
        return toHexString(buf, null, Integer.MAX_VALUE);
    }


    public static String toHexString(byte[] buf, String sep, int lineLen) {
        if (buf == null)
            return null;
        if (lineLen <= 0)
            lineLen = Integer.MAX_VALUE;
        StringBuffer res = new StringBuffer(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            int b = buf[i];
            res.append(HEX_DIGITS[(b >> 4 & 0xF)]);
            res.append(HEX_DIGITS[(b & 0xF)]);
            if ((i > 0) && (i % lineLen == 0)) {
                res.append('\n');
            } else if ((sep != null) && (i < lineLen - 1))
                res.append(sep);
        }
        return res.toString();
    }


    public static byte[] fromHexString(String text) {
        text = text.trim();
        if (text.length() % 2 != 0)
            text = "0" + text;
        int resLen = text.length() / 2;

        byte[] res = new byte[resLen];
        for (int i = 0; i < resLen; i++) {
            int j = i << 1;
            int hiNibble = charToNibble(text.charAt(j));
            int loNibble = charToNibble(text.charAt(j + 1));
            if ((loNibble == -1) || (hiNibble == -1))
                return null;
            res[i] = ((byte) (hiNibble << 4 | loNibble));
        }
        return res;
    }

    private static final int charToNibble(char c) {
        if ((c >= '0') && (c <= '9'))
            return c - '0';
        if ((c >= 'a') && (c <= 'f'))
            return 10 + (c - 'a');
        if ((c >= 'A') && (c <= 'F')) {
            return 10 + (c - 'A');
        }
        return -1;
    }


    public static boolean isEmpty(String str) {
        return (str == null) || (str.equals(""));
    }


    public static long longStr(String value) {
        if (value.length() >= 1) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.println("Cannot parse string "  + value +  " to long" + e);
            }
        }
        return 0L;
    }

    public static int intStr(String value) {
        if (value.length() >= 1) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
               System.out.println("Cannot parse string "  + value +  " to int" + e);
            }
        }
        return 0;
    }

    public static void main(String [] args){
        System.out.println(intStr("2"));
    }

}
