package cn.itcast.estore.web.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.Cart;
import cn.itcast.estore.domain.CartItem;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.service.OrderService;
import cn.itcast.estore.service.UserService;
import cn.itcast.estore.utils.BaseServlet;
import cn.itcast.estore.utils.UUIDUtils;

public class OrderServlet extends BaseServlet{
	/**
	 * 生成订单的方法
	 * @param req
	 * @param resp
	 * @return
	 */
	public String saveOrder(HttpServletRequest req,HttpServletResponse resp){
		
		Order order=new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setOrdertime(new Date());
		order.setState(1);
		order.setAddress(null);
		Cart cart=(Cart) req.getSession().getAttribute("cart");
		if(cart==null){
			req.setAttribute("msg", "您还没有购物!不能产生订单!");
			return "/jsps/msg.jsp";
			
		}
		order.setTotal(cart.getTotal());
		
		User existUser=(User) req.getSession().getAttribute("existUser");
		if(existUser==null){
			req.setAttribute("msg", "您还没有登录,请先登录!");
			return "/jsps/user/login.jsp";
		}
		order.setUser(existUser);//把user对象添加到订单中
		//把订单项添加到订单中,把购物项中的数据封装到订单项中
		for(CartItem cartItem:cart.getCartItems()){
			OrderItem orderItem=new OrderItem();
			orderItem.setItemid(UUIDUtils.getUUID());
			orderItem.setBook(cartItem.getBook());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setOrder(order);
			order.getOrderItems().add(orderItem);
		}
		//调用业务层保存订单
		OrderService orderService=new OrderService();
		orderService.save(order);
		//清空购物车
		cart.clearCart();
		//将订单保存到req域中
		req.setAttribute("order", order);
		//页面跳转
		return "/jsps/order/desc.jsp";
	}
	public String findByUid(HttpServletRequest req,HttpServletResponse resp){
		//String uid=req.getParameter("uid");
		User user=(User) req.getSession().getAttribute("existUser");
		String uid=user.getUid();
		UserService userService=new UserService();
		List<Order> list=userService.findByUid(uid);
		
		req.setAttribute("list", list);
		return "/jsps/order/list.jsp";
	}
	public String findByOid(HttpServletRequest req,HttpServletResponse resp){
		
		String oid=req.getParameter("oid");
		OrderService orderService =new OrderService();
		Order order=orderService.findByOid(oid);
		req.setAttribute("order", order);
		return "jsps/order/desc.jsp";
		
		
	}
	/**
	 * 修改订单状态为订单结束
	 */
	public String updateState(HttpServletRequest req,HttpServletResponse resp){
		System.out.println("a");
		String oid=req.getParameter("oid");
		OrderService orderService = new OrderService();
		Order order=orderService.findByOid(oid);
		order.setState(4);
		orderService.updateOrder(order);
		System.out.println("b");
		return findByUid(req,resp);
	}
	/**
	 * 修改订单状态为已付款
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updateState2(HttpServletRequest req,HttpServletResponse resp){
		String oid=req.getParameter("oid");
		OrderService orderService = new OrderService();
		Order order=orderService.findByOid(oid);
		order.setState(2);
		orderService.updateOrder(order);
		return findByUid(req,resp);
	}
}
