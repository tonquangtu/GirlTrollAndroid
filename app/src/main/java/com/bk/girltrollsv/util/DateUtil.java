package com.bk.girltrollsv.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by User on 10/7/2015.
 */
public class DateUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);

    /**
     * Calculator age
     *
     * @param dateOfBirth
     * @return age
     */
    public static int getAgeByBirthDay(String dateOfBirth) {
        if (TextUtils.isEmpty(dateOfBirth))
            return 0;

        int age = 0;

        try {
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            Date date = DATE_FORMAT.parse(dateOfBirth.trim());
            dob.setTime(date);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return age;
    }

    public static Calendar getBirthDayCalendar(String dateOfBirth) {
        if (dateOfBirth == null) {
            return Calendar.getInstance();
        }
        try {
            Date bd = DATE_FORMAT.parse(dateOfBirth);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(bd);
            return calendar;
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance();
    }

    public static String getDateString(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Convert date string to date string by format type
     *
     * @param date
     * @param typeInput
     * @param typeOutput
     * @param locale
     * @return string date follow output type
     */
    public static String convertDate(String date, String typeInput,
                                     String typeOutput, Locale locale) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(typeInput, locale);
        SimpleDateFormat outputFormat = new SimpleDateFormat(typeOutput, locale);
        Date inputDate = null;
        String outputDate = null;
        try {
            inputDate = inputFormat.parse(date);
            outputDate = outputFormat.format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Japan"));
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateFromCalendar(Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(calendar.getTime());
    }

    public static List<String> getListDayofWeek() {
        List<String> date = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        date.add(DATE_FORMAT.format(calendar.getTime()));
        DebugLog.e(DATE_FORMAT.format(calendar.getTime()));

        for (int i = 1; i <= 7; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            date.add(dateFormat.format(cal.getTime()));
            DebugLog.e(dateFormat.format(cal.getTime()));
        }
        return date;
    }

    public static String getFrequency(String date) {
        if (StringUtil.isEmpty(date)) {
            return "";
        }
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPANESE);
        DateFormat dateFormat2 = new SimpleDateFormat("EEE", Locale.JAPANESE);
        Date day = null;
        try {
            day = dateFormat1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat2.format(day);
    }

    public static String convertTime(String time) {
        //Todo: convertTime 00:00:00- 00:00:00 -> 00:00-00:00
        if (StringUtil.isEmpty(time)) {
            return "";
        }
        String[] parts = time.split("-");
        String part1 = parts[0]; // 00:00:00
        String part2 = parts[1]; // 00:00:00
        String resault = part1.substring(0, 5) + "-" + part2.substring(0, 5);
        return resault;
    }

    public static String getFrequency(int frequency) {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.JAPANESE);
        String[] namesOfDays = dateFormatSymbols.getWeekdays();
        return namesOfDays[frequency];
    }

    public static List<String> getDateTomorrow(String todays, int compareToday) {
        //Todo: return tomorrow if compareToday = 1; yesterday = -1
        List<String> dataDate = new ArrayList<>();
        try {
            Calendar calendar = Calendar.getInstance();
            Date today = DATE_FORMAT.parse(todays);
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_YEAR, compareToday);
            Date tomorrow = calendar.getTime();
            dataDate.add(DATE_FORMAT.format(tomorrow));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataDate;
    }

    public static String getNextDate(String today) {

        if (StringUtil.isEmpty(today)) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        try {
            Date date = DATE_FORMAT.parse(today);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            return DATE_FORMAT.format(tomorrow);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int convertHHmmSSToSecond(String hhmmss) {
        String[] units = hhmmss.split(":");
        int house = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]); //first element
        int seconds = Integer.parseInt(units[2]); //second element
        int duration = 3600 * house + 60 * minutes + seconds;
        return duration;
    }

    public static int convertHHmmToSecond(String hhmm) {
        String[] units = hhmm.split(":");
        int house = Integer.parseInt(units[0]); //first element
        int minutes = Integer.parseInt(units[1]); //second element
        int duration = 3600 * house + minutes * 60;
        return duration;
    }

    public static boolean compareDateTimeProgram(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN);
        format.setTimeZone(TimeZone.getTimeZone("Japan"));

        try {
            Date date = format.parse(stringDate);
            if (Calendar.getInstance().getTimeInMillis() > date.getTime())
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean compareDateProgram(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);
        format.setTimeZone(TimeZone.getTimeZone("Japan"));

        try {
            Date currentDate = format.parse(format.format(new Date()));
            Date date = format.parse(stringDate);
            if (currentDate.getTime() > date.getTime())
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Japan"));
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Japan"));
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }

    public static boolean inPeriodTime(String timeCheck, String periodTime) {
        String[] times = periodTime.split("-");

        int startSecondTime = convertHHmmToSecond(times[0]);
        int endSecondTime = convertHHmmToSecond(times[1]);
        int secondTimeCheck = convertHHmmToSecond(timeCheck);

        if (secondTimeCheck >= startSecondTime && secondTimeCheck <= endSecondTime) {
            return true;
        }

        return false;
    }

    public static boolean inPeriodDateTime(String current, String proDate, String periodTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN);
        format.setTimeZone(TimeZone.getTimeZone("Japan"));

        String[] times = periodTime.split("-");

        if (times.length < 2) {
            return false;
        }

        String datetimeStart = String.format("%s %s", proDate, times[0]);
        String datetimeEnd = String.format("%s %s", proDate, times[1]);

        try {
            Date currentDate = format.parse(current);
            Date startDate = format.parse(datetimeStart);
            Date endDate = format.parse(datetimeEnd);

            if (currentDate.getTime() >= startDate.getTime() && currentDate.getTime() <= endDate.getTime()) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static List<Date> nextSevenDays() {
        List<Date> listSevenDate = new ArrayList<>();
        Date currentDate = new Date();
        listSevenDate.add(currentDate);
        for (int i = 1; i <= 6; i++) {
            listSevenDate.add(new Date(currentDate.getTime() + (i * 86400000)));
        }
        return listSevenDate;
    }

}
