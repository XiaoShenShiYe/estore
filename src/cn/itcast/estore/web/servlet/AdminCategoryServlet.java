package cn.itcast.estore.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.Category;
import cn.itcast.estore.service.CategoryService;
import cn.itcast.estore.utils.BaseServlet;
import cn.itcast.estore.utils.UUIDUtils;
/**
 * 后台分类管理的Servlet
 * @author Admin
 *
 */
public class AdminCategoryServlet extends BaseServlet{
	/**
	 * 查找所有分类
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findAll(HttpServletRequest req,HttpServletResponse resp){
		CategoryService categoryService=new CategoryService();
		List<Category> list = categoryService.findAll();
		req.setAttribute("list", list);
		return "/adminjsps/admin/category/list.jsp";
	}
	/**
	 * 添加分类
	 * @param req
	 * @param resp
	 * @return
	 */
	public String saveUI(HttpServletRequest req,HttpServletResponse resp){
		
		return "/adminjsps/admin/category/add.jsp";
	}
	/**
	 * 添加分类
	 * 
	 * 如果分类已经存在,怎么处理
	 * @param req
	 * @param resp
	 * @return
	 */
	public String add(HttpServletRequest req,HttpServletResponse resp){
		
		String cname=req.getParameter("cname");
		String cid=UUIDUtils.getUUID();
		Category category=new Category();
		category.setCid(cid);
		category.setCname(cname);
		CategoryService categoryService=new CategoryService();
		categoryService.add(category);
		
		return findAll(req, resp);
	}
	/**
	 * 通过cid 来查询category
	 * @param req
	 * @param resp
	 * @return
	 */
	public String update(HttpServletRequest req,HttpServletResponse resp){
		String cid=req.getParameter("cid");
		CategoryService categoryService=new CategoryService();
		Category category=categoryService.findByCid(cid);
		req.setAttribute("category", category);
		return "/adminjsps/admin/category/mod.jsp";
	}
	/**
	 * 保存修改后的category
	 */
	public String save(HttpServletRequest req,HttpServletResponse resp){
		String cid=req.getParameter("cid");
		String cname=req.getParameter("cname");
		Category category=new Category();
		category.setCid(cid);
		category.setCname(cname);
		CategoryService categoryService=new CategoryService();
		categoryService.save(category);
		
		return findAll(req, resp);
	}
	public  String delete(HttpServletRequest req,HttpServletResponse resp){
		
		String cid=req.getParameter("cid");
		CategoryService categoryService=new CategoryService();
		categoryService.delete(cid);
		return findAll(req, resp);
	}
}
