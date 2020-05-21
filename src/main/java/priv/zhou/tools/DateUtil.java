package priv.zhou.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author zhou
 * @since 2019.03.11
 */
@SuppressWarnings("unused")
public class DateUtil {

	private DateUtil() {
	}

	/**
	 * 时间格式
	 */
	public final static String Y = "yyyy";

	public final static String YM = "yyyy-MM";

	public final static String YMD = "yyyy-MM-dd";

	public final static String HMS = "HH:mm:ss";

	public final static String YMDHMS = "yyyy-MM-dd HH:mm:ss";


	/**
	 * 默认格式
	 */
	private final static String DEFAULT_FORMAT = YMD;

	/**
	 * 默认单位
	 */
	private final static TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;


	public static String format() {
		return format(new Date());
	}

	public static String format(String format) {
		return format(new Date(), format);
	}

	/**
	 * 格式化时间成字符串（年月日）
	 */
	public static String format(Date date) {
		return format(date, DEFAULT_FORMAT);
	}


	/**
	 * 格式化时间Long值成字符串（年月日）
	 */
	public static String format(long date) {
		return format(new Date(date));
	}


	/**
	 * 格式化时间Long值成字符串
	 */
	public static String format(long date, String format) {
		return format(new Date(date), format);
	}


	/**
	 * 格式化时间成字符串
	 */
	public static String format(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}


	/**
	 * 解析时间字符串成时间（年月日）
	 */
	public static Date parse(String dateStr) {
		return parse(dateStr, DEFAULT_FORMAT);
	}


	/**
	 * 解析时间字符串成时间
	 */
	public static Date parse(String dateStr, String format) {
		try {
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (Exception e) {
			throw new RuntimeException("DateUtil.getDateTime(dateStr = " + dateStr + ", format = " + format + ")");
		}
	}


	/**
	 * 获取两个时间内相差的秒数
	 */
	public static int differ(Date beginTime, Date endTime) {
		return differ(beginTime, endTime, DEFAULT_UNIT);
	}


	/**
	 * 获取两个时间内相差的TimeUnit数
	 * 结束时间减去开始时间
	 */
	public static int differ(Date beginTime, Date endTime, TimeUnit timeUnit) {
		return differ(beginTime, endTime, timeUnit.toMillis(1));
	}


	private static int differ(Date beginTime, Date endTime, Long millis) {
		if (beginTime == null || endTime == null) {
			return 0;
		}
		return (int) ((endTime.getTime() - beginTime.getTime()) / millis);
	}


	/**
	 * 剩余今天剩余的毫秒数
	 */
	public static Integer restOfToday() {
		Date now = new Date();
		return differ(now, parse(format(now) + " 23:59:59", YMDHMS), DEFAULT_UNIT);
	}


	/**
	 * 增加天数
	 */
	public static Date add(Date date, int dayNum) {
		return add(date, Calendar.DAY_OF_MONTH, dayNum);
	}


	/**
	 * 增加指定字段的时间
	 */
	public static Date add(Date date, int calendarField, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calendarField, num);
		return cal.getTime();
	}


	/**
	 * 获取指定日期月份最大天数
	 */
	public int maxDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}


	/**
	 * 获取指定月份的日历信息
	 *
	 * @param month 月，如(2014-05)
	 * @return
	 */
	public static Date[] getDates(String month) {
		try {
			// 获得第一天
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date first = sdf.parse(month);

			// 获取本月天数
			Calendar cal = Calendar.getInstance();
			cal.setTime(first);
			int maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			Date[] dates = new Date[maxDate];
			for (int i = 0; i < maxDate; i++) {
				dates[i] = new Date(cal.getTime().getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			return dates;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public static String formatWeek(Date date) {
		return new SimpleDateFormat("E", Locale.CHINESE).format(date);
	}


	/**
	 * 时间是否在一个区间内
	 */
	public static boolean inRegion(Date targetTime, Date startTime, Date endTime) {
		return targetTime.getTime() > startTime.getTime() && targetTime.getTime() < endTime.getTime();
	}
}

