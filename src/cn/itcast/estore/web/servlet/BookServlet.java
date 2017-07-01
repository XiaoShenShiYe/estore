package cn.itcast.estore.web.servlet;
/**
 * 图书管理的Servlet
 */
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.Book;
import cn.itcast.estore.service.BookService;
import cn.itcast.estore.utils.BaseServlet;
public class BookServlet extends BaseServlet {
	//查找所有的图书
	public String findAll(HttpServletRequest req,HttpServletResponse resp){
		BookService bookService = new BookService();  
		List<Book> list=bookService.findAll();
		req.setAttribute("list", list);
		return "/jsps/book/list.jsp";
	}
	//通过cid查找图书信息
	public String findByCid(HttpServletRequest req,HttpServletResponse resp){
	
		String cid=req.getParameter("cid");
		BookService bookService = new BookService();
		List<Book> list=bookService.findByCid(cid);
		req.setAttribute("list", list);
		return "/jsps/book/list.jsp";
	}
	//通过bid查找图书信息
	public String findByBid(HttpServletRequest req,HttpServletResponse resp){
	
		String bid=req.getParameter("bid");
		BookService bookService = new BookService();
		Book book = bookService.findByBid(bid);
		req.setAttribute("book", book);
		
		return "jsps/book/desc.jsp";
		
	}

}
