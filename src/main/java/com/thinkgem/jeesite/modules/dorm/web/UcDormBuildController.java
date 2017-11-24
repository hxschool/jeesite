/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
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
import com.thinkgem.jeesite.modules.dorm.entity.UcDormBuild;
import com.thinkgem.jeesite.modules.dorm.service.UcDormBuildService;

/**
 * 公寓管理Controller
 * @author 赵俊飞
 * @version 2017-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/dorm/ucDormBuild")
public class UcDormBuildController extends BaseController {

	@Autowired
	private UcDormBuildService ucDormBuildService;
	
	@ModelAttribute
	public UcDormBuild get(@RequestParam(required=false) String id) {
		UcDormBuild entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ucDormBuildService.get(id);
		}
		if (entity == null){
			entity = new UcDormBuild();
		}
		return entity;
	}
	
	@RequiresPermissions("dorm:ucDormBuild:view")
	@RequestMapping(value = {"list", ""})
	public String list(UcDormBuild ucDormBuild, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UcDormBuild> page = ucDormBuildService.findPage(new Page<UcDormBuild>(request, response), ucDormBuild); 
		model.addAttribute("page", page);
		return "modules/dorm/ucDormBuildList";
	}

	@RequiresPermissions("dorm:ucDormBuild:view")
	@RequestMapping(value = "form")
	public String form(UcDormBuild ucDormBuild, Model model) {
		model.addAttribute("ucDormBuild", ucDormBuild);
		return "modules/dorm/ucDormBuildForm";
	}

	@RequiresPermissions("dorm:ucDormBuild:edit")
	@RequestMapping(value = "save")
	public String save(UcDormBuild ucDormBuild, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ucDormBuild)){
			return form(ucDormBuild, model);
		}
		ucDormBuildService.save(ucDormBuild);
		addMessage(redirectAttributes, "保存公寓管理成功");
		return "redirect:"+Global.getAdminPath()+"/dorm/ucDormBuild/?repage";
	}
	
	@RequiresPermissions("dorm:ucDormBuild:edit")
	@RequestMapping(value = "delete")
	public String delete(UcDormBuild ucDormBuild, RedirectAttributes redirectAttributes) {
		ucDormBuildService.delete(ucDormBuild);
		addMessage(redirectAttributes, "删除公寓管理成功");
		return "redirect:"+Global.getAdminPath()+"/dorm/ucDormBuild/?repage";
	}

}