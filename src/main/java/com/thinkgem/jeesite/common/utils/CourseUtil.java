package com.thinkgem.jeesite.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.thinkgem.jeesite.modules.calendar.dao.CourseCalendarDao;
import com.thinkgem.jeesite.modules.calendar.entity.CourseCalendar;

public class CourseUtil {
	private static CourseCalendarDao courseCalendarDao = SpringContextHolder.getBean(CourseCalendarDao.class);
	public static Map<String,String> schoolRootMap = new HashMap<String,String>();
	
	static{
		schoolRootMap.put("01", "A栋");
		schoolRootMap.put("02", "B栋");
		schoolRootMap.put("03", "C栋");
	}
	
	public static String jie(String $j)
	{
	        if($j.equals("1"))
	                return "1-2节";
	        if($j.equals("2"))
	                return "3-4节";
	        if($j.equals("3"))
	                return "5-6节";
	        if($j.equals("4"))
	                return "7-8节";
	        if($j.equals("5"))
	                return "9-10节";
	        if($j.equals("6"))
                return "11-12节";
			return $j;
	}
	//返回周
	public static String zhou(String $z)
	{
	        if($z.equals("1"))
	                return "周一";
	        if($z.equals("2"))
	                return "周二";
	        if($z.equals("3"))
	                return "周三";
	        if($z.equals("4"))
	                return "周四";
	        if($z.equals("5"))
	                return "周五";
	        if($z.equals("6"))
	                return "周六";
	        if($z.equals("7"))
	                return "周日";
			return $z;
	}
	
	public static String jiaoxuelou(String school)
	{
	        if(!StringUtils.isEmpty(school)&&school.length()==5) {
	        	return schoolRootMap.get(school.substring(0,2));
	        }
	        return school;
	}
	
	public static String jiaoshi(String school)
	{
	        if(!StringUtils.isEmpty(school)&&school.length()==5) {
	        	return school.substring(2);
	        }
	        return school;
	}
	
	
	
	public static Map<String,String> GetTimeCol(String $time_add){
	    Map<String,String> $time = new HashMap<String,String>();
	    $time.put("start", $time_add.substring(0, 4));
	    $time.put("end", $time_add.substring(5, 9));
	    $time.put("term", $time_add.substring(10, 12));
	    $time.put("school", $time_add.substring(12,17));
	    $time.put("week", $time_add.substring(17, 19));
	    $time.put("jie", $time_add.substring(19, 20));
	    $time.put("zhou", $time_add.substring(20, 21));
	    return $time;
	}
	
	public static String getTimeAdd(String timeAdd) {
		CourseCalendar courseCalendar = courseCalendarDao.get("1");
		int calendarYear = Integer.valueOf(courseCalendar.getCalendarYear());
		int calendarMonth = Integer.valueOf(courseCalendar.getCalendarMonth()); // 月
		int calendarDay = Integer.valueOf(courseCalendar.getCalendarDay());
		int week = Integer.valueOf(GetTimeCol(timeAdd).get("week"));
		int xq = Integer.valueOf(GetTimeCol(timeAdd).get("zhou"));
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendarYear, calendarMonth - 1, calendarDay);
		calendar.add(calendar.WEEK_OF_MONTH, week - 1);
		calendar.add(calendar.DAY_OF_WEEK, xq - 1);
		Date date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}
	
	public static String getCompleteTimeAdd(String timeAdd) {
		String school = GetTimeCol(timeAdd).get("school");
		String $j = GetTimeCol(timeAdd).get("jie");
		String xq = GetTimeCol(timeAdd).get("zhou");
		//getTimeAdd(timeAdd)
		return getTimeAdd(timeAdd).concat(" ").concat(zhou(xq)).concat(" ").concat(jiaoxuelou(school)).concat(jiaoshi(school)).concat("教室").concat(jie($j));
	}

	public static void main(String[] args) {
		int calendarYear = 2019;
		int calendarMonth = 2; // 月
		int calendarDay = 13; // 日
		int week = 2;
		int xq = 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendarYear, calendarMonth - 1, calendarDay);
		calendar.add(calendar.WEEK_OF_MONTH, week - 1);
		calendar.add(calendar.DAY_OF_WEEK, xq - 1);
		Date date = calendar.getTime();
		System.out.println(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String str = format.format(date);
		System.out.println(str);
		String timeAdd = "2018-2019-01015010327";
		System.out.println(getCompleteTimeAdd(timeAdd));
	}
	
	public static String addDate(String today,int day) {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		 Date date = null;  
		 try {  
		     date = format.parse(today);  
		 } catch (ParseException e) {  
		     // TODO Auto-generated catch block  
		     e.printStackTrace();  
		 }  
		   
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.add(Calendar.DATE, day - 1);
		 return format.format(calendar.getTime());
	}
}
