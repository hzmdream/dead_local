package dl.dlutils.utils.base.date;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class TimePeriod implements Serializable {
	
	private static final long serialVersionUID = -4928251035721502776L;
	
	private Date fromDate;
	
	private Date toDate;
	
	private boolean marker;
	
	public static long getDuration(final Date fromDate, final Date toDate) {
		if (fromDate == null || toDate == null || toDate.before(fromDate) == true) {
			return 0;
		}
		long millis = toDate.getTime() - fromDate.getTime();
		return millis;
	}
	
	/**
	 * hoursOfDay = 24; minHoursOfDaySeparation = 0;
	 * 
	 * @see #getDurationFields(long, int, int)
	 */
	public static int[] getDurationFields(long millis) {
		return getDurationFields(millis, 24);
	}
	
	/**
	 * minHoursOfDaySeparation = 0;
	 * 
	 * @see #getDurationFields(long, int, int)
	 */
	public static int[] getDurationFields(long millis, int hoursOfDay) {
		return getDurationFields(millis, hoursOfDay, 0);
	}
	
	/**
	 * Gets the duration of this time period.
	 * 
	 * @param hoursOfDay Hours of day is for example 8 for a working day.
	 * @param minHours4DaySeparation If minHours is e. g. 48 then 48 hours will
	 *            result in 0 days and 48 hours independent of the hoursOfDay.
	 *            (Depending on the scope minHoursOfDay is more convenient to
	 *            read.). If minHours is than zero, no seperation will be done.
	 * @param duration in millis.
	 * @return int array { days, hours, minutes};
	 */
	public static int[] getDurationFields(long millis, int hoursOfDay, int minHours4DaySeparation) {
		long duration = millis / 60000;
		int hours = (int) duration / 60;
		int minutes = (int) duration % 60;
		int days = 0;
		if (minHours4DaySeparation >= 0 && hours >= minHours4DaySeparation) {
			// Separate the days for more than 24 hours (=3 days):
			days = hours / hoursOfDay;
			hours = hours % hoursOfDay;
		}
		return new int[] { days, hours, minutes };
	}
	
	public TimePeriod() {
		this(null, null, false);
	}
	
	public TimePeriod(Date fromDate, Date toDate) {
		this(fromDate, toDate, false);
	}
	
	public TimePeriod(Date fromDate, Date toDate, boolean marker) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.marker = marker;
	}
	
	/**
	 * hoursOfDay = 24; minHoursOfDaySeparation = 0;
	 * 
	 * @see #getDurationFields(long, int, int)
	 */
	public int[] getDurationFields() {
		return getDurationFields(24);
	}
	
	/**
	 * minHoursOfDaySeparation = 0;
	 * 
	 * @see #getDurationFields(long, int, int)
	 */
	public int[] getDurationFields(int hoursOfDay) {
		return getDurationFields(hoursOfDay, 0);
	}
	
	/**
	 * @see #getDurationFields(long, int, int)
	 */
	public int[] getDurationFields(int hoursOfDay, int minHours4DaySeparation) {
		return getDurationFields(getDuration(), hoursOfDay, minHours4DaySeparation);
	}
	
	/**
	 * Duration in millis.
	 * 
	 * @return
	 */
	public long getDuration() {
		return getDuration(fromDate, toDate);
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public void setMarker(boolean marker) {
		this.marker = marker;
	}
	
	/**
	 * For storing time period collisions of time sheets.
	 * 
	 * @return
	 */
	public boolean getMarker() {
		return marker;
	}
	
	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this);
		sb.append("from", DateUtils.formatAsUTC(fromDate));
		sb.append("to", DateUtils.formatAsUTC(toDate));
		return sb.toString();
	}
	
}
