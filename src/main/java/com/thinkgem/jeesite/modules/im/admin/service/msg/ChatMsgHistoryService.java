/**
 * Copyright &copy; 2018-2025 <a href="http://www.greathiit.com">哈尔滨信息工程学院</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.im.admin.service.msg;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.im.admin.dao.msg.ChatMsgHistoryDao;
import com.thinkgem.jeesite.modules.im.admin.entity.msg.ChatMsgHistory;

/**
 * 历史消息Service
 * @author 赵俊飞
 * @version 2018-08-10
 */
@Service
@Transactional(readOnly = true)
public class ChatMsgHistoryService extends CrudService<ChatMsgHistoryDao, ChatMsgHistory> {

	public ChatMsgHistory get(String id) {
		return super.get(id);
	}
	
	public List<ChatMsgHistory> findByParentIdsLike(ChatMsgHistory chatMsgHistory) {
		return super.findByParentIdsLike(chatMsgHistory);
	}
	
	public Page<ChatMsgHistory> findPage(Page<ChatMsgHistory> page, ChatMsgHistory chatMsgHistory) {
		return super.findPage(page, chatMsgHistory);
	}
	
	@Transactional(readOnly = false)
	public void save(ChatMsgHistory chatMsgHistory) {
		super.save(chatMsgHistory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ChatMsgHistory chatMsgHistory) {
		super.delete(chatMsgHistory);
	}
	
}