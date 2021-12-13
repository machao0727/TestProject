package com.dongyuwuye.compontent_sdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/4/5.
 */

public class DateArrayUtils {
    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }


    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期是该月的第几天
     *
     * @return
     */
    public static int getCurrentDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期是该周的第几天
     *
     * @return
     */
    public static int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前是几点
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//二十四小时制
    }

    /**
     * 获取当前是几分
     *
     * @return
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 获取当前秒
     *
     * @return
     */
    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    /**
     * 根据传入的年份和月份，获取当前月份的日历分布
     *
     * @param year
     * @param month
     * @return
     */
    public static List<Integer> getDayOfMonthFormat(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);//设置时间为每月的第一天
        //设置日历格式数组,6行7列
        int days[][] = new int[6][7];
        //设置该月的第一天是周几
        int daysOfFirstWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //设置本月有多少天
        int daysOfMonth = getDaysOfMonth(year, month);
        //设置上个月有多少天
        int daysOfLastMonth = getLastDaysOfMonth(year, month);
        int dayNum = 1;
        int nextDayNum = 1;
        //将日期格式填充数组
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                if (i == 0 && j < daysOfFirstWeek - 1) {
                    days[i][j] = daysOfLastMonth - daysOfFirstWeek + 2 + j;
                } else if (dayNum <= daysOfMonth) {
                    days[i][j] = dayNum++;
                } else {
                    days[i][j] = nextDayNum++;
                }
            }
        }
        List<Integer> realdays = new ArrayList<>();
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                realdays.add(days[i][j]);
            }
        }
        return realdays;
    }

    /**
     * 根据传入的年份和月份，判断上一个有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getLastDaysOfMonth(int year, int month) {
        int lastDaysOfMonth = 0;
        if (month == 1) {
            lastDaysOfMonth = getDaysOfMonth(year - 1, 12);
        } else {
            lastDaysOfMonth = getDaysOfMonth(year, month - 1);
        }
        return lastDaysOfMonth;
    }

    /**
     * 根据传入的年份和月份，判断当前月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (isLeap(year)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return -1;
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeap(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定字符串时间是否为今日(效率不是很高，不过也是一种方法)
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate3(String sdate) {
        try {
            return dateFormater3.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {//是在同一年
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay < 0) {//在今天以前
                return true;
            } else {//在今天以后
                return false;
            }
        } else {//不是在同一年
            return true;
        }
    }

    /**
     * 判断是否为昨天(效率比较高),同时判断不同年的情况
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean NewIsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {//是在同一年
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay < 0) {//在今天以前
                return true;
            } else {//在今天以后
                return false;
            }
        } else if (cal.get(Calendar.YEAR) > (pre.get(Calendar.YEAR))) {//今年以后
            return false;
        } else {//今年以前
            return true;
        }
    }

    /**
     * 判断当前时间是在什么区间,今天，跨天还是跨年
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static int CurrentTime(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {//是在同一年
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay < 0) {//今天以前
                return 1;
            } else if (diffDay == 0) {//今天
                return 0;
            } else {//今天以后
                return 2;
            }
        } else {//不是在同一年
            return 3;
        }
    }

    /**
     * 判断是否同一个月
     *
     * @param day
     * @return
     */
    public static int isSameMonth(String day) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = getDateFormat().parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        int diffMonth = 0;
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {//是在同一年
            int diffDay = cal.get(Calendar.MONTH)
                    - pre.get(Calendar.MONTH);
            if (diffDay == 0) {//同一个月
                diffMonth = 0;
            } else if (diffDay < 0) {//上月
                diffMonth = 1;
            } else if (diffDay > 0) {//以后
                diffMonth = 2;
            }
        } else {
            diffMonth = 2;
        }
        return diffMonth;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    /**
     * 转换时间格式
     */
    public static String resetDataTime(String dtf, String time) {
        return resetDataTime(dtf, time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTime(String style) {
        SimpleDateFormat sdf = new SimpleDateFormat(style, Locale.CHINA);
        return sdf.format(new Date());
    }


    /**
     * 转换时间格式
     */
    public static String resetDataTime(Date date, String style) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(style, Locale.CHINA);
        return dateFormat.format(date);
    }


    /**
     * 转换时间格式
     */
    public static String resetDataTime(String dtf, String time, String currentStyle) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dtf);
        SimpleDateFormat currentFormat = new SimpleDateFormat(currentStyle);
        String resetTime;
        try {//格式化成功
            Date date = currentFormat.parse(time);
            resetTime = dateFormat.format(date);
        } catch (ParseException e) {
            //格式化未成功
            resetTime = "";
        }
        return resetTime;
    }

    /**
     * 判断两个时间的大小
     */
    public static long CheckTime(String data1, String data2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1;
            Date date2;
            if (data1.contains("/")) {
                date1 = dateFormat.parse(data1);
            } else {
                date1 = dateFormat1.parse(data1);
            }
            if (data2.contains("/")) {
                date2 = dateFormat.parse(data2);
            } else {
                date2 = dateFormat1.parse(data2);
            }

//            if (date1.getTime()>date2.getTime()){
//                return 1;
//            }else {
//                return 2;
//            }
            return date1.getTime() - date2.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断时间差（根据天数计算）
     *
     * @return
     */
    public int offsetDay(String day) throws ParseException {
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastDate = dtf.parse(day);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(lastDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) //不同年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                {
                    timeDistance += 366;
                } else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return Math.abs(timeDistance + (day2 - day1));
        } else //同年
        {
            return Math.abs(day2 - day1);
        }
    }

    /**
     * 判断时间差（根据秒速计算）
     *
     * @return
     */
    public static int offsetDaymillis(String day) throws ParseException {
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastDate = dtf.parse(day);
        Date CurrentDate = new Date();
        int days = (int) ((lastDate.getTime() - CurrentDate.getTime()) / (1000 * 3600 * 24));
        return Math.abs(days);
    }

    /**
     * 判断时间差（根据秒速计算）
     * 传入时间 2019-03-22 11-03
     *
     * @return
     */
    public static long getDaymillis(String day) {
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date lastDate = null;
        try {
            lastDate = dtf.parse(day + ":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date CurrentDate = new Date();
        long days = (lastDate.getTime() - CurrentDate.getTime());
        return Math.abs(days) / 1000;
    }

    //获取当前日期往后一周的时间
    public static List<String> getSevendate() {
        List<String> dates = new ArrayList<>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int maxDay = c.getActualMaximum(Calendar.DATE);//该月最大天数
        int mYear = c.get(Calendar.YEAR);// 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //当天
        String date = mYear + "." + mMonth + "." + mDay;
        dates.add(date);
        //往后6天
        for (int i = 0; i < 6; i++) {
            if (mDay + 1 > maxDay) { //超过最大天数
                if (mMonth + 1 > 12) {//该年最后一个月
                    mYear = mYear + 1;
                    mMonth = 1;
                } else {
                    mMonth = mMonth + 1;
                }
                mDay = 1;
            } else {
                mDay = mDay + 1;
            }
            date = mYear + "." + mMonth + "." + mDay;
            dates.add(date);
        }
        return dates;
    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<String> getWeeks() {
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int week = getCurrentDayOfWeek();
        for (int i = 0; i < 7; i++) {
            String date = "";
            switch (week + i < 8 ? week + i : Math.abs(week + i - 7)) {
                case 1:
                    date = "周日";
                    break;
                case 2:
                    date = "周一";
                    break;
                case 3:
                    date = "周二";
                    break;
                case 4:
                    date = "周三";
                    break;
                case 5:
                    date = "周四";
                    break;
                case 6:
                    date = "周五";
                    break;
                case 7:
                    date = "周六";
                    break;
            }
            if (i == 0) {
                date = "今天";
            }
            dates.add(date);
        }
        return dates;
    }

    /**
     * 得到当年当月的最大日期
     **/
    public static int MaxDayFromDay_OF_MONTH(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month - 1);//注意,Calendar对象默认一月为0                 
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

    /**
     * String 转 Calendar
     *
     * @param time
     * @return
     */
    public static Calendar getCalendar(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date subOneDaty(Date date) {
        return new Date(date.getTime() - 3600 * 24 * 1000);
    }

    /**
     * String 转 Calendar
     *
     * @param time
     * @return
     */
    public static String getTime(Calendar time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time.getTime());
    }

    /**
     * String 转 Calendar
     *
     * @param time
     * @return
     */
    public static String getTime(Calendar time, String style) {
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        return sdf.format(time.getTime());
    }

    /**
     * 获取指定时间的秒
     *
     * @param time
     * @return
     */
    public static long getTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取当前月第一天
     *
     * @return
     */
    public static String getFirstDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }

    /**
     * 获取当前月最后一天
     *
     * @return
     */
    public static String getLastDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime());
    }


}
