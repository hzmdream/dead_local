
package dl.dlutils.utils.base.date;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dl.dlutils.utils.StringUtil;


/**
 * Parse and formats dates.
 * 
 * @author Kai Reinhard (k.reinhard@micromata.de)
 */
public class DateUtils implements Serializable {
	
	private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
	
	private static final long serialVersionUID = -94010735614402146L;
	
	/**
	 * Number of milliseconds of one minute. DO NOT USE FOR exact date
	 * calculations (summer and winter time etc.)!
	 */
	public static final long MILLIS_MINUTE = 60 * 1000;
	
	/**
	 * Number of milliseconds of one hour. DO NOT USE FOR exact date
	 * calculations (summer and winter time etc.)!
	 */
	public static final long MILLIS_HOUR = 60 * MILLIS_MINUTE;
	
	/**
	 * Number of milliseconds of one day. DO NOT USE FOR exact date calculations
	 * (summer and winter time etc.)!
	 */
	public static final long MILLIS_DAY = 24 * MILLIS_HOUR;
	
	/**
	 * Europe/Berlin
	 */
	public final static TimeZone EUROPE_BERLIN = TimeZone.getTimeZone("Europe/Berlin");
	
	public static final BigDecimal MILLIS_PER_HOUR = new BigDecimal(MILLIS_HOUR);
	
	public static final BigDecimal HOURS_PER_WORKING_DAY = new BigDecimal(DateTimeFormatter.DEFAULT_HOURS_OF_DAY);
	
	public static final BigDecimal MILLIS_PER_WORKING_DAY = new BigDecimal(MILLIS_HOUR * DateTimeFormatter.DEFAULT_HOURS_OF_DAY);
	
	public static final BigDecimal SECONDS_PER_WORKING_DAY = new BigDecimal(60 * 60 * DateTimeFormatter.DEFAULT_HOURS_OF_DAY);
	
	/**
	 * UTC
	 */
	public final static TimeZone UTC = TimeZone.getTimeZone("UTC");
	
	private static final DateFormat FORMAT_ISO_DATE = new SimpleDateFormat(DateFormats.ISO_DATE);
	
	private static final DateFormat FORMAT_ISO_SECOND = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_SECONDS);
	
	private static final DateFormat FORMAT_ISO_TIMESTAMP = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_MILLIS);
	
	private static final DateFormat FORMAT_STANDARD_DATE = new SimpleDateFormat(DateFormats.STANDARD_DATE);
	
	private static final DateFormat FILENAME_FORMAT_TIMESTAMP = new SimpleDateFormat(DateFormats.ISO_DATE + "_HH-mm-ss");
	
	private static final DateFormat FILENAME_FORMAT_DATE = new SimpleDateFormat(DateFormats.ISO_DATE);
	
	/**
	 * Compares millis. If both dates are null then they're equal.
	 * 
	 * @param d1
	 * @param d2
	 * @see Date#getTime()
	 */
	public static boolean equals(final Date d1, final Date d2) {
		if (d1 == null) {
			return d2 == null;
		}
		if (d2 == null) {
			return false;
		}
		return d1.getTime() == d2.getTime();
	}
	
	/**
	 * thread safe
	 * 
	 * @param timezone
	 */
	public static DateFormat getIsoDateFormat(final TimeZone timezone) {
		final DateFormat df = (DateFormat) FORMAT_ISO_DATE.clone();
		if (timezone != null) {
			df.setTimeZone(timezone);
		}
		return df;
	}
	
	/**
	 * thread safe
	 * 
	 * @param timezone If null then time zone is ignored.
	 */
	public static DateFormat getIsoSecondFormat(final TimeZone timezone) {
		final DateFormat df = (DateFormat) FORMAT_ISO_SECOND.clone();
		if (timezone != null) {
			df.setTimeZone(timezone);
		}
		return df;
	}
	
	/**
	 * thread safe
	 * 
	 * @param timezone If null then time zone is ignored.
	 */
	public static DateFormat getIsoTimestampFormat(final TimeZone timezone) {
		final DateFormat df = (DateFormat) FORMAT_ISO_TIMESTAMP.clone();
		if (timezone != null) {
			df.setTimeZone(timezone);
		}
		return df;
	}
	
	/**
	 * thread safe
	 * 
	 * @param timezone If null then time zone is ignored.
	 */
	public static DateFormat getStandardDateFormat(final TimeZone timezone) {
		final DateFormat df = (DateFormat) FORMAT_STANDARD_DATE.clone();
		if (timezone != null) {
			df.setTimeZone(timezone);
		}
		return df;
	}
	
	/**
	 * thread safe
	 * 
	 * @param timezone
	 */
	public static DateFormat getFilenameFormatTimestamp(final TimeZone timezone) {
		final DateFormat df = (DateFormat) FILENAME_FORMAT_TIMESTAMP.clone();
		if (timezone != null) {
			df.setTimeZone(timezone);
		}
		return df;
	}
	
	/**
	 * thread safe
	 * 
	 * @param timezone
	 */
	public static DateFormat getFilenameFormatDate(final TimeZone timezone) {
		final DateFormat df = (DateFormat) FILENAME_FORMAT_DATE.clone();
		if (timezone != null) {
			df.setTimeZone(timezone);
		}
		return df;
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss.S in UTC. Thread safe usage:
	 * FOR_TESTCASE_OUTPUT_FORMATTER.get().format(date)
	 */
	public static final ThreadLocal<DateFormat> FOR_TESTCASE_OUTPUT_FORMATTER = new ThreadLocal<DateFormat>() {
		
		@Override
		protected DateFormat initialValue() {
			final DateFormat df = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_MILLIS);
			df.setTimeZone(UTC);
			return df;
		}
	};
	
	/**
	 * Thread safe usage: FOR_TESTCASE_OUTPUT_FORMATTER.get().format(date)
	 */
	public static final ThreadLocal<DateFormat> TECHNICAL_ISO_UTC = new ThreadLocal<DateFormat>() {
		
		@Override
		protected DateFormat initialValue() {
			final DateFormat dateFormat = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_MILLIS + " z");
			dateFormat.setTimeZone(UTC);
			return dateFormat;
		}
	};
	
	/**
	 * @return Short name of day represented by the giving day. The context
	 *         user's locale and time zone is considered.
	 */
	public static final String formatShortNameOfDay(final Date date) {
		final DateFormat df = new SimpleDateFormat("EE", Locale.getDefault());
		df.setTimeZone(TimeZone.getDefault());
		return df.format(date);
	}
	
	/**
	 * Formats the given date as UTC date in ISO format attached TimeZone (UTC).
	 * 
	 * @param date
	 * @return
	 */
	public static final String formatAsUTC(final Date date) {
		if (date == null) {
			return "";
		}
		return UTC_ISO_DATE.get().format(date);
	}
	
	/**
	 * Thread safe usage: UTC_ISO_DATE.get().format(date)
	 */
	public static final ThreadLocal<DateFormat> UTC_ISO_DATE = new ThreadLocal<DateFormat>() {
		
		@Override
		protected DateFormat initialValue() {
			final DateFormat df = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_MILLIS + " Z");
			df.setTimeZone(UTC);
			return df;
		}
	};
	
	/**
	 * Takes time zone of context user if exist.
	 * 
	 * @param date
	 */
	public static String formatIsoDate(final Date date) {
		//为了防止界面的日期时间�?�传过来为null值时，代码报错，在此处做个判断，返回个空字符串�??
		if (date == null || date.equals("")) {
			return "";
		} else {
			return getIsoDateFormat(TimeZone.getDefault()).format(date);
		}
	}
	
	/**
	 * Takes time zone of context user if exist.
	 * 
	 * @param date
	 */
	public static String formatIsoDate(final Date date, final TimeZone timeZone) {
		return getIsoDateFormat(timeZone).format(date);
	}
	
	/**
	 * logError = true
	 * 
	 * @param str
	 * @return
	 * @see #parseMillis(String, boolean)
	 */
	public static Date parseMillis(final String str) {
		return parseMillis(str, true);
	}
	
	/**
	 * @param str
	 * @param logError If true, any ParseException error will be logged if
	 *            occured.
	 * @return The parsed date or null, if not parseable.
	 */
	public static Date parseMillis(final String str, final boolean logError) {
		Date date = null;
		try {
			final long millis = Long.parseLong(str);
			date = new Date(millis);
		} catch (final NumberFormatException ex) {
			if (logError == true) {
				log.error("Could not parse date string (millis expected): " + str, ex);
			}
		}
		return date;
	}
	
	public static String formatIsoSecond(final Date date) {
		return getIsoSecondFormat(TimeZone.getDefault()).format(date);
	}
	
	public static String formatIsoTimestamp(final Date date) {
		return getIsoTimestampFormat(TimeZone.getDefault()).format(date);
	}
	
	public static String formatStandardDate(final Date date) {
		return getStandardDateFormat(TimeZone.getDefault()).format(date);
	}
	
	/**
	 * Format yyyy-mm-dd
	 * 
	 * @param isoDateString
	 * @return Parsed date or null if a parse error occurs.
	 */
	public static Date parseIsoDate(final String isoDateString) {
		return parseIsoDate(isoDateString, TimeZone.getDefault());
	}
	
	/**
	 * Format yyyy-mm-dd
	 * 
	 * @param isoDateString
	 * @return Parsed date or null if a parse error occurs.
	 */
	public static Date parseIsoDate(final String isoDateString, final TimeZone timeZone) {
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(timeZone);
		Date date;
		try {
			date = df.parse(isoDateString);
		} catch (final ParseException ex) {
			return null;
		}
		return date;
	}
	
	/**
	 * Format: {@link DateFormats#ISO_TIMESTAMP_SECONDS}
	 * 
	 * @param isoDateString
	 * @return Parsed date or null if a parse error occurs.
	 */
	public static Date parseIsoSecond(final String isoDateString) {
		return parseIsoSecond(isoDateString, TimeZone.getDefault());
	}
	
	/**
	 * Format: {@link DateFormats#ISO_TIMESTAMP_SECONDS}
	 * 
	 * @param isoDateString
	 * @return Parsed date or null if a parse error occurs.
	 */
	public static Date parseIsoSecond(final String isoDateString, final TimeZone timeZone) {
		final DateFormat df = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_SECONDS);
		df.setTimeZone(timeZone);
		Date date;
		try {
			date = df.parse(isoDateString);
		} catch (final ParseException ex) {
			return null;
		}
		return date;
	}
	
	public static Date parseIsoDateTime(final String isoDateString, final String dateFormat) {
		final DateFormat df = new SimpleDateFormat(dateFormat);
		df.setTimeZone(TimeZone.getDefault());
		Date date;
		try {
			date = df.parse(isoDateString);
		} catch (final ParseException ex) {
			return null;
		}
		return date;
	}
	
	/**
	 * Format: {@link DateFormats#ISO_TIMESTAMP_MILLIS}
	 * 
	 * @param isoDateString
	 * @return Parsed date or null if a parse error occurs.
	 */
	public static Date parseIsoTimestamp(final String isoDateString, final TimeZone timeZone) {
		final DateFormat df = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_MILLIS);
		df.setTimeZone(timeZone);
		Date date;
		try {
			date = df.parse(isoDateString);
		} catch (final ParseException ex) {
			return null;
		}
		return date;
	}
	
	/**
	 * Format: {@link DateFormats#STANDARD_DATE}
	 * 
	 * @param isoDateString
	 * @return Parsed date or null if a parse error occurs.
	 */
	public static Date parseStandardDate(final String isoDateString) {
		return parseStandardDate(isoDateString, TimeZone.getDefault());
	}
	
	/**
	 * Format: {@link DateFormats#STANDARD_DATE}
	 * 
	 * @param isoDateString
	 * @return Parsed date or null if a parse error occurs.
	 */
	public static Date parseStandardDate(final String isoDateString, final TimeZone timeZone) {
		final DateFormat df = new SimpleDateFormat(DateFormats.STANDARD_DATE);
		df.setTimeZone(timeZone);
		Date date;
		try {
			date = df.parse(isoDateString);
		} catch (final ParseException ex) {
			return null;
		}
		return date;
	}
	
	public static String formatIsoTimePeriod(final Date fromDate, final Date toDate) {
		return formatIsoDate(fromDate) + ":" + formatIsoDate(toDate);
	}
	
	/**
	 * Format yyyy-mm-dd:yyyy-mm-dd
	 * 
	 * @param isoTimePeriodString
	 * @return Parsed time period or null if a parse error occurs.
	 */
	public static TimePeriod parseIsoTimePeriod(final String isoTimePeriodString, final TimeZone timeZone) {
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(timeZone);
		final String[] sa = isoTimePeriodString.split(":");
		if (sa.length != 2) {
			return null;
		}
		final Date fromDate = DateUtils.parseIsoDate(sa[0], DateUtils.UTC);
		final Date toDate = DateUtils.parseIsoDate(sa[1], DateUtils.UTC);
		if (fromDate == null || toDate == null) {
			return null;
		}
		return new TimePeriod(fromDate, toDate);
	}
	
	/**
	 * Output via FOR_TESTCASE_OUTPUT_FORMATTER for test cases.<br/>
	 * 
	 * @param dateHolder
	 * @return
	 */
	public static final String getForTestCase(final DateHolder dateHolder) {
		return FOR_TESTCASE_OUTPUT_FORMATTER.get().format(dateHolder.getDate());
	}
	
	/**
	 * Output via FOR_TESTCASE_OUTPUT_FORMATTER for test cases.
	 * 
	 * @param dateHolder
	 * @return
	 */
	public static final String getForTestCase(final Date date) {
		return FOR_TESTCASE_OUTPUT_FORMATTER.get().format(date);
	}
	
	public static final String getTimestampAsFilenameSuffix(final Date date) {
		if (date == null) {
			return "--";
		}
		return getFilenameFormatTimestamp(TimeZone.getDefault()).format(date);
	}
	
	public static final String getDateAsFilenameSuffix(final Date date) {
		if (date == null) {
			return "--";
		}
		return getFilenameFormatDate(TimeZone.getDefault()).format(date);
	}
	
	/**
	 * Returns a calendar instance. If a context user is given then the user's
	 * time zone and locale will be used if given.
	 */
	public static Calendar getCalendar() {
		return getCalendar(null, null);
	}
	
	/**
	 * Returns a calendar instance. If a context user is given then the user's
	 * time zone and locale will be used if given.
	 * 
	 * @param locale if given this locale will overwrite any the context user's
	 *            locale.
	 */
	public static Calendar getCalendar(final Locale locale) {
		return getCalendar(null, locale);
	}
	
	public static Calendar getCalendar(TimeZone timeZone, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		if (timeZone == null) {
			timeZone = TimeZone.getDefault();
		}
		return Calendar.getInstance(timeZone, locale);
	}
	
	public static Calendar getUTCCalendar() {
		return getCalendar(UTC, null);
	}
	
	/**
	 * If stopTime is before startTime a negative value will be returned.
	 * 
	 * @param startTime
	 * @param stopTime
	 * @return Duration in minutes or 0, if not computable (if start or stop
	 *         time is null or stopTime is before startTime).
	 */
	public static long getDuration(final Date startTime, final Date stopTime) {
		if (startTime == null || stopTime == null || stopTime.before(startTime) == true) {
			return 0;
		}
		final long millis = stopTime.getTime() - startTime.getTime();
		return millis / 60000;
	}
	
	/**
	 * @return Formatted string without seconds, such as 5:45.
	 * @param time in millis
	 */
	public static String formatDuration(final long milliSeconds) {
		final long duration = milliSeconds / 60000;
		final long durationHours = duration / 60;
		final long durationMinutes = (duration % 60);
		final StringBuffer buf = new StringBuffer(10);
		buf.append(durationHours);
		if (durationMinutes < 10)
			buf.append(":0");
		else
			buf.append(':');
		buf.append(durationMinutes);
		return buf.toString();
	}
	
	/**
	 * Initializes a new ArrayList with -1 ("--") and all 12 month with labels
	 * "01", ..., "12".
	 */
	public static List<LabelValueBean<String, Integer>> getMonthList() {
		final List<LabelValueBean<String, Integer>> list = new ArrayList<LabelValueBean<String, Integer>>();
		list.add(new LabelValueBean<String, Integer>("--", -1));
		for (int month = 0; month < 12; month++) {
			list.add(new LabelValueBean<String, Integer>(StringUtil.format2DigitNumber(month + 1), month));
		}
		return list;
	}
	
	/**
	 * @param year
	 * @param month 0-11
	 * @return "yyyy-mm"
	 */
	public static String formatMonth(final int year, final int month) {
		final StringBuffer buf = new StringBuffer();
		buf.append(year);
		if (month >= 0) {
			buf.append('-');
			final int m = month + 1;
			if (m <= 9) {
				buf.append('0');
			}
			buf.append(m);
		}
		return buf.toString();
	}
	
	/**
	 * Should be used application wide for getting and/or displaying the week of
	 * year!
	 * 
	 * @param date
	 * @return Return the week of year. The week of year depends on the Locale
	 *         set in the Configuration (config.xml). If given date is null then
	 *         -1 is returned. For "de" the first week of year is the first week
	 *         with a minimum of 4 days in the new year. For "en" the first week
	 *         of the year is the first week with a minimum of 1 days in the new
	 *         year.
	 * @see java.util.Calendar#getMinimalDaysInFirstWeek()
	 */
	public static int getWeekOfYear(final Date date) {
		if (date == null) {
			return -1;
		}
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * Should be used application wide for getting and/or displaying the week of
	 * year!
	 * 
	 * @param calendar (this methods uses the year, month and day of the given
	 *            Calendar)
	 * @return Return the week of year. The week of year depends on the Locale
	 *         set in the Configuration (config.xml). If given date is null then
	 *         -1 is returned. For "de" the first week of year is the first week
	 *         with a minimum of 4 days in the new year. For "en" the first week
	 *         of the year is the first week with a minimum of 1 days in the new
	 *         year.
	 * @see java.util.Calendar#getMinimalDaysInFirstWeek()
	 */
	public static int getWeekOfYear(final Calendar calendar) {
		if (calendar == null) {
			return -1;
		}
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, calendar.get(Calendar.MONDAY));
		cal.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * Should be used application wide for getting and/or displaying the week of
	 * year!
	 * 
	 * @param date
	 * @return Return the week of year. The week of year depends on the Locale
	 *         set in the Configuration (config.xml). If given date is null then
	 *         -1 is returned. For "de" the first week of year is the first week
	 *         with a minimum of 4 days in the new year. For "en" the first week
	 *         of the year is the first week with a minimum of 1 days in the new
	 *         year.
	 * @see java.util.Calendar#getMinimalDaysInFirstWeek()
	 */
	public static int getWeekOfYear(final DateTime date) {
		if (date == null) {
			return -1;
		}
		return getWeekOfYear(date.toDate());
	}
	
	/**
	 * @param d1
	 * @param d2
	 * @return True if the dates are both null or both represents the same day
	 *         (year, month, day) independant of the hours, minutes etc.
	 * @see DateHolder#isSameDay(Date)
	 */
	public static boolean isSameDay(final Date d1, final Date d2) {
		if (d1 == null) {
			if (d2 == null) {
				return true;
			} else {
				return false;
			}
		} else if (d2 == null) {
			return false;
		}
		final DateHolder dh = new DateHolder(d1);
		return dh.isSameDay(d2);
	}
	
	/**
	 * @param d1
	 * @param d2
	 * @return True if the dates are both null or both represents the same day
	 *         (year, month, day) independant of the hours, minutes etc.
	 * @see DateHolder#isSameDay(Date)
	 */
	public static boolean isSameDay(final DateTime d1, final DateTime d2) {
		if (d1 == null) {
			if (d2 == null) {
				return true;
			} else {
				return false;
			}
		} else if (d2 == null) {
			return false;
		}
		return d1.getYear() == d2.getYear() && d1.getDayOfYear() == d2.getDayOfYear();
	}
	
	public static boolean dateOfYearBetween(final int month, final int dayOfMonth, final int fromMonth, final int fromDayOfMonth, final int toMonth, final int toDayOfMonth) {
		if (fromMonth == toMonth) {
			if (month != fromMonth) {
				return false;
			}
			if (dayOfMonth < fromDayOfMonth || dayOfMonth > toDayOfMonth) {
				return false;
			}
		} else if (fromMonth < toMonth) {
			// e. g. APR - JUN
			if (month < fromMonth || month > toMonth) {
				// e. g. FEB or JUL
				return false;
			} else if (month == fromMonth && dayOfMonth < fromDayOfMonth) {
				return false;
			} else if (month == toMonth && dayOfMonth > toDayOfMonth) {
				return false;
			}
		} else if (fromMonth > toMonth) {
			// e. g. NOV - FEB
			if (month > toMonth && month < fromMonth) {
				// e. g. MAR
				return false;
			} else if (month == fromMonth && dayOfMonth < fromDayOfMonth) {
				return false;
			} else if (month == toMonth && dayOfMonth > toDayOfMonth) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sets given DateTime (UTC) as local time, meaning e. g. 08:00 UTC will be
	 * 08:00 local time.
	 * 
	 * @param dateTime
	 * @return
	 * @see DateTime#toString(String)
	 * @see DateUtils#parseIsoDate(String, TimeZone)
	 */
	public static long getDateTimeAsMillis(final DateTime dateTime) {
		final String isDateString = dateTime.toString(DateFormats.ISO_TIMESTAMP_MILLIS);
		final Date date = DateUtils.parseIsoTimestamp(isDateString, TimeZone.getDefault());
		return date.getTime();
	}
	
	public final static int convertCalendarDayOfWeekToJoda(final int calendarDayOfWeek) {
		if (calendarDayOfWeek == Calendar.SUNDAY) {
			return DateTimeConstants.SUNDAY;
		}
		return calendarDayOfWeek - 1;
	}
	
	public final static Date getToday() {
		return getDate(new Date());
	}
	
	public final static Date getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		return cal.getTime();				
	}
}
