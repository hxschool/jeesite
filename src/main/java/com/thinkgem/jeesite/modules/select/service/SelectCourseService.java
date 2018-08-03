/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.select.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.select.entity.SelectCourse;
import com.thinkgem.jeesite.modules.select.dao.SelectCourseDao;

/**
 * 选课信息表Service
 * @author 赵俊飞
 * @version 2018-07-31
 */
@Service
@Transactional(readOnly = true)
public class SelectCourseService extends CrudService<SelectCourseDao, SelectCourse> {

	public SelectCourse get(String id) {
		return super.get(id);
	}
	
	public List<SelectCourse> findList(SelectCourse selectCourse) {
		return super.findList(selectCourse);
	}
	
	public Page<SelectCourse> findPage(Page<SelectCourse> page, SelectCourse selectCourse) {
		return super.findPage(page, selectCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(SelectCourse selectCourse) {
		super.save(selectCourse);
	}
	
	@Transactional(readOnly = false)
	public void delete(SelectCourse selectCourse) {
		super.delete(selectCourse);
	}
	
}