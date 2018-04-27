/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.teacher.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherClass;
import com.thinkgem.jeesite.modules.teacher.dao.TeacherClassDao;

/**
 * 教师班级信息表Service
 * @author 赵俊飞
 * @version 2018-04-28
 */
@Service
@Transactional(readOnly = true)
public class TeacherClassService extends CrudService<TeacherClassDao, TeacherClass> {

	public TeacherClass get(String id) {
		return super.get(id);
	}
	
	public List<TeacherClass> findList(TeacherClass teacherClass) {
		return super.findList(teacherClass);
	}
	
	public Page<TeacherClass> findPage(Page<TeacherClass> page, TeacherClass teacherClass) {
		return super.findPage(page, teacherClass);
	}
	
	@Transactional(readOnly = false)
	public void save(TeacherClass teacherClass) {
		super.save(teacherClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(TeacherClass teacherClass) {
		super.delete(teacherClass);
	}
	
}