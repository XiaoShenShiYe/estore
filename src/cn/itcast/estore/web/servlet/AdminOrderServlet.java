package cn.itcast.estore.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.mail.iap.Response;

import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.service.OrderService;
import cn.itcast.estore.utils.BaseServlet;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class AdminOrderServlet extends BaseServlet{
	/**
	 * 查询所有订单
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findAll(HttpServletRequest req,HttpServletResponse resp){
		String state= req.getParameter("state");
		OrderService orderService = new OrderService();
		List<Order> list=null;
		if(state==null||"".equals(state)){
			list= orderService.findAll();
		}else{
			int value=Integer.parseInt(state);
			list=orderService.findByState(value);//根据订单状态查询订单
		}
		req.setAttribute("list", list);
		
		return "/adminjsps/admin/order/ajaxlist.jsp";
	}
	/**
	 * 修改订单状态
	 */
	public String updateState(HttpServletRequest req,HttpServletResponse resp){
		String oid = req.getParameter("oid");
		OrderService orderService = new OrderService();
		Order order=orderService.findByOid(oid);
		order.setState(3);
		orderService.updateOrder(order);
		
		return findAll(req,resp);
	}
	public String showDetail(HttpServletRequest req,HttpServletResponse resp){
		System.out.println("a");
		String oid= req.getParameter("oid");
		OrderService orderService = new OrderService();
		List<OrderItem> list =orderService.showDetail(oid);
		//转为JSON格式向页面输出
		JsonConfig jsonConfig = new JsonConfig();//去除不想显示项
		jsonConfig.setExcludes(new String[]{"itemid","order"});
		JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
		try {
			resp.getWriter().print(jsonArray.toString());//向页面输出
			System.out.println(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
}
