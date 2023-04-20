package com.yangy.mutipile.data.util;

import java.text.SimpleDateFormat;
import java.util.*;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Title: CronTranslator
 * @Package com.yangy.mutipile.data.util
 * @Description: cron表达式转换中文
 * @Author: yangy
 * @Date: 2023/3/28 13:04
 **/
public class CronTranslator {


    private static final Logger logger = LoggerFactory.getLogger(CronTranslator.class);
    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static String[] CRON_TIME_CN = new String[]{"秒", "分", "时", "天", "月", "周", "年"};
    private final static Integer HOURS = 24;
    private final static Integer TIMESCALE = 60;

    /**
     * @Description: 获取下次执行时间
     * @Author: yangy
     * @Date: 2022/8/9 16:00
     * @Params:[cronExpressionStr]
     * @Returns:java.lang.String
     **/
    public static String getNextFireTimeStrAfterNow(String cronExpressionStr) {
        String dateStr = null;
        try {
            CronExpression cronExpression = new CronExpression(cronExpressionStr);
            Date date = cronExpression.getTimeAfter(new Date());
            //将date转换为指定日期格式字符串
            dateStr = dataFormat.format(date);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return dateStr;
    }

    public static Date getNextFireDateAfterNow(String cronExpressionStr) {
        Date date = null;
        try {
            CronExpression cronExpression = new CronExpression(cronExpressionStr);
            date = cronExpression.getTimeAfter(new Date());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return date;
    }

    /**
     * @Description:解析cron表达式成中文 * 主要解析 斜杆  /：表示起始时间开始触发，然后每隔固定时间触发一次。
     * * 范围值+开始值 - 间隔值 = 范围内最后执行的值；
     * * 例如在Hourss域使用3/4,则意味着从第3小时到24+3-4：23小时范围内，第3小时开始触发第一次，然后每隔4小时，即7，11，15，19，23小时等分别触发一次。
     * * 例如在Minutes域使用5/20,则意味着从第5分钟到60+5-20：45分范围内，第5分钟开始触发第一次，然后每隔20分钟，即25，45分钟等分别触发一次。
     * * 例如在Seconds域使用8/10,则意味着从第8秒到60+8-10：58秒范围内，第8秒开始触发第一次，然后每隔10秒，即18，28，38，48，58秒等分别触发一次。
     * * *
     * * 对于 *：表示匹配该域的任意值。例如在Minutes域使用*, 即表示每分钟都会触发事件。
     * * 对于问号  ?：只能用在DayofMonth和DayofWeek两个域，其作用为不指定
     * * 对于 -：表示范围。例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次。直接进行拼接
     * * 对于逗号 ,：表示列出枚举值。例如在Minutes域使用5,20 ， 则意味着在5和20分每分钟触发一次。
     * * 对于L：表示最后，只能出现在DayofWeek和DayofMonth域。
     * * 如果出现在DayofMonth域，只能使用L，表示当月最后一天
     * * 如果在DayofWeek域
     * * 使用数字（1-7）或L（和7的作用一样表示每周的最后一天周六），比如数字"5"表示每周4， "7"或"L"表示每周六
     * * 使用数字（1-7）结合L，表示当月最后一周的周几，比如"5L" 表示在最后的一周的周四； "3L" 表示最后一周的周二
     * * 对于#: 用于确定每个月第几个星期几，只能出现在DayofMonth域。
     * * 例如 "4#2" 表示某月的第二个星期三（4表示星期三，2表示第二周）;
     * * “6#3”表示本月第三周的星期五（6表示星期五，3表示第三周）;
     * * “2#1”表示本月第一周的星期一;
     * * “4#5”表示第五周的星期三。
     * @Author: yangy
     * @Date: 2022/8/12 16:47
     * @Params:[cronExp, cronTimeArr]
     * @Returns:java.lang.String
     **/
    public static String translateToChinese(String cronExp, String[] cronTimeArr) {
        if (cronExp == null || cronExp.length() < 1) {
            return "cron表达式为空";
        }

        String[] tmpCorns = cronExp.split(" ");
        StringBuffer sBuffer = new StringBuffer();
        if (tmpCorns.length == 6 || tmpCorns.length == 7) {
            if (tmpCorns.length == 7) {
                //解析年 Year
                String year = tmpCorns[6];
                if ((!year.equals("*") && !year.equals("?"))) {
                    sBuffer.append(year).append(cronTimeArr[6]);
                }
            }

            //解析月 Month 主要解析 /
            String months = tmpCorns[4];
            if (!months.equals("*") && !months.equals("?")) {
                if (months.contains(",")) {// 多个数字，逗号隔开
                    List<String> monthList = Arrays.asList(months.split(","));
                    sBuffer.append("每年");
                    for (int i = 0; i < monthList.size(); i++) {
                        sBuffer.append(judgeMonth(monthList.get(i)));
                        if (i < monthList.size() - 1) {
                            sBuffer.append(",");
                        }
                    }
                } else if (months.contains("/")) {
                    sBuffer.append("从").append(months.split("/")[0]).append("号开始").append(",每")
                            .append(months.split("/")[1]).append(cronTimeArr[4]);
                } else {
                    sBuffer.append("每年").append(months).append(cronTimeArr[4]);
                }
            } else {
                if (sBuffer.toString().length() == 0 && tmpCorns.length == 7 && "?".equals(tmpCorns[5])) { // 前面没有内容的话，拼接下
                    sBuffer.append("每").append(cronTimeArr[4]);
                }
                //sBuffer.append("每").append(cronTimeArr[4]);
            }

            //解析周 DayofWeek  主要解析 , -  1~7/L   1L~7L
            String dayofWeek = tmpCorns[5];
            if (!dayofWeek.equals("*") && !dayofWeek.equals("?")) {
                if (dayofWeek.contains(",")) {// 多个数字，逗号隔开
                    List<String> weekList = Arrays.asList(dayofWeek.split(","));
                    sBuffer.append("每星期");
                    for (int i = 0; i < weekList.size(); i++) {
                        sBuffer.append(judgeWeek(weekList.get(i)));
                        if (i < weekList.size() - 1) {
                            sBuffer.append(",");
                        }
                    }
                    //sBuffer.append(cronTimeArr[3]);
                    //sBuffer.append("每星期").append(dayofWeek).append(cronTimeArr[3]);
                } else if (dayofWeek.contains("L") && dayofWeek.length() > NumberUtils.INTEGER_ONE) {// 1L-7L
                    String weekNum = dayofWeek.split("L")[0];
                    String weekName = judgeWeek(weekNum);
                    sBuffer.append("每月的最后一周的");
                    sBuffer.append(weekName);
                } else if (dayofWeek.contains("-")) {
                    String[] splitWeek = dayofWeek.split("-");
                    String weekOne = judgeWeek(splitWeek[0]);
                    String weekTwo = judgeWeek(splitWeek[1]);
                    sBuffer.append("每周的").append(weekOne).append("到").append(weekTwo);
                } else { // 1-7/L
                    if ("L".equals(dayofWeek)) { // L 转换为7，便于识别
                        dayofWeek = "7";
                    }
                    int weekNums = Integer.parseInt(dayofWeek);
                    if (weekNums < 0 || weekNums > 7) {
                        return "cron表达式有误，dayofWeek数字应为1-7";
                    }
                    sBuffer.append("每周的");
                    String weekName = judgeWeek(dayofWeek);
                    sBuffer.append(weekName);
                }
            }

            //解析日 days -- DayofMonth  需要解析的 / # L W
//                 *       “6#3”表示本月第三周的星期五（6表示星期五，3表示第三周）;
//     *       “2#1”表示本月第一周的星期一;
//     *       “4#5”表示第五周的星期三。

            String days = tmpCorns[3];
            if (!days.equals("?") && !days.equals("*")) {
                if (days.contains("/")) {
                    sBuffer.append("每周从第").append(days.split("/")[0]).append("天开始").append(",每")
                            .append(days.split("/")[1]).append(cronTimeArr[3]);
                } else if ("L".equals(days)) { // 处理L 每月的最后一天
                    sBuffer.append("每月最后一天");
                } else if ("W".equals(days)) { // 处理W 暂时不懂怎么处理
                    sBuffer.append(days);
                } else if (days.contains("#")) {
                    String[] splitDays = days.split("#");
                    String weekNum = splitDays[0]; // 前面数字表示周几
                    String weekOfMonth = splitDays[1]; // 后面的数字表示第几周 范围1-4 一个月最多4周
                    String weekName = judgeWeek(weekNum);
                    sBuffer.append("每月第").append(weekOfMonth).append(cronTimeArr[5]).append(weekName);
                } else if (days.contains("-")) {
                    sBuffer.append(days).append("日");
                } else {
                    if (StringUtils.isBlank(months)) {
                        sBuffer.append("每月第").append(days).append(cronTimeArr[3]);
                    }
                }

            } else {
                if (sBuffer.toString().length() == 0 || ("*".equals(tmpCorns[4]) && "?".equals(tmpCorns[5]))) { // 前面没有内容的话，拼接下
                    StringBuffer daySb = new StringBuffer();
                    daySb.append("每").append(cronTimeArr[3]);
                    sBuffer = daySb;
                }
            }


            //解析时 Hours 主要解析 /
            String hours = tmpCorns[2];
            if (!hours.equals("*")) {
                if (hours.contains("/")) {
//                   sBuffer.append("内，从").append(hours.split("/")[0]).append("时开始").append(",每")
//                            .append(hours.split("/")[1]).append(cronTimeArr[2]);
                    sBuffer.append(appendGapInfo(hours, HOURS, 2));
                } else {
                    if (!(sBuffer.toString().length() > 0)) { // 对于 , 的情况，直接拼接
                        sBuffer.append("每天").append(hours).append(cronTimeArr[2]);
                    } else {
                        sBuffer.append(hours).append(cronTimeArr[2]);
                    }
                }
            } else {
                if (sBuffer.toString().length() == 0 || ("*".equals(months) && "*".equals(days))) { // 月天都为空
                    StringBuffer hourSb = new StringBuffer();
                    hourSb.append("每小时");
                    sBuffer = hourSb;
                } else if (!"*".equals(dayofWeek)) {
                    sBuffer.append("所有时");
                }
            }

            //解析分 Minutes 主要解析 /
            String minutes = tmpCorns[1];
            if (!minutes.equals("*")) {
                if (minutes.contains("/")) {
                    sBuffer.append(appendGapInfo(minutes, TIMESCALE, 1));
                } else if (minutes.equals("0")) {
                    sBuffer.append("0分");
                }
/*                else if (minutes.contains("-")) {
                    String[] splitMinute = minutes.split("-");
                    sBuffer.append(splitMinute[0]).append(cronTimeArr[1]).append("到").append(splitMinute[1])
                            .append(cronTimeArr[1]).append("每分钟");
                } */
                else {
                    sBuffer.append(minutes).append(cronTimeArr[1]);
                }
            } else {
                if ("*".equals(hours) && "*".equals(days)) {
                    StringBuffer minSb = new StringBuffer();
                    minSb.append("每分钟");
                    sBuffer = minSb;
                    //sBuffer.append("每分钟");
                } else {
                    sBuffer.append("所有分");
                }
            }

            //解析秒 Seconds 主要解析 /
            String seconds = tmpCorns[0];
            if (!seconds.equals("*")) {
                if (seconds.contains("/")) {
//                    sBuffer.append("内，从第").append(seconds.split("/")[0]).append("秒开始").append(",每")
//                            .append(seconds.split("/")[0]).append(cronTimeArr[0]);
                    sBuffer.append(appendGapInfo(seconds, TIMESCALE, 0));
                } else if (!seconds.equals("0")) {
                    sBuffer.append(seconds).append(cronTimeArr[0]);
                }
            }

            if (sBuffer.toString().length() > 0) {
                //sBuffer.append("执行一次");
            } else {
                sBuffer.append("表达式中文转换异常");
            }
        }
        return sBuffer.toString();
    }


    public static String judgeWeek(String weekNum) {
        String weekName = WeekEnum.matchNameCn(String.valueOf(weekNum));
        return StringUtils.isNotEmpty(weekName) ? weekName : String.valueOf(weekNum);
    }

    public static String judgeMonth(String monthNum) {
        String monthName = WeekEnum.matchNameCn(String.valueOf(monthNum));
        return StringUtils.isNotEmpty(monthName) ? monthName : String.valueOf(monthNum);
    }


    private static String appendGapInfo(String time, int rangeNum, int index) {
        StringBuffer sBufferTemp = new StringBuffer();
        String[] splitTime = time.split("/");
        String startNum = splitTime[0];
        String gapNum = splitTime[1];
        int endNum = rangeNum + Integer.parseInt(startNum) - Integer.parseInt(gapNum);
        String endStr = String.valueOf(endNum);
        String timeUnit = CRON_TIME_CN[index];
        sBufferTemp.append("从").append(startNum).append(timeUnit).append("开始")
                .append("到").append(endStr).append(timeUnit).append("范围内")
                .append(",每隔").append(gapNum).append(timeUnit);
        return sBufferTemp.toString();
    }

    public enum WeekEnum {
        SUN("1", "星期天", "SUN"),
        MON("2", "星期一", "MON"),
        TUE("3", "星期二", "TUE"),
        WED("4", "星期三", "WED"),
        THU("5", "星期四", "THU"),
        FRI("6", "星期五", "FRI"),
        SAT("7", "星期六", "SAT"),

        JAN("1", "一月", "JAN"),
        FEB("2", "二月", "FEB"),
        MAR("3", "三月", "MAR"),
        APR("4", "四月", "APR"),
        MAY("5", "五月", "MAY"),
        JUN("6", "六月", "JUN"),
        JUL("7", "七月", "JUL"),
        AUG("8", "八月", "AUG"),
        SEP("9", "九月", "SEP"),
        OCT("10", "十月", "OCT"),
        NOV("11", "十一月", "NOV"),
        DEC("12", "十二月", "DEC");

        private String key;
        private String nameCn;
        private String nameEn;

        WeekEnum(String key, String nameCn, String nameEn) {
            this.key = key;
            this.nameCn = nameCn;
            this.nameEn = nameEn;
        }


        public static String matchNameCn(String code) {
            for (WeekEnum m : WeekEnum.values()) {
                if (m.getNameEn().equals(code)) {
                    return m.getNameCn();
                }
            }
            return "";
        }


        public static String matchNameEn(String code) {
            for (WeekEnum m : WeekEnum.values()) {
                if (m.getKey().equals(code)) {
                    return m.getNameEn();
                }
            }
            return "";
        }


        public String getKey() {
            return key;
        }


        public void setKey(String key) {
            this.key = key;
        }


        public String getNameCn() {
            return nameCn;
        }


        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }


        public String getNameEn() {
            return nameEn;
        }


        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }
    }


    public static void main(String[] args) {
//        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
//        CronParser parser = new CronParser(cronDefinition);
//        ExecutionTime executionTime = ExecutionTime.forCron(parser.parse("*/45 * * * * ?"));
//        Optional<ZonedDateTime> zonedDateTime = executionTime.nextExecution(ZonedDateTime.now());
//        System.err.println("下次执行时间: " + zonedDateTime.toString());
//        Optional<ZonedDateTime> zonedDateTime1 = executionTime.lastExecution(ZonedDateTime.now());
//        System.err.println("最后执行时间: " + zonedDateTime1.toString());
//        ZonedDateTime timeForLast = ZonedDateTime.now();
//        Optional<Duration> duration = executionTime.timeFromLastExecution(timeForLast);
//        System.err.println("最后一次执行时间过去了: " + duration + " 秒");
//        Optional<Duration> duration1 = executionTime.timeToNextExecution(ZonedDateTime.now());
//        System.err.println("距离下次执行时间: " + duration1);

        String cronExpression = "0 0 9 ? * MON,TUE,WED,THU,FRI *";
        CronParser parser2 = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
        Cron cron = parser2.parse(cronExpression);
        String description = CronDescriptor.instance(Locale.CHINA).describe(cron);
        System.out.println(description);
        System.out.println(translateToChinese(cronExpression, CRON_TIME_CN));

    }

}
