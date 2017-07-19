//**************************
//* Time Module			   *					
//**************************
package shady.common.sunalg;
import java.util.*;
public class DateUtils {
	
	private static class DateUtilsSingleton {
		private static DateUtils instance = new DateUtils();
	}
	
	public static DateUtils getInstance() {
		return DateUtilsSingleton.instance;
	}
	

	
	private double year, month, day, hour, minute, second, dst;
	
	private DateUtils() {
		// do nothing
	}

	public void setSystemTime()
	{
		
	    Calendar c = Calendar.getInstance();

		year = c.get(Calendar.YEAR);
		double simmonth = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH); 
		hour =  c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		second = c.get(Calendar.SECOND);
		month = simmonth+1;
		double dstmill =c.get(Calendar.DST_OFFSET); //
		dst = dstmill/3600000;
	} 
	
	public double getYear()
	{
		return year;
	}
	
	
	public double getMonth()
	{
		return month;
	}
	
	public double getDay()
	{
		return day; 
	}
	public double getHour()
	{
		return hour; //24 hour format
	}
	public double getMinute()
	{
		return minute;
	}
	public double getSecond()
	{
		return second;
	}
	public double getDST()
	{
		return dst;
	}
	
	
	
	
	
		
	    
						} //end class DateUtils
	  







