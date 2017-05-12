package com.hzm.test.javadate.differentdays;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DifferentDays {

	@Test
	public void show() {
		String dateStr = "2015-1-1 21:21:28";
		String dateStr2 = "2015-2-2 1:21:28";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			System.out.println("两个日期的差距：" + differentDays(date, date2));
			// System.out.println("两个日期的差距：" +
			// differentDaysByMillisecond(date,date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		// 不同年
		if (year1 != year2) {
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}
			System.out.println(day1 + "---" + day2);
			return timeDistance + (day2 - day1);
		} else {// 同一年
			System.out.println(day1 + "---" + day2);
			System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}
}
