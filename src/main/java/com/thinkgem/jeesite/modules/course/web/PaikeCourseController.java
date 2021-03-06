/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.course.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CourseUtil;
import com.thinkgem.jeesite.common.utils.RegexUtils;
import com.thinkgem.jeesite.common.utils.StudentUtil;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportResult;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.calendar.entity.CourseCalendar;
import com.thinkgem.jeesite.modules.calendar.service.CourseCalendarService;
import com.thinkgem.jeesite.modules.course.entity.Course;
import com.thinkgem.jeesite.modules.course.entity.CourseClass;
import com.thinkgem.jeesite.modules.course.entity.CourseSchedule;
import com.thinkgem.jeesite.modules.course.entity.CourseScheduleExt;
import com.thinkgem.jeesite.modules.course.service.CourseClassService;
import com.thinkgem.jeesite.modules.course.service.CourseScheduleService;
import com.thinkgem.jeesite.modules.course.service.CourseService;
import com.thinkgem.jeesite.modules.course.service.CourseTeachingModeService;
import com.thinkgem.jeesite.modules.course.service.TimetableService;
import com.thinkgem.jeesite.modules.school.entity.SchoolRoot;
import com.thinkgem.jeesite.modules.school.service.SchoolRootService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SysConfigService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.web.TreeLink;
import com.thinkgem.jeesite.modules.teacher.entity.Teacher;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherClass;
import com.thinkgem.jeesite.modules.teacher.service.TeacherClassService;
import com.thinkgem.jeesite.modules.teacher.service.TeacherService;

/**
 * 课程基本信息Controller
 * @author 赵俊飞
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/course/paike")
public class PaikeCourseController extends BaseController {
	@Autowired
	private CourseCalendarService courseCalendarService;

	@Autowired
	private CourseScheduleService courseScheduleService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TeacherClassService teacherClassService;
	@Autowired
	private DictService dictService;
	@Autowired
	private SchoolRootService schoolRootService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private CourseClassService courseClassService;
	@Autowired
	private CourseTeachingModeService courseTeachingModeService;
	@Autowired
	private TimetableService timetableService;
	@RequiresPermissions("course:paike:edit")
	@RequestMapping(value = "lock")
	public String lock(CourseCalendar courseCalendar, Model model) {
		
		String yearTerm = sysConfigService.getModule(Global.SYSCONFIG_COURSE).getTermYear();
		model.addAttribute("yearTerm",yearTerm);
		model.addAttribute("courseCalendar", courseCalendarService.systemConfig());
		return "modules/paike/LockCourse";
	}
	
	@RequiresPermissions("course:paike:edit")
	@RequestMapping(value = "ajaxAddLock")
	@ResponseBody
	public String addlock(String time_add,String tips,@RequestParam(value="w",required=false,defaultValue="0")int w, Model model) {
		
		int s = Integer.valueOf( CourseUtil.GetTimeCol(time_add).get("week"));
		if(StringUtils.isEmpty(w)||w==0) {
			w = s;
		}
		boolean flag = false;
		for(;s<=w;s++) {
			String week="";
			if(s<=9) {
				week = "0".concat(String.valueOf(s));
			}else {
				week =  String.valueOf(s);
			}
			String zhou = time_add.substring(19);
			
			time_add = time_add.substring(0,17).concat(week).concat(zhou);
			CourseSchedule courseSchedule = courseScheduleService.getByAddTime(time_add);
			if(!org.springframework.util.StringUtils.isEmpty(courseSchedule)&&courseSchedule.getScLock().equals("1")) {
				courseSchedule.setScLock("0");
				courseSchedule.setTips(tips);
				courseScheduleService.save(courseSchedule);
				flag = true;
			}
		}
		
		if(flag) {
			return "1";
		}
		return "0";
	}
	
	/**
	 * 新增排课页面
	 * @param courseCalendar
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addCourse")
	public String addCourse(CourseCalendar courseCalendar, Model model) {
		
		String yearTerm= sysConfigService.getModule(Global.SYSCONFIG_COURSE).getTermYear();
		
		model.addAttribute("yearTerm",yearTerm);
		model.addAttribute("courseCalendar", courseCalendarService.systemConfig());
		return "modules/paike/AddCourse";
	}
	/**
	 * 添加排课信息
	 * @param time_add
	 * @param tips
	 * @param model
	 * @return
	 */
	@RequiresPermissions("course:paike:edit")
	@RequestMapping(value = "ajaxAddCourse")
	@ResponseBody
	public String ajaxAddCourse(String time_add,String student_id,String course_id,String tips,@RequestParam(value="w",required=false,defaultValue="0")int w, Model model) {
		Course course = null;
		if(StringUtils.isEmpty(course_id)) {
			return "9";
		}
		course = courseService.get(course_id);
		if(course.getCursProperty().equals(Course.COURSE_PROPERTY_SELECT)) {
			student_id = "00000000";
		}
		if(!course.getCursProperty().equals(Course.COURSE_PROPERTY_SELECT)&&StringUtils.isEmpty(student_id)) {
			CourseClass courseClass = new CourseClass();
			courseClass.setCourse(course);
			List<CourseClass> ccs = courseClassService.findByParentIdsLike(courseClass);
			StringBuffer sb = new StringBuffer();
			for(CourseClass cs : ccs) {
				String classId = cs.getClassId();
				if(!StringUtils.isEmpty(classId)) {
					sb.append(classId);
					sb.append(",");
				}
			}
			if(sb.length()>0) {
				student_id = student_id.substring(0,sb.length()-1);
			}
		}
		int s = Integer.valueOf( CourseUtil.GetTimeCol(time_add).get("week"));
		if(StringUtils.isEmpty(w)||w==0) {
			w = s;
		}
		boolean flag = false;
		for(;s<=w;s++) {
			String week="";
			if(s<=9) {
				week = "0".concat(String.valueOf(s));
			}else {
				week =  String.valueOf(s);
			}
			String zhou = time_add.substring(19);
			
			time_add = time_add.substring(0,17).concat(week).concat(zhou);
			CourseSchedule courseSchedule = courseScheduleService.getByAddTime(time_add);
			if(!org.springframework.util.StringUtils.isEmpty(courseSchedule)&&courseSchedule.getScLock().equals("1")) {
				courseSchedule.setScLock("2");
				courseSchedule.setCourseClass(student_id);
				courseSchedule.setCourseId(course_id);
				courseSchedule.setTips(tips);
				courseScheduleService.save(courseSchedule);
				flag = true;
			}
		}
		
		if(flag) {
			course.setCursStatus(Course.PAIKE_STATUS_YI_PAIKE);
			courseService.save(course);
			return "1";
		}
		
		return "0";
	}
	
	
	@RequiresPermissions("course:paike:edit")
	@RequestMapping(value = "ajaxDeleteCourse")
	@ResponseBody
	public String ajaxDeleteCourse(String time_add,String week, Model model) {
		CourseSchedule courseSchedule = courseScheduleService.getByAddTime(time_add);
		if(!org.springframework.util.StringUtils.isEmpty(courseSchedule)) {
			courseSchedule.setScLock("1");
			courseSchedule.setCourseId("00000000");
			courseSchedule.setTips("");
			courseScheduleService.save(courseSchedule);
			return "1";
		}
		return "0";
	}
	
	
	@RequiresPermissions("course:paike:edit")
	@RequestMapping(value = "ajaxDeleteLock")
	@ResponseBody
	public String dellock(String time_add, Model model) {
		CourseSchedule courseSchedule = courseScheduleService.getByAddTime(time_add);
		if(!org.springframework.util.StringUtils.isEmpty(courseSchedule)) {
			courseSchedule.setScLock("1");
			courseSchedule.setTips("");
			courseSchedule.setCourseClass("");
			courseSchedule.setCourseId("");
			courseScheduleService.save(courseSchedule);
			return "1";
		}
		return "0";
	}
	
	
	@RequestMapping(value = "ajaxChangeTable")
	public void ajaxChangeTable(String time_add,HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter ps = response.getWriter();
		List<CourseSchedule> courseSchedules = courseScheduleService.findListByTimeAdd(time_add);
		for(CourseSchedule courseSchedule:courseSchedules) {
			ps.write(courseSchedule.getScLock());
			if(courseSchedule.getScLock().equals("1")) {
				ps.write("<div class=\"course_text\">教师:</div>");
				ps.write("<div class=\"course_text\">课程:</div>");
				ps.write("<div class=\"course_text\"></div>");
				ps.write("<div class=\"course_text\">备注:</div>");
				
			}else {
				//科目号,理论不应该出现异常现象,不应该出现空指针现象
				
				String courseClass = courseSchedule.getCourseClass();
				
				if(courseSchedule.getScLock().equals("0")||courseClass.length()<7) {
					ps.write("<div class=\"course_text\"></div>");
					ps.write("<div class=\"course_text\">"+courseClass+"</div>");
				}else {
					Course course = courseService.get(courseSchedule.getCourseId());
					if(!StringUtils.isEmpty(course)) {
						ps.write("<div class=\"course_text\">课程:"+course.getCursName()+"</div>");
						Teacher teacher = teacherService.getTeacherByTeacherNumber(course.getTeacher().getTeacherNumber());
						ps.write("<div class=\"course_text\">教师:"+teacher.getTchrName()+"</div>");
					}
					
					if(!StringUtils.isEmpty(courseClass)) {
						if(courseClass.indexOf("00000000")>-1) {
							ps.write("<div class=\"course_text\">公共课</div>");
						}else {
							String[] courseArray = courseClass.split(",");
							StringBuilder sb = new StringBuilder();
							for(int i=0;i<courseArray.length;i++) {
								String str = courseArray[i];
								Office clazz = officeService.get(str);
								String officeId = str.substring(4,6);
								Office office = officeService.get(officeId);
								String company = "";
								String major = "";
								if(!StringUtils.isEmpty(office)) {
									major = office.getName();
									company = office.getParent().getName();
								}
								sb.append("<a title=\""+company + "," + major + "," + clazz.getName() + "\">"+clazz.getName()+"</a>");
								if(i%2==1) {
									sb.append("<br>");
								}else {
									sb.append(",");
								}
								
							}
							sb.deleteCharAt(sb.length() - 1);
							ps.write("<div class=\"course_text\">"+ sb.toString() +"</div>");
						}
						
					}
					
				}
				
				
				ps.write("<div class=\"course_text\">备注:"+courseSchedule.getTips()+"</div>");
				
			}
			ps.write("@");
		}
		ps.flush();
		ps.close();
	}
	
	
	@RequestMapping(value = "ajaxStudentCourse")
	public void ajaxStudentCourse(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter ps = response.getWriter();
		
		User student = UserUtils.getUser();
		
		Office clazz = student.getClazz();
		if(!StringUtils.isEmpty(clazz)) {
			CourseScheduleExt courseScheduleExt = new CourseScheduleExt();
			courseScheduleExt.setCourseClass(student.getClazz().getId());
			List<CourseScheduleExt> courseSchedules = courseScheduleService.getCourseScheduleExt(courseScheduleExt);
			for(CourseScheduleExt courseSchedule:courseSchedules) {
				ps.write(courseSchedule.getScLock());
				if(courseSchedule.getScLock().equals("1")) {
					ps.write("<div class=\"course_text\">教师:</div>");
					ps.write("<div class=\"course_text\">课程:</div>");
					ps.write("<div class=\"course_text\"></div>");
					ps.write("<div class=\"course_text\">备注:</div>");
					
				}else {
					//科目号,理论不应该出现异常现象,不应该出现空指针现象
					String courseId = courseSchedule.getCourseId();
					String courseClass = courseSchedule.getCourseClass();
					Course course = courseService.getCourseByCourseId(courseId);
					
					ps.write("<div class=\"course_text\">课程:"+course.getCursName()+"</div>");
					
					if(courseSchedule.getScLock().equals("0")||courseClass.length()<7) {
						ps.write("<div class=\"course_text\"></div>");
						ps.write("<div class=\"course_text\">"+courseClass+"</div>");
					}else {
						
						Teacher teacher = teacherService.getTeacherByTeacherNumber(course.getTeacher().getTeacherNumber());
						ps.write("<div class=\"course_text\">教师:"+teacher.getTchrName()+"</div>");
						String grade = courseClass.substring(0,4);
						String school = courseClass.substring(4,5);
						String major = courseClass.substring(5,7);
						String c = courseClass.substring(7);
						Office company = officeService.get(school);
						Office office = officeService.get(major);
						ps.write("<div class=\"course_text\">"+company.getName()+ " " + office.getName() + " "+c +"</div>");
					}
					
					ps.write("<div class=\"course_text\">备注:"+courseSchedule.getTips()+"</div>");
					
				}
				ps.write("@");
			}
		}
		ps.flush();
		ps.close();
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxAllClassByCourseId")
	public List<TeacherClass> ajaxAllClassByCourseId( HttpServletRequest request, HttpServletResponse response) {
		String courseId = request.getParameter("courseId");
		Course course = courseService.get(courseId);
		List<TeacherClass> tcs = new ArrayList<TeacherClass>();
		if(!StringUtils.isEmpty(course)) {
			Teacher teacher = course.getTeacher();
			TeacherClass teacherClass = new TeacherClass();
			teacherClass.setTeacherNumber(teacher.getTeacherNumber());
			tcs = teacherClassService.findByParentIdsLike(teacherClass);
		}
		return tcs;
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxAllCourse")
	public List<TreeLink> ajaxAllCourse( HttpServletRequest request, HttpServletResponse response) {
		
		String termYear = request.getParameter("termYear");
		String cursProperty = request.getParameter("cursProperty");
		List<Course> list1  = null;
		Course course = new Course();
		course.setCursYearTerm(termYear);

		if(!isAdmin()) {
			course.setTeacher(UserUtils.getTeacher());
		}
		course.setCursProperty("99");
		if(!StringUtils.isEmpty(cursProperty)&&cursProperty.equals(Course.COURSE_PROPERTY_SELECT)) {
			course.setCursProperty(cursProperty);
		}
		list1 = courseService.findByParentIdsLike(course);
		List<TreeLink> treeLinks1 = new ArrayList<TreeLink>();
		for(Course c:list1) {
			TreeLink treeLink = new TreeLink();
			treeLink.setValue(c.getId());
			Teacher teacher = c.getTeacher();
			if(!StringUtils.isEmpty(teacher)) {
				treeLink.setName(c.getCursName().concat("("+c.getCursClassHour()+")").concat("|").concat(teacher.getTchrName()));
			}
			treeLinks1.add(treeLink);
		}
		return treeLinks1;
	}
	

	
	@RequestMapping(value = "view")
	public String view() {
		return "modules/paike/ImportView";
	}
	
	@RequestMapping(value = "import")
	public String importFile(MultipartFile file,String currTerm,HttpServletRequest request, HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		Dict dict = new Dict();
		dict.setType("course_curs_type");
		List<Dict> courseCurrsTypes = dictService.findByParentIdsLike(dict);
		dict.setType("course_curs_form");
		List<Dict> courseCurrsForms = dictService.findByParentIdsLike(dict);
		
		dict.setType("sys_user_type");
		List<Dict> userTypes = dictService.findByParentIdsLike(dict);
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			
			ImportExcel importExcel = new ImportExcel(file,3);
			String[][] curriculumPlans = importExcel.importFile();
			for(String[] curriculumPlan:curriculumPlans) {
				try {
					String id = curriculumPlan[0];
					String office_name = curriculumPlan[1];
					String major_name = curriculumPlan[2];
					String curs_num = curriculumPlan[3];
					String curs_name = curriculumPlan[4];
					String curs_type = curriculumPlan[5];// 课程类型
					String assessment_type = curriculumPlan[6];// 考核类型
					String clazz = RegexUtils.getUnChinese(curriculumPlan[7]);
					String count = curriculumPlan[8];
					String tchr_name = curriculumPlan[9];
					String tchr_title = curriculumPlan[10];
					String user_type = curriculumPlan[11];
					String week = curriculumPlan[12];
					String week_count = curriculumPlan[13];
					String curs_class_hour = curriculumPlan[14];
					String remark = curriculumPlan[15];

					List<String> cls = new ArrayList<String>();

					if (clazz.indexOf("、") > -1 && clazz.indexOf("-") > -1) {
						String[] sss = clazz.split("、");
						for (String s : sss) {
							if (s.indexOf("-") > -1) {
								String[] ss = s.split("-");
								for (int k = Integer.valueOf(ss[0]); k <= Integer.valueOf(ss[1]); k++) {
									cls.add(String.valueOf(k));
								}
							} else {
								cls.add(s);
							}
						}
					} else if (clazz.indexOf("、") > -1) {
						String[] ss = clazz.split("、");
						for (String s : ss) {
							cls.add(s);
						}
					} else if (clazz.indexOf("-") > -1) {
						String[] ss = clazz.split("-");
						for (int k = Integer.valueOf(ss[0]); k <= Integer.valueOf(ss[1]); k++) {
							cls.add(String.valueOf(k));
						}
					} else {
						cls.add(clazz);
					}
					
					if(major_name.indexOf("/")>-1) {
						major_name = major_name.split("/")[0];
					}
					
					Office major = officeService.getOfficeByName(major_name);
					// 判断任课教师是否存在
					User user = systemService.isExisUser(office_name, curs_type, tchr_name, tchr_title, major);
					
					for (String cs : cls) {
						String year = "20" + cs.substring(0, 2);
						String classNo = cs.substring(2);
						Office cl = StudentUtil.createClasses(year, major.getId(), classNo);
						TeacherClass teacherClass = new TeacherClass();
						teacherClass.setTeacherNumber(user.getNo());
						teacherClass.setClazz(cl);
						TeacherClass entityteacherClass = teacherClassService.get(teacherClass);
						if (StringUtils.isEmpty(entityteacherClass)) {
							teacherClassService.save(teacherClass);
						}
					}
					Teacher tt = new Teacher();
					tt.setUser(user);
					Course course = new Course();
					course.setTeacher(tt);
					course.setCursNum(curs_num);
					course.setCursName(curs_name);
					Course entity = courseService.getCourse(course);
					if (StringUtils.isEmpty(entity)) {
						// 新增课程相关信息
						entity = new Course();
						entity.setId(systemService.getSequence("serialNo10"));
						entity.setIsNewRecord(true);
						entity.setCursMajor(major_name);
						entity.setCursYearTerm(currTerm);
						entity.setCursWeekTotal(new Double(week_count).intValue()+"");
						entity.setCursWeekHour(new Double(week).intValue()+"");
						entity.setTeacher(tt);
						entity.setCursNum(curs_num);
						entity.setCursName(curs_name);
						entity.setCursTotal(new Double(count).intValue()+"");
						entity.setCursStatus(Course.PAIKE_STATUS_WEI_PAIKE);
					
						curs_type = curs_type.substring(0, 2); 
						String cursValue = "other";
						for(Dict d:courseCurrsTypes) {
							if(d.getLabel().indexOf(curs_type)>-1) {
								cursValue = d.getValue();
								break;
							}
						}
						entity.setCursType(cursValue);
						
						String cursForm = "99";
						for(Dict d:courseCurrsForms) {
							if(d.getLabel().indexOf(assessment_type)>-1) {
								cursForm = d.getValue();
								break;
							}
						}
						entity.setCursForm(cursForm);
			
						entity.setCursClassHour(new Double(curs_class_hour).intValue()+"");
						entity.setRemarks(remark);
						courseService.save(entity);
					}
					successNum++;
				} catch (Exception e) {
					failureMsg.append("<br/>课程信息异常: "+curriculumPlan+" ; "+e.getMessage());
					failureNum++;
					
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}

			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条课程信息"+failureMsg);
		}catch (Exception ex) {
			ex.printStackTrace();
			addMessage(model, "导入失败！失败信息："+ex.getMessage());
		}
		return "modules/paike/ImportView";
	}
	
	
	
	
		
	
	public void auto(HttpServletRequest request) {
		
		Course course = new Course();
		//如何选择学院,进行如下操作
		if(!StringUtils.isEmpty(request.getParameter("officeId"))) {
			List<Office> majors = officeService.findByParentId(request.getParameter("officeId"));
			if (!StringUtils.isEmpty(majors) && majors.size() > 0) {
				List<String> item = new ArrayList<String>();
				for(Office major:majors) {
					if(!StringUtils.isEmpty(request.getParameter("majorId")) && request.getParameter("majorId").equals(major.getId())) {
						item.add(major.getName());
						break;
					}
					item.add(major.getName());
				}
				course.setItem(item);
			}
		}
		course.setCursStatus("00");
		
		List<Course> courses = courseService.findByParentIdsLike(course);
		//需要进行排课的课程大概有
		logger.info("需要排课的课程大概有:{},具体需要排课总数:{}",courses,courses.size());
		//计算可排课教室,默认显示全部教室.管理员显示全部教室
		List<TreeLink> treelinks = schoolRootService.treeLinkId();
		List<String> schoolRoots = new ArrayList<String>();
		for(TreeLink treeLink:treelinks) {
			schoolRoots.add(treeLink.getValue());
		}
		//zhaojunfei
		CourseSchedule courseSchedule = new CourseSchedule();
		courseSchedule.setSchoolRoots(schoolRoots);
		List<CourseSchedule> courseSchedules = courseScheduleService.auto(courseSchedule);
		
		Iterator<CourseSchedule> its = courseSchedules.iterator();
		Iterator<Course> itcs = courses.iterator();
		while(its.hasNext()) {
			CourseSchedule cs = its.next();
			String $time_add = cs.getTimeAdd();
			Map<String,String> timeAddMap = CourseUtil.GetTimeCol($time_add);
			String schoolId = timeAddMap.get("school");
			SchoolRoot schoolRoot = schoolRootService.get(schoolId);
			while(itcs.hasNext()) {
				
				Course c = itcs.next();
				//1。确定课程类型 ,2.确定人数
				int cursTotal = Integer.valueOf(c.getCursTotal());//人数
				int weekTotal = Integer.valueOf(c.getCursWeekTotal());//周数
				int weekHour = Integer.valueOf(c.getCursWeekHour());//周学时
				int cursClassHour = Integer.valueOf(c.getCursClassHour());//总学时
				//课程人数小于40
				int total = schoolRoot.getTotal();
				//确定了人数
				int retTotal = fenpeibanji(cursTotal);
				if (total <= retTotal && retTotal != 0) {
					
				}
				
			}
		}
	}
	
	
	
	public int fenpeibanji(int total) {
		//40
		if (total < 40) {
			return 40;
		} else if (40 < total && total < 60) {
			return 60;
		} else if (60 < total && total < 68) {
			return 68;
		} else if (68 < total && total < 80) {
			return 80;
		} else if (80 < total && total < 90) {
			return 90;
		} else if (90 < total && total < 124) {
			return 124;
		} else if (90 < total && total < 124) {
			return 124;
		} else if (124 < total && total < 132) {
			return 132;
		} else if (132 < total && total < 133) {
			return 124;
		} else if (133 < total && total < 134) {
			return 133;
		} else if (134 < total && total < 135) {
			return 135;
		} else if (135 < total && total < 186) {
			return 186;
		} else if (186 < total && total < 190) {
			return 190;
		}
		return 0;
	}
	
	@RequestMapping(value = "timetable")
	public String timetable(HttpServletRequest request, HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		return "modules/paike/Timetable";
	}
	
	@RequestMapping(value = "importTimetable")
	public String importTimetable(MultipartFile file,HttpServletRequest request, HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
		ImportResult ir = timetableService.importFile(file);
		if (ir.getFailureNum()>0){
			ir.getFailureMsg().insert(0, "，失败 "+ir.getFailureNum()+" 条，导入信息如下：");
		}
		addMessage(redirectAttributes, "已成功导入 "+ir.getSuccessNum()+" 条"+ir.getFailureMsg());
		return "redirect:" + adminPath + "/course/paike/timetable?repage";
	}

	
	public static void main(String[] args) {
		String str="16、18本科";
		String reg = "[\u4e00-\u9fa5]";

		Pattern pat = Pattern.compile(reg);  

		Matcher mat=pat.matcher(str); 

		String repickStr = mat.replaceAll("");
		//	A330
		System.out.println("去中文后:"+repickStr);
		String time = "周一，5-6节";
		String address = "A330";
		String curs_year_term = "2018-2019-01";
		if(!StringUtils.isEmpty(time)&&!StringUtils.isEmpty(address)) {
			//周一，5-6节
			String ss[] = time.split("，");
			if(ss.length==2) {
				//String zhou = CourseUtil.zhouValue(ss[1]);
				//String jie =  CourseUtil.jieValue(ss[1]);
				//String school = CourseUtil.schoolRootMap.get(address.substring(0,1));
				String room = address.substring(1);
				System.out.println(curs_year_term.concat("01").concat(room).concat("01").concat("1").concat("1"));
			}
		}
	}
}