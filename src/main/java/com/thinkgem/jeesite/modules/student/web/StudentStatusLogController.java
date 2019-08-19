/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.student.entity.StudentStatusLog;
import com.thinkgem.jeesite.modules.student.service.StudentStatusLogService;

/**
 * 变动进度表Controller
 * @author 变动进度表
 * @version 2019-08-19
 */
@Controller
@RequestMapping(value = "${adminPath}/student/studentStatusLog")
public class StudentStatusLogController extends BaseController {

	@Autowired
	private StudentStatusLogService studentStatusLogService;
	
	@ModelAttribute
	public StudentStatusLog get(@RequestParam(required=false) String id) {
		StudentStatusLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentStatusLogService.get(id);
		}
		if (entity == null){
			entity = new StudentStatusLog();
		}
		return entity;
	}
	
	@RequiresPermissions("student:studentStatusLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(StudentStatusLog studentStatusLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudentStatusLog> page = studentStatusLogService.findPage(new Page<StudentStatusLog>(request, response), studentStatusLog); 
		model.addAttribute("page", page);
		return "modules/student/studentStatusLogList";
	}

	@RequiresPermissions("student:studentStatusLog:view")
	@RequestMapping(value = "form")
	public String form(StudentStatusLog studentStatusLog, Model model) {
		model.addAttribute("studentStatusLog", studentStatusLog);
		return "modules/student/studentStatusLogForm";
	}

	@RequiresPermissions("student:studentStatusLog:edit")
	@RequestMapping(value = "save")
	public String save(StudentStatusLog studentStatusLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentStatusLog)){
			return form(studentStatusLog, model);
		}
		studentStatusLogService.save(studentStatusLog);
		addMessage(redirectAttributes, "保存变动进度表成功");
		return "redirect:"+Global.getAdminPath()+"/student/studentStatusLog/?repage";
	}
	
	@RequiresPermissions("student:studentStatusLog:edit")
	@RequestMapping(value = "delete")
	public String delete(StudentStatusLog studentStatusLog, RedirectAttributes redirectAttributes) {
		studentStatusLogService.delete(studentStatusLog);
		addMessage(redirectAttributes, "删除变动进度表成功");
		return "redirect:"+Global.getAdminPath()+"/student/studentStatusLog/?repage";
	}

}