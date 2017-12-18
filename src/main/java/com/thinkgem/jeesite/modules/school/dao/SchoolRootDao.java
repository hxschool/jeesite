/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.school.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.school.entity.SchoolRoot;

/**
 * 楼宇DAO接口
 * @author 赵俊飞
 * @version 2017-12-15
 */
@MyBatisDao
public interface SchoolRootDao extends CrudDao<SchoolRoot> {
	
}