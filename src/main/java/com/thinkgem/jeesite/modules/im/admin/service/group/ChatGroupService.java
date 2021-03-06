/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.im.admin.service.group;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.im.admin.dao.group.ChatGroupDao;
import com.thinkgem.jeesite.modules.im.admin.entity.group.ChatGroup;

/**
 * 好友分组Service
 * @author 赵俊飞
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class ChatGroupService extends CrudService<ChatGroupDao, ChatGroup> {

	public ChatGroup get(String id) {
		return super.get(id);
	}
	
	public List<ChatGroup> findByParentIdsLike(ChatGroup chatGroup) {
		return super.findByParentIdsLike(chatGroup);
	}
	
	public Page<ChatGroup> findPage(Page<ChatGroup> page, ChatGroup chatGroup) {
		return super.findPage(page, chatGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(ChatGroup chatGroup) {
		super.save(chatGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(ChatGroup chatGroup) {
		super.delete(chatGroup);
	}
	
}