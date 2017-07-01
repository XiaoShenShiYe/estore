package cn.itcast.estore.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.Book;
import cn.itcast.estore.domain.Cart;
import cn.itcast.estore.domain.CartItem;
import cn.itcast.estore.service.BookService;
import cn.itcast.estore.utils.BaseServlet;

/**
 * 购物车的Servlet
 * Servlet implementation class CartServlet
 */
@WebServlet(urlPatterns="/cartServlet")
public class CartServlet extends BaseServlet {
	/**
	 * 获得购物车
	 * @param req
	 * @return
	 */
	public Cart getCart(HttpServletRequest req){
		
		Cart cart=(Cart) req.getSession().getAttribute("cart");
		if(cart==null){
			cart=new Cart();
			req.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	/**
	 * 添加购物项到购物车
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addCart(HttpServletRequest req,HttpServletResponse resp){
		String bid=req.getParameter("bid");
		int count=Integer.parseInt(req.getParameter("count"));
		
		BookService bookService=new BookService();
		Book book=bookService.findByBid(bid);
		
		
		CartItem cartItem=new CartItem();
		cartItem.setCount(count);
		cartItem.setBook(book);
		
		Cart cart = getCart(req);
		cart.addCart(cartItem);
		
		return "/jsps/cart/list.jsp";
	}
	/**
	 * 清空购物车
	 * @param req
	 * @param resp
	 * @return
	 */
	public String clearCart(HttpServletRequest req,HttpServletResponse resp){
		Cart cart=getCart(req);
		cart.clearCart();
		return "/jsps/cart/list.jsp";
	}
	/**
	 * 删除购物车中的购物项
	 * @param req
	 * @param resp
	 * @return
	 */
	public String  removeCart(HttpServletRequest req,HttpServletResponse resp){
		String bid=req.getParameter("bid");
		Cart cart = getCart(req);
		cart.removeCart(bid);
		System.out.println("aa");
		
		return "/jsps/cart/list.jsp";
	}
	
}
