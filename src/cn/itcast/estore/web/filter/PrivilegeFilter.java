package cn.itcast.estore.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.itcast.estore.domain.User;

public class PrivilegeFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest reqq=(HttpServletRequest) req;
		User existUser=(User) reqq.getSession().getAttribute("existUser");
		if(existUser==null){
			reqq.setAttribute("msg", "您还没有登录,没有权限访问");
			reqq.getRequestDispatcher("/jsps/msg.jsp").forward(reqq, resp);
			return;
		}else{
			chain.doFilter(reqq, resp);
		}
		
	}

	@Override
	public void destroy() {
		
	}
	

}
