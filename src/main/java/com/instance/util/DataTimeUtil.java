package com.instance.util;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 关于时间的公共方法集
 * @author wxy
 */
public class DataTimeUtil {

    /**
     * 获取当前周周一时间
     * @return string
     */
    private static String getWeekMon(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.DATE, -1);
        // 获取本周一的日期
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return df.format(cal.getTime());
    }

    /**
     * 获取当前周周天时间
     * @return string
     */
    private static String getWeekSun(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // 这种输出的是上个星期周日的日期，因为国外那边把周日当成第一天
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // 增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return df.format(cal.getTime());
    }
    /**
     * 获取当前月
     * @return string
     */
    private static String getMonthMon(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        cal.add(Calendar.DATE,-1);
        return df.format(cal.getTime());
    }

    /**
     * 获取上月
     * @return string
     */
    private static String getMonthSun(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        cal.add(Calendar.MONTH,-1);
        return df.format(cal.getTime());
    }
    public static void main(String[] args) throws UnknownHostException {
        //System.out.println(getWeekMon()+"-----"+getWeekSun());
        /*InetAddress address = InetAddress.getLocalHost();
        String hostAddress = address.getHostAddress();
        System.out.println(hostAddress);*/
        System.out.println(getMonthSun());
    }

}
