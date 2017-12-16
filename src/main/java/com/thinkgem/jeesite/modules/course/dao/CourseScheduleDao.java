/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.course.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.course.entity.CourseSchedule;

/**
 * 计划教室DAO接口
 * @author 赵俊飞
 * @version 2017-12-15
 */
@MyBatisDao
public interface CourseScheduleDao extends CrudDao<CourseSchedule> {
	
}