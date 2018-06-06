package com.thinkgem.jeesite.modules.payment.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.utils.IPUtil;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.QRCodeKit;
import com.thinkgem.jeesite.modules.payment.entity.order.Order;
import com.thinkgem.jeesite.modules.payment.entity.trade.Traderecord;
import com.thinkgem.jeesite.modules.payment.service.order.OrderService;
import com.thinkgem.jeesite.modules.payment.service.trade.TraderecordService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/payment")
public class PaymentController {
	@Autowired
	private SystemService systemService;
	@Autowired
	private TraderecordService  traderecordService;
	@Autowired
	private OrderService  orderService;

	@RequestMapping(value = {"index", ""})
	public String index( HttpServletRequest request, HttpServletResponse response, Model model) {
		List<PaymentEntity> list = new ArrayList<PaymentEntity>();
		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setId("1");
		paymentEntity.setTitle("学费");
		paymentEntity.setAmount("100");
		paymentEntity.setRemarks("备注学费信息");
		list.add(paymentEntity);
		model.addAttribute("list", list);
		return "modules/payment/paymentIndex";
	}
	
	@RequestMapping("pay.html")
	public String pay(String[] ids, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		User user = UserUtils.getUser();
		String id = "T".concat(systemService.getSequence("serialNo14"));
		
		List<PaymentEntity> list = new ArrayList<PaymentEntity>();
		PaymentEntity paymentEntity = new PaymentEntity();
		paymentEntity.setId("1");
		paymentEntity.setTitle("学费");
		paymentEntity.setAmount("100");
		paymentEntity.setRemarks("备注学费信息");
		list.add(paymentEntity);
		
		List<Order> orders = new ArrayList<Order>();
		BigDecimal amount = BigDecimal.ZERO;
		for(PaymentEntity entity:list) {
			Order order = new Order();
			order.setId("O".concat(systemService.getSequence("serialNo14")));
			order.setPayId(entity.getId());
			order.setPayTitle(entity.getTitle());
			order.setPayRemark(entity.getRemarks());
			order.setPayTime(new Date());
			order.setStatus("00");
			orders.add(order);
			
			BigDecimal paymentAmount=new BigDecimal(entity.getAmount());
			amount = amount.add(paymentAmount);
		}
		String payAmount = amount.toString();
		Traderecord traderecord = new Traderecord();
		traderecord.setUser(user);
		traderecord.setIdCard(user.getLoginName());
		traderecord.setUserIp(IPUtil.getIpAddr(request));
		traderecord.setId(id);
		traderecord.setOrders(orders);
		traderecord.setPayAmount(payAmount);
		String key = UUID.randomUUID().toString();
		JedisUtils.setObject(key, traderecord, 1000*60*30);
		model.addAttribute("k", key);
		return "modules/payment/paymentPay";
	}
	@ResponseBody
	@RequestMapping("qrcode")
	public void qrcode(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String key = request.getParameter("k");
		String data = "http://alipay.tunnel.qydev.com/payment/qrcode?k="+key;
		BufferedImage image = QRCodeKit.createQRCodeWithLogo(data);
		ServletOutputStream servletOutputStream = response.getOutputStream();
		ImageIO.write(image, "png", servletOutputStream);
	}
}
