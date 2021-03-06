/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.dorm.web;

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
import com.thinkgem.jeesite.modules.dorm.entity.UcDormRecord;
import com.thinkgem.jeesite.modules.dorm.service.UcDormRecordService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 查寝记录Controller
 * @author 赵俊飞
 * @version 2018-01-22
 */
@Controller
@RequestMapping(value = "${adminPath}/dorm/ucDormRecord")
public class UcDormRecordController extends BaseController {

	@Autowired
	private UcDormRecordService ucDormRecordService;
	
	@ModelAttribute
	public UcDormRecord get(@RequestParam(required=false) String id) {
		UcDormRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			UcDormRecord  ucDormRecord = new  UcDormRecord();
			ucDormRecord.setId(id);
			entity = ucDormRecordService.get(ucDormRecord);
		}
		if (entity == null){
			entity = new UcDormRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("dorm:ucDormRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(UcDormRecord ucDormRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UcDormRecord> page = ucDormRecordService.findPage(new Page<UcDormRecord>(request, response), ucDormRecord); 
		model.addAttribute("page", page);
		return "modules/dorm/ucDormRecordList";
	}
	
	@RequiresPermissions("dorm:ucDormRecord:view")
	@RequestMapping(value = "my")
	public String my(UcDormRecord ucDormRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		ucDormRecord.setStudentNumber(UserUtils.getUser().getNo());
		Page<UcDormRecord> page = ucDormRecordService.findPage(new Page<UcDormRecord>(request, response), ucDormRecord);
		model.addAttribute("page", page);
		return "modules/dorm/myDormRecordList";
	}
	
	@RequiresPermissions("dorm:ucDormRecord:view")
	@RequestMapping(value = "detail")
	public String detail(UcDormRecord ucDormRecord, Model model) {
		model.addAttribute("ucDormRecord", ucDormRecord);
		return "modules/dorm/ucDormRecordDetail";
	}

	@RequiresPermissions("dorm:ucDormRecord:view")
	@RequestMapping(value = "form")
	public String form(UcDormRecord ucDormRecord, Model model) {
		model.addAttribute("ucDormRecord", ucDormRecord);
		return "modules/dorm/ucDormRecordForm";
	}

	@RequiresPermissions("dorm:ucDormRecord:edit")
	@RequestMapping(value = "save")
	public String save(UcDormRecord ucDormRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ucDormRecord)){
			return form(ucDormRecord, model);
		}
		ucDormRecordService.save(ucDormRecord);
		addMessage(redirectAttributes, "保存查寝记录成功");
		return "redirect:"+Global.getAdminPath()+"/dorm/ucDormRecord/?repage";
	}
	
	@RequiresPermissions("dorm:ucDormRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(UcDormRecord ucDormRecord, RedirectAttributes redirectAttributes) {
		ucDormRecordService.delete(ucDormRecord);
		addMessage(redirectAttributes, "删除查寝记录成功");
		return "redirect:"+Global.getAdminPath()+"/dorm/ucDormRecord/?repage";
	}

}