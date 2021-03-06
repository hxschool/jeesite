/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.answering.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.answering.admin.entity.AsAnsweringStudent;
import com.thinkgem.jeesite.modules.answering.admin.service.AsAnsweringService;
import com.thinkgem.jeesite.modules.answering.admin.service.AsAnsweringStudentService;

/**
 * 答辩Controller
 * @author 赵俊飞
 * @version 2018-08-17
 */
@Controller
@RequestMapping(value = "${adminPath}/answering/admin/asAnsweringStudent")
public class AsAnsweringStudentController extends BaseController {

	@Autowired
	private AsAnsweringStudentService asAnsweringStudentService;
	
	@ModelAttribute
	public AsAnsweringStudent get(@RequestParam(required=false) String id) {
		AsAnsweringStudent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = asAnsweringStudentService.get(id);
		}
		if (entity == null){
			entity = new AsAnsweringStudent();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(AsAnsweringStudent asAnsweringStudent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AsAnsweringStudent> page = asAnsweringStudentService.findPage(new Page<AsAnsweringStudent>(request, response), asAnsweringStudent); 
		model.addAttribute("page", page);
		return "modules/answering/admin/asAnsweringStudentList";
	}

	@RequestMapping(value = "form")
	public String form(AsAnsweringStudent asAnsweringStudent, Model model) {
		model.addAttribute("asAnsweringStudent", asAnsweringStudent);
		return "modules/answering/admin/asAnsweringStudentForm";
	}
	
	@RequestMapping(value = "update")
	public String update(AsAnsweringStudent asAnsweringStudent, Model model) {
		asAnsweringStudent.setStatus(AsAnsweringService.HAVE_IN_HAND);
		AsAnsweringStudent as = asAnsweringStudentService.get(asAnsweringStudent);
		if(!org.springframework.util.StringUtils.isEmpty(as)) {
			as.setStatus(AsAnsweringService.COMPLETE);
			asAnsweringStudentService.save(as);
		}
		return "redirect:"+Global.getAdminPath()+"/answering/admin/asAnsweringStudent/?asAnsweringId="+asAnsweringStudent.getAsAnsweringId()+"&repage";
	}
	

	@RequestMapping(value = "save")
	public String save(AsAnsweringStudent asAnsweringStudent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, asAnsweringStudent)){
			return form(asAnsweringStudent, model);
		}
		asAnsweringStudentService.save(asAnsweringStudent);
		addMessage(redirectAttributes, "保存答辩成功");
		return "redirect:"+Global.getAdminPath()+"/answering/admin/asAnsweringStudent/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(AsAnsweringStudent asAnsweringStudent, RedirectAttributes redirectAttributes) {
		asAnsweringStudentService.delete(asAnsweringStudent);
		addMessage(redirectAttributes, "删除答辩成功");
		return "redirect:"+Global.getAdminPath()+"/answering/admin/asAnsweringStudent/?repage";
	}

}