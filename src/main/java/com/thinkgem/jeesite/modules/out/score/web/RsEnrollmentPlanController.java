/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.out.score.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.out.score.entity.RsEnrollmentPlan;
import com.thinkgem.jeesite.modules.out.score.service.RsEnrollmentPlanService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 招生计划Controller
 * @author 赵俊飞
 * @version 2018-01-09
 */
@Controller
@RequestMapping(value = "${adminPath}/out/score/rsEnrollmentPlan")
public class RsEnrollmentPlanController extends BaseController {

	@Autowired
	private RsEnrollmentPlanService rsEnrollmentPlanService;
	
	@ModelAttribute
	public RsEnrollmentPlan get(@RequestParam(required=false) String id) {
		RsEnrollmentPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = rsEnrollmentPlanService.get(id);
		}
		if (entity == null){
			entity = new RsEnrollmentPlan();
		}
		return entity;
	}
	
	//@RequiresPermissions("out:jcd:rsMajorSetup:view")
	@RequestMapping(value = {"list", ""})
	public String list(RsEnrollmentPlan rsMajorSetup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RsEnrollmentPlan> page = rsEnrollmentPlanService.findPage(new Page<RsEnrollmentPlan>(request, response), rsMajorSetup); 
		model.addAttribute("page", page);
		return "modules/out/score/rsEnrollmentPlanList";
	}

	@RequiresPermissions("out:jcd:rsMajorSetup:view")
	@RequestMapping(value = "form")
	public String form(RsEnrollmentPlan rsMajorSetup, Model model) {
		model.addAttribute("rsMajorSetup", rsMajorSetup);
		return "modules/out/score/rsEnrollmentPlanForm";
	}

	@RequiresPermissions("out:jcd:rsMajorSetup:edit")
	@RequestMapping(value = "save")
	public String save(RsEnrollmentPlan rsMajorSetup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, rsMajorSetup)){
			return form(rsMajorSetup, model);
		}
		rsEnrollmentPlanService.save(rsMajorSetup);
		addMessage(redirectAttributes, "保存招生计划成功");
		return "redirect:"+Global.getAdminPath()+"/out/score/rsEnrollmentPlan/?repage";
	}
	
	
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RsEnrollmentPlan> list = ei.getDataList(RsEnrollmentPlan.class);
			for (RsEnrollmentPlan jcd : list){
				try {
					RsEnrollmentPlan entity = rsEnrollmentPlanService.getMajorId(jcd);
					if (org.springframework.util.StringUtils.isEmpty(entity)) {
						entity = new RsEnrollmentPlan();
						entity.setMajorId(jcd.getMajorId());
						entity.setMajorName(jcd.getMajorName());
						entity.setCreateBy(UserUtils.getUser());
						entity.setMajorCount("0");
						entity.setMajorTotal(jcd.getMajorTotal());
					} else {
						String id = entity.getId();
						BeanUtils.copyProperties(jcd, entity);
						entity.setId(id);
					}
					rsEnrollmentPlanService.save(entity);
					successNum++;
				} catch (ConstraintViolationException ex) {

					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>专业编码: " + jcd.getMajorId() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条专业信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条专业信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/out/score/rsEnrollmentPlan/?repage";
	}
	
	@RequiresPermissions("out:jcd:rsMajorSetup:edit")
	@RequestMapping(value = "delete")
	public String delete(RsEnrollmentPlan rsMajorSetup, RedirectAttributes redirectAttributes) {
		rsEnrollmentPlanService.delete(rsMajorSetup);
		addMessage(redirectAttributes, "删除招生计划成功");
		return "redirect:"+Global.getAdminPath()+"/out/score/rsEnrollmentPlan/?repage";
	}

}