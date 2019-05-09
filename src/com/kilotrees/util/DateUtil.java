package com.kilotrees.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	//取日期的年月日部
	public static String getShortDateString(java.util.Date d)
	{
		if(d == null)
			return "null";
		SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd");
		return fm.format(d);
	}
	
	public static String getDateString(java.util.Date d)
	{
		if(d == null)
			return "null";
		SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		return fm.format(d);
	}
	
	public static boolean isSameDate(java.util.Date d1,java.util.Date d2)
	{
		String sd1 = getShortDateString(d1);
		String sd2 = getShortDateString(d2);
		return sd1.equals(sd2);
	}
	
	public static String getDateBeginString(java.util.Date d)
	{
		if(d == null)
			return "null";
		SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd");
		return fm.format(d);
	}
	
	public static void main(String[] argv)
	{
		String sDate = getDateString(new Date());
		System.out.println(sDate);
	}
}
