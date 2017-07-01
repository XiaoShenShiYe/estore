package cn.itcast.estore.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.Book;
import cn.itcast.estore.domain.Category;
import cn.itcast.estore.service.BookService;
import cn.itcast.estore.service.CategoryService;
import cn.itcast.estore.utils.BaseServlet;

/**
 * 后台图书管理的Servlet
 * 
 * @author Admin
 *
 */
public class AdminBookServlet extends BaseServlet {
	/**
	 * 查找所有的图书
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) {

		BookService bookService = new BookService();
		List<Book> list = bookService.findAll();
		req.setAttribute("list", list);
		return "/adminjsps/admin/book/list.jsp";
	}

	/**
	 * 根据图书的bid 来查找图书
	 */
	public String findByBid(HttpServletRequest req, HttpServletResponse resp) {
		String bid = req.getParameter("bid");
		BookService bookService = new BookService();
		Book book = bookService.findByBid(bid);

		CategoryService categoryService = new CategoryService();
		List<Category> list=categoryService.findAll();
		req.setAttribute("list", list);
		req.setAttribute("book", book);

		return "/adminjsps/admin/book/desc.jsp";
	}
	/**
	 * 跳转到添加图书界面
	 * @param req
	 * @param resp
	 * @return
	 */
	public String saveUI(HttpServletRequest req, HttpServletResponse resp){
		
		CategoryService categryService=new CategoryService();
		List<Category> list = categryService.findAll();
		req.setAttribute("list", list);
		return "/adminjsps/admin/book/add.jsp";
	}
	/**
	 * 图书下架
	 */
	public String pushDown(HttpServletRequest req, HttpServletResponse resp){
		
		String bid=req.getParameter("bid");
		BookService bookService = new BookService();
		Book book = bookService.findByBid(bid);
		book.setIsdel(1);
		bookService.updateBook(book);
		
		return findAll(req,resp) ;
	}
	/**
	 * 查询所有已下架的图书
	 */
	public String findUpBook(HttpServletRequest req, HttpServletResponse resp){

		BookService bookService = new BookService();
		List<Book> list = bookService.findUpBook();
		req.setAttribute("list", list);
		return "/adminjsps/admin/book/upbooklist.jsp";
	}
	/**
	 * 图书上架
	 */
	public String pushUp(HttpServletRequest req, HttpServletResponse resp){
		String bid= req.getParameter("bid");
		BookService bookService = new BookService();
		Book book = bookService.findByBid(bid);
		book.setIsdel(0);
		bookService.updateBook(book);
		return findAll(req,resp) ;
	}
}
