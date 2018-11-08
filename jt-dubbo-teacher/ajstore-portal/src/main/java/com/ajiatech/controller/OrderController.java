package com.ajiatech.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ajiatech.mapper.AjiaOrderMapper;
import com.ajiatech.pojo.AjiaOrder;
import com.ajiatech.pojo.AjiaUserResult;
import com.ajiatech.pojo.ItemVO;
import com.ajiatech.pojo.OrderVO;
import com.ajiatech.service.OrderService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/order")
public class OrderController {
	// tomcat����-->����servletContext-->ContextLoaderListener-->applicationContext.xml-->ɨ��Mapper

	// ��spring���ȡ��һ��service
	@Autowired
	OrderService orderService;
//ÿҳ��5������
	//ͨ��springMVC���ʹ��properties�е�ֵ
	@Value("${pageSize}")
	String pageSize;
	
	@RequestMapping("/toPayment.html")
	//ת����payment.jsp
	public String toPayment(Model model
			,String orderId) 
			throws Exception
	{
		AjiaOrder ajiaOrder=orderService.selectById(orderId);
		//�����ݷŵ�request��,�൱��modelAndView.addObject
		model.addAttribute("ajiaOrder", ajiaOrder);
		//ת����web-inf/jsp/payment.jsp
		return "payment";
	}
	
	@RequestMapping("/cancelOrder.html")
	public ModelAndView cancelOrder(String orderId,
			@RequestParam(defaultValue="all")
	String status,
	@RequestParam(defaultValue="1")
	int currentPage
			) 
			throws Exception
	{
		ModelAndView modelAndView=new ModelAndView();
		//��ѯ��û���������
		AjiaOrder ajiaOrder=orderService
				.findOrderByOrderid(orderId);
		//�ж϶����ǲ��ǵ�ǰ�û���
		if (ajiaOrder!=null && ajiaOrder.getUserId()==14L)
		{
			orderService.updateOrderStatus(orderId, 8);
		}		
		modelAndView.setViewName		
		("redirect:/order/toMyOrder.html?status="
		+status+"&currentPage="+currentPage);
		return modelAndView;
	}
	
	@RequestMapping("/toMyOrder.html")
	public ModelAndView toMyOrder
	(@RequestParam(defaultValue="all")String status,
		@RequestParam(defaultValue="1"
		)	int currentPage,
		HttpServletRequest request) throws Exception {
		//��request��ȡ�û���Ϣ
		AjiaUserResult ajiaUserResult=
				(AjiaUserResult) request
				.getAttribute("ajiaUserResult");
		
		Long userId=ajiaUserResult.getData().getId();
		
		ModelAndView modelAndView = new ModelAndView();
		//��״̬���浽request��
		modelAndView.addObject("status", status);
		
		//0��ʾ��ѯ���ж���
		//���ַ���ת��״̬��
		int istatus=0;
		switch (status) {
		case "waitPay":
			istatus=1;
			break;
		case "waitReceive":
			istatus=5;
			break;
		case "waitAssess":
			istatus=6;
			break;
		case "all":
			istatus=0;
			break;
		}
//		List<OrderVO> orderVOs=orderService
//				.selectByUserIdAndStatus(14L, istatus);
		PageInfo<OrderVO> pageInfo=orderService
				.selectByUserIdAndStatusForPage(userId, istatus, currentPage, Integer.parseInt(pageSize));
		modelAndView.addObject("orderVOs", 
				pageInfo.getList());
		modelAndView.addObject("pageInfo", pageInfo);
		
		modelAndView.setViewName("myOrder");
		return modelAndView;
	}

	@RequestMapping("/submitOrder.html")
	// http://www.ajstore.com/order/submitOrder.html
	// ?addId=1&itemId=28&itemId=30
	public ModelAndView submitOrder(Long addId, Long[] itemId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("payment");
		// ������ת��list
		List<Long> itemIdList = Arrays.asList(itemId);

		AjiaOrder ajiaOrder = orderService.saveOrder(addId, 14L, itemIdList);
		modelAndView.addObject("ajiaOrder", ajiaOrder);
		return modelAndView;
	}

	@RequestMapping("/confirmOrder.html")
	public ModelAndView confirmOrder(String itemIds) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String[] array = itemIds.split("\\,");
		List<Long> itemIdList = new ArrayList<>();
		for (String string : array) {
			itemIdList.add(Long.parseLong(string));
		}
		List<ItemVO> itemVOs = orderService.selectCartItemByUseridAndItemIds(14L, itemIdList);
		modelAndView.addObject("itemVOs", itemVOs);

		modelAndView.setViewName("orderConfirm");
		return modelAndView;
	}

	// ���Է���json
	// http://www.ajstore.com/select.html
	@RequestMapping("/select.html")
	@ResponseBody
	public AjiaOrder select() {
		AjiaOrder ajiaOrder = new AjiaOrder();
		ajiaOrder.setOrderId("1111");
		return ajiaOrder;
	}

	// http://www.ajstore.com/show.html
	@RequestMapping("/show.html")
	public ModelAndView show() throws Exception {
		// ����Զ���ǰ׺/web-inf/jsp/,�Ӻ�׺.jsp
		// ת���� /web-inf/jsp/order.jsp
		ModelAndView modelAndView = new ModelAndView("order");
		AjiaOrder ajiaOrder = orderService.selectById("20161001490667343075");
		// ����Ϣ�ŵ�request��
		modelAndView.addObject("payment", ajiaOrder.getPayment());
		return modelAndView;
	}

}
