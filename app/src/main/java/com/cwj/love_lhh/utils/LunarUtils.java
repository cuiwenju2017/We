package com.cwj.love_lhh.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 工具类，通过查表法实现公农历互转
 */
public class LunarUtils {
    /**
     * 支持转换的最小农历年份
     */
    private static final int MIN_YEAR = 1900;
    /**
     * 支持转换的最大农历年份
     */
    private static final int MAX_YEAR = 2099;

    /**
     * 公历每月前的天数
     */
    private static final int DAYS_BEFORE_MONTH[] = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};

    /**
     * 用来表示1900年到2099年间农历年份的相关信息，共24位bit的16进制表示，其中：
     * 1. 前4位表示该年闰哪个月；为0表示不润月
     * 2. 5-17位从左至右表示农历年份13个月的大小月分布，闰月紧接着月份，0表示小，1表示大；
     * 3. 最后7位表示农历年首（正月初一）对应的公历日期。 高两位表示月份，低5位表示日期
     * <p>
     * 以2014年的数据0x955ABF为例说明：
     * 1001 0101 0101 1010 1011 1111
     * 闰九月                                 		 农历正月初一对应公历1月31号
     */
    private static final int LUNAR_INFO[] =
            {
                    0x84B6BF,/*1900*/
                    0x04AE53, 0x0A5748, 0x5526BD, 0x0D2650, 0x0D9544, 0x46AAB9, 0x056A4D, 0x09AD42, 0x24AEB6, 0x04AE4A,/*1901-1910*/
                    0x6A4DBE, 0x0A4D52, 0x0D2546, 0x5D52BA, 0x0B544E, 0x0D6A43, 0x296D37, 0x095B4B, 0x749BC1, 0x049754,/*1911-1920*/
                    0x0A4B48, 0x5B25BC, 0x06A550, 0x06D445, 0x4ADAB8, 0x02B64D, 0x095742, 0x2497B7, 0x04974A, 0x664B3E,/*1921-1930*/
                    0x0D4A51, 0x0EA546, 0x56D4BA, 0x05AD4E, 0x02B644, 0x393738, 0x092E4B, 0x7C96BF, 0x0C9553, 0x0D4A48,/*1931-1940*/
                    0x6DA53B, 0x0B554F, 0x056A45, 0x4AADB9, 0x025D4D, 0x092D42, 0x2C95B6, 0x0A954A, 0x7B4ABD, 0x06CA51,/*1941-1950*/
                    0x0B5546, 0x555ABB, 0x04DA4E, 0x0A5B43, 0x352BB8, 0x052B4C, 0x8A953F, 0x0E9552, 0x06AA48, 0x6AD53C,/*1951-1960*/
                    0x0AB54F, 0x04B645, 0x4A5739, 0x0A574D, 0x052642, 0x3E9335, 0x0D9549, 0x75AABE, 0x056A51, 0x096D46,/*1961-1970*/
                    0x54AEBB, 0x04AD4F, 0x0A4D43, 0x4D26B7, 0x0D254B, 0x8D52BF, 0x0B5452, 0x0B6A47, 0x696D3C, 0x095B50,/*1971-1980*/
                    0x049B45, 0x4A4BB9, 0x0A4B4D, 0xAB25C2, 0x06A554, 0x06D449, 0x6ADA3D, 0x0AB651, 0x095746, 0x5497BB,/*1981-1990*/
                    0x04974F, 0x064B44, 0x36A537, 0x0EA54A, 0x86B2BF, 0x05AC53, 0x0AB647, 0x5936BC, 0x092E50, 0x0C9645,/*1991-2000*/
                    0x4D4AB8, 0x0D4A4C, 0x0DA541, 0x25AAB6, 0x056A49, 0x7AADBD, 0x025D52, 0x092D47, 0x5C95BA, 0x0A954E,/*2001-2010*/
                    0x0B4A43, 0x4B5537, 0x0AD54A, 0x955ABF, 0x04BA53, 0x0A5B48, 0x652BBC, 0x052B50, 0x0A9345, 0x474AB9,/*2011-2020*/
                    0x06AA4C, 0x0AD541, 0x24DAB6, 0x04B64A, 0x6a573D, 0x0A4E51, 0x0D2646, 0x5E933A, 0x0D534D, 0x05AA43,/*2021-2030*/
                    0x36B537, 0x096D4B, 0xB4AEBF, 0x04AD53, 0x0A4D48, 0x6D25BC, 0x0D254F, 0x0D5244, 0x5DAA38, 0x0B5A4C,/*2031-2040*/
                    0x056D41, 0x24ADB6, 0x049B4A, 0x7A4BBE, 0x0A4B51, 0x0AA546, 0x5B52BA, 0x06D24E, 0x0ADA42, 0x355B37,/*2041-2050*/
                    0x09374B, 0x8497C1, 0x049753, 0x064B48, 0x66A53C, 0x0EA54F, 0x06AA44, 0x4AB638, 0x0AAE4C, 0x092E42,/*2051-2060*/
                    0x3C9735, 0x0C9649, 0x7D4ABD, 0x0D4A51, 0x0DA545, 0x55AABA, 0x056A4E, 0x0A6D43, 0x452EB7, 0x052D4B,/*2061-2070*/
                    0x8A95BF, 0x0A9553, 0x0B4A47, 0x6B553B, 0x0AD54F, 0x055A45, 0x4A5D38, 0x0A5B4C, 0x052B42, 0x3A93B6,/*2071-2080*/
                    0x069349, 0x7729BD, 0x06AA51, 0x0AD546, 0x54DABA, 0x04B64E, 0x0A5743, 0x452738, 0x0D264A, 0x8E933E,/*2081-2090*/
                    0x0D5252, 0x0DAA47, 0x66B53B, 0x056D4F, 0x04AE45, 0x4A4EB9, 0x0A4D4C, 0x0D1541, 0x2D92B5          /*2091-2099*/
            };

    /**
     * 将农历日期转换为公历日期
     *
     * @param year     农历年份
     * @param month    农历月，若为闰月则传入负数
     * @param monthDay 农历日
     * @param //查询区间   1900年正月初一 至 2099年腊月三十
     * @return 返回农历日期对应的公历日期，year0, month1, day2.若返回null表示运行错误，请检查输入数据是否合法。
     */
    public static int[] lunarToSolar(int year, int month, int monthDay) {
        int dayOffset;
        int leapMonth;
        int i;
        int pos = Math.abs(month);

        if (year < MIN_YEAR || year > MAX_YEAR || pos < 1 || pos > 12 || monthDay < 1 || monthDay > 30) {
            return null;
        }
        leapMonth = leapMonth(year);
        if (month < 0 && pos != leapMonth) {
            return null;
        }

        //0x001F为 1 1111；按位与取低5位并减1，得到当年春节的日与1的天数
        dayOffset = (LUNAR_INFO[year - MIN_YEAR] & 0x001F) - 1;
        //0x60为 01100000，按位与取得春节所对应的月份+5个0，右移5位后取得春节所对应的月份
        int basemonth = (int) ((LUNAR_INFO[year - MIN_YEAR] & 0x0060) >> 5);
        //若春节对应的月份不为1月，则需将前面几个月的天数加上
        for (int m = 1; m < basemonth; m++) {
            dayOffset += daysInMonth(year, m);
        }
        //加上农历月份的天数差
        if (leapMonth != 0) {
            if (month < 0 || month > leapMonth) {
                pos = pos + 1;
            }
        }
        for (i = 1; i < pos; i++) {
            dayOffset += daysInMyMonth(year, i);
        }
        //加上农历日的天数差
        dayOffset += monthDay;

        //阳历已跨年处理
        int yeardays = daysInYear(year);
        if (dayOffset > yeardays) {
            year += 1;
            dayOffset -= yeardays;
        }

        int[] solarInfo = new int[3];

        for (i = 1; i < 13; i++) {
            boolean isleap = isLeapYear(year);
            int iPos = DAYS_BEFORE_MONTH[i];

            if (isleap && i > 2) {
                iPos += 1;
            }

            if (iPos >= dayOffset) {
                solarInfo[1] = i;
                iPos = DAYS_BEFORE_MONTH[i - 1];
                if (isleap && i > 2) {
                    iPos += 1;
                }
                solarInfo[2] = dayOffset - iPos;
                break;
            }
        }
        solarInfo[0] = year;

        return solarInfo;
    }

    /**
     * 将公历日期转换为农历日期，且标识是否是闰月
     *
     * @param year
     * @param month
     * @param monthDay
     * @param //查询区间   1900-01-31 至 2100-02-08
     * @return 返回公历日期对应的农历日期，year0，month1，day2，若返回月份为负数，则表示该月为闰月
     */
    public static int[] solarToLunar(int year, int month, int monthDay) {
        int[] lunarDate = new int[3];
        if (year < 1900 || year > 2100 || month < 1 || month > 12 || monthDay < 1 || monthDay > 31) {
            return null;
        } else if (year == 1900 && month == 1 && monthDay < 31) {
            return null;
        } else if (year == 2100) {
            if (month > 2) {
                return null;
            } else if (month == 2 && monthDay > 8) {
                return null;
            }
        }

        Date baseDate = new GregorianCalendar(1900, 0, 31).getTime();
        Date objDate = new GregorianCalendar(year, month - 1, monthDay).getTime();
        //与1900年春节的天数差,86400000 = 1000 * 60 * 60 * 24
        int offset = (int) ((objDate.getTime() - baseDate.getTime()) / 86400000L);

        // 用offset减去每农历年的天数计算当天是农历第几天
        // iYear最终结果是农历的年份, offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = MIN_YEAR; iYear <= MAX_YEAR && offset > 0; iYear++) {
            daysOfYear = daysInLunarYear(iYear);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }

        // 农历年份
        lunarDate[0] = iYear;

        int leapMonth = leapMonth(iYear); // 闰哪个月,1-12
        boolean isLeap = false;
        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth <= 13 && offset > 0; iMonth++) {
            daysOfMonth = daysInMyMonth(iYear, iMonth);
            offset -= daysOfMonth;
        }

        // 月份需要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }

        // 当前月超过闰月，要校正
        if (leapMonth != 0 && iMonth > leapMonth) {
            --iMonth;
            if (iMonth == leapMonth) {
                isLeap = true;
            }
        }

        if (isLeap) {
            lunarDate[1] = -1 * iMonth;
        } else {
            lunarDate[1] = iMonth;
        }
        lunarDate[2] = offset + 1;

        return lunarDate;
    }

    /**
     * 将农历日期转换为公历日期
     *
     * @param year     农历年份
     * @param month    农历月，若为闰月则传入负数
     * @param monthDay 农历日
     * @param //查询区间   1900年正月初一 至 2099年腊月三十
     * @return 返回农历日期对应的公历日期yyyy-MM-dd格式标准字符串.若返回空串，请检查输入数据是否合法。
     */
    public static String getTranslateSolarString(int year, int month, int monthDay) {
        String result = "";
        int[] soloar = lunarToSolar(year, month, monthDay);
        if (soloar != null) {
            result = soloar[0] + "-";
            if (soloar[1] < 10) {
                result = result + "0" + soloar[1];
            } else {
                result = result + soloar[1];
            }
            if (soloar[2] < 10) {
                result = result + "-0" + soloar[2];
            } else {
                result = result + "-" + soloar[2];
            }
        }
        return result;
    }

    /**
     * 将公历日期转换为农历日期字符串
     *
     * @param year
     * @param month
     * @param monthDay
     * @param //查询区间   1900-01-31 至 2100-02-08
     * @return 返回公历日期对应的农历日期字符串，若返回为空串，则表示参数错误
     */
    public static String getTranslateLunarString(int year, int month, int monthDay) {
        String result = "";
        int[] lunar = solarToLunar(year, month, monthDay);
        if (lunar != null) {
            String tp = getLunarMonthName(lunar[1]);
            result = lunar[0] + "年" + tp + "月";
            tp = getLunarDayName(lunar[2]);
            result = result + tp;
        }
        return result;
    }

    /**
     * 根据阳历的年月日获取字符串
     *
     * @param year     年
     * @param month    月
     * @param monthDay 日
     * @return
     */
    public String getSolarString(int year, int month, int monthDay) {
        String result = "";
        if (year > 0) {
            result = year + "-";
        }
        if (month < 10) {
            result = result + "0" + month;
        } else {
            result = result + month;
        }
        if (monthDay < 10) {
            result = result + "-0" + monthDay;
        } else {
            result = result + "-" + monthDay;
        }
        return result;
    }

    /**
     * 根据农历的年月日获取字符串
     *
     * @param year     年
     * @param month    月
     * @param monthDay 日
     * @return
     */
    public String getLunarString(int year, int month, int monthDay) {
        String result = "";
        if (year > 0) {
            result = year + "年";
        }
        String tp = getLunarMonthName(month);
        result += tp + "月";
        tp = getLunarDayName(monthDay);
        result += tp;
        return result;
    }

    /**
     * 获取阳历日期是星期几
     */
    public int getWeekByDateStr(int year, int month, int day) {
        int x = -1;
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            x = (cal.get(Calendar.DAY_OF_WEEK) + 6) % 7;
            if (x == 0) {
                x = 7;
            }
        } catch (Exception e) {
        }

        return x;
    }

    /**
     * 判断阳历year年是否为闰年
     *
     * @param year 将要计算的年份
     * @return 返回传入年份的总天数
     */
    public static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 == 0) {
            if (year % 400 == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 传回阳历 year年的总天数
     *
     * @param year 将要计算的年份
     * @return 返回传入年份的总天数
     */
    public static int daysInYear(int year) {
        return isLeapYear(year) ? 366 : 365;
    }

    /**
     * 传回阳历 year年month月的总天数
     *
     * @param year 将要计算的年份  month 要传入的月份
     * @return 返回传入年份的总天数
     */
    public static int daysInMonth(int year, int month) {
        if (month != 2) {
            if (month <= 7) {
                if (month % 2 == 1) {
                    return 31;
                } else {
                    return 30;
                }
            } else {
                if (month % 2 == 1) {
                    return 30;
                } else {
                    return 31;
                }
            }
        } else {
            if (year > 0 && isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    /**
     * 传回农历 year年的总天数
     *
     * @param year 将要计算的年份
     * @return 返回传入年份的总天数，若返回-1则表示运行错误，请检查传入参数
     */
    public static int daysInLunarYear(int year) {
        if (year < 1900 || year > 2099) {
            return -1;
        }
        int i = 0;
        int sum = 348;
        //若不闫月，且所有月都是小月，农历一年348天；若闫且所有月都是小月，则为377天
        if (leapMonth(year) != 0) {
            sum = 377;
        }
        //0x0FFF80为1111 1111 1111 1000 0000，取当年各个月份的大小，每有一个大月加一天
        int monthInfo = LUNAR_INFO[year - MIN_YEAR] & 0x0FFF80;
        //0x80000为  1000 0000 0000 0000 0000，0x40为100 0000
        for (i = 0x80000; i > 0x40; i >>= 1) {
            if ((monthInfo & i) != 0)
                sum += 1;
        }
        return sum;
    }

    /**
     * 传回农历 year年闰哪个月 1-12 , 没闰传回 0
     *
     * @param year 将要计算的年份
     * @return 传回农历 year年闰哪个月1-12, 没闰传回 0.若传回－1则表示传入参数错误
     */
    public static int leapMonth(int year) {
        if (year < 1900 || year > 2099) {
            return -1;
        }
        return (int) ((LUNAR_INFO[year - MIN_YEAR] & 0xF00000)) >> 20;
    }

    /**
     * 传回农历 year年month月的总天数,错误返回-1
     * 若要查询闰月的天数，请将此月份设为负数
     */
    public int daysInLunarMonth(int year, int month) {
        int pos = Math.abs(month);
        if (year < 1900 || year > 2099 || pos > 12 || pos == 0) {
            return -1;
        }
        int leap = leapMonth(year);
        if (month < 0 && pos != leap) {
            return -1;
        }

        if (leap != 0) {
            if (month < 0 || month > leap) {
                pos = pos + 1;
            }
        }

        if ((LUNAR_INFO[year - MIN_YEAR] & (0x100000 >> pos)) == 0)
            return 29;
        else
            return 30;
    }

    /**
     * 传回农历 year年的月份列表，闰月以负数表示
     * 若要查询通用年份的月份列表，则将年份设为负数
     *
     * @return 以int数组的形式返回月份列表，若返回null表示参数错误
     */
    public int[] getLunarMonths(int year) {
        if ((year > 0 && year < 1900) || year > 2099) {
            return null;
        }
        int[] months;
        int leap = 0;
        if (year > 0) {
            leap = leapMonth(year);
        }
        if (leap == 0) {
            months = new int[12];
            for (int i = 0; i < 12; i++) {
                months[i] = i + 1;
            }
        } else {
            months = new int[13];
            for (int i = 0; i < 13; i++) {
                if (i + 1 <= leap) {
                    months[i] = i + 1;
                } else if (i == leap) {
                    months[i] = -1 * leap;
                } else {
                    months[i] = i;
                }
            }
        }
        return months;
    }

    /**
     * 根据农历的数字月份获取农历月份的字符串形式
     *
     * @return 若返回null表示参数错误
     */
    public static String getLunarMonthName(int month) {
        String res = "";
        String[] months = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"};
        int x = Math.abs(month);
        if (month < 0) {
            res = "闰";
        }
        res += months[x - 1];
        return res;
    }

    /**
     * 根据农历的数字月份获取农历月份的字符串形式
     *
     * @return 若返回null表示参数错误
     */
    public static String getLunarDayName(int day) {
        String res = "";
        String[] days1 = {"初", "十", "廿", "三"};
        String[] days2 = {"十", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        int x = (int) day / 10;
        int y = day % 10;
        if (x == 1 && y == 0) {
            res = "初十";
        } else {
            res = days1[x] + days2[y];
        }
        return res;
    }

    /**
     * 传回农历 year年pos月的总天数，总共有13个月包括闰月
     * 月份为按13个月的位置
     */
    private static int daysInMyMonth(int year, int pos) {
        if ((LUNAR_INFO[year - MIN_YEAR] & (0x100000 >> pos)) == 0)
            return 29;
        else
            return 30;
    }

}
