package com.instance.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关于字符
 * @author wxy
 */
public class StringUtil {

    private static Pattern CHINESE = Pattern.compile("[\u4e00-\u9fa5]");
    private static Pattern LETTER =Pattern.compile("[a-zA-Z]");

    /**
     * 判断字符是否有汉字
     * @param str 要判断的字符串
     * @return ture or false
     */
    public static boolean isContainChinese(String str) {
        Matcher m = CHINESE.matcher(str);
        return m.find();
    }

    /**
     * 判断字符是否有汉字
     * @param str 要判断的字符串
     * @return ture or false
     */
    public static boolean isLetter(String str) {
        Matcher m = LETTER.matcher(str);
        return m.find();
    }

    /**
     *
     */
    public static void getLoalhostIP(){
        try {
            Enumeration<?> enumeration= NetworkInterface.getNetworkInterfaces();
            InetAddress ip=null;
            while(enumeration.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) enumeration.nextElement();
                Enumeration<?> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    System.out.println("服务地址:" + ip.getHostName());
                    if (ip != null && ip instanceof Inet4Address){
                        String ip1=ip.getHostAddress();
                        System.out.println("本机所有的IP地址:"+ip1);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    /**
     *  将map转换为XML
     * @param map 转换map
     * @return string
     */
    public static String createXML(Map<String, Object> map) {
        String xml = "<xml>";
        Set<String> set = map.keySet();
        Iterator<String> i = set.iterator();
        while (i.hasNext()) {
            String str = i.next();
            xml += "<" + str + ">" + "<![CDATA[" + map.get(str) + "]]>" + "</" + str + ">";
        }
        xml += "</xml>";
        return xml;
    }
}
