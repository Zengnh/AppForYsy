package com.toolmvplibrary.tool_app;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToolData {
    private static SimpleDateFormat simpleData = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat simpleDataymd = new SimpleDateFormat("yyyyMMdd");
    //    2020-09-03 14:30:15
    private static SimpleDateFormat simpleDataymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDateYYYY_MM_DD(Date date) {
        return simpleData.format(date);
    }
    public String getDateYYYYMMDD(Date date) {
        return simpleDataymd.format(date);
    }
    public static String getDateYmdhms(Date date) {
        return simpleDataymdhms.format(date);
    }

//    获取本周起始日期
    public static Date getTimeInterval(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
//        String imptimeBegin = sdf.format(cal.getTime());
//        // System.out.println("所在周星期一的日期：" + imptimeBegin);
//        cal.add(Calendar.DATE, 6);
//        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        return cal.getTime();
    }

    /**
     * 获取上周开始时间
     * @return
     */
    public static Date getLastStartTimeInterval() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
//        String lastBeginDate = sdf.format(calendar1.getTime());
//        // System.out.println(sdf.format(calendar2.getTime()));// last Sunday
//        String lastEndDate = sdf.format(calendar2.getTime());
        return calendar1.getTime();
    }

    /**
     * 获取上周最后一天时间
     * @return
     */
    public static Date getLastEndTimeInterval() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
//        String lastBeginDate = sdf.format(calendar1.getTime());
//        // System.out.println(sdf.format(calendar2.getTime()));// last Sunday
//        String lastEndDate = sdf.format(calendar2.getTime());
        return calendar2.getTime();
    }

    /**
     * 获取本月开始时间
     * @return
     */
    public static Date getThisMouth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 获取今年开始时间
     * @return
     */
    public static Date getThisYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 获取上个月起始时间
     * @return
     */
    public static Date getProMouthStart() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
//        int lastMonthMaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 23, 59, 59);
        return c.getTime();
    }

    /**
     * 获取上个月最后一天
     * @return
     */
    public static Date getProMouthEnd() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
//        String gtimelast = sdf.format(c.getTime()); //上月
//        System.out.println(gtimelast);
        int lastMonthMaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(lastMonthMaxDay);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);

//        //按格式输出
//        String gtime = sdf.format(c.getTime()); //上月最后一天
//        System.out.println(gtime);
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01  00:00:00");
//        String gtime2 = sdf2.format(c.getTime()); //上月第一天
//        System.out.println(gtime2);
        return c.getTime();
    }

    /**
     * 获取今天开始时间
     * @return
     */
    public static Date getTodayTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


//    ##############################################################
    private static DateFormat dfDate = new SimpleDateFormat("yyyyMMdd");
    private static DateFormat dfDateShow = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static DateFormat dfYear = new SimpleDateFormat("yyyy");// 年
    private static DateFormat df = new SimpleDateFormat("HH:mm");
    private static DateFormat df2 = new SimpleDateFormat("MM-dd");
    private static DateFormat df3 = new SimpleDateFormat("MM-dd HH:mm");


    //    获取时间，方便后期做统一时间修改，目前单独处理
    public static String getTimeChat(long l) {
        Date date = new Date(l);
        Date today = new Date();

        if (!dfYear.format(today).equals(dfYear.format(date))){
            return dfDateShow.format(date);
        }else if (dfDate.format(today).equals(dfDate.format(date))) {
            return df.format(date);
        } else {
            return df3.format(date);
        }
    }


    //    获取时间，方便后期做统一时间修改，目前单独处理
    public static String getTime(long l) {
        Date date = new Date(l);
        Date today = new Date();
        if (dfDate.format(today).equals(dfDate.format(date))) {
            return df.format(date);
        } else {
            return df2.format(date);
        }
    }
}
