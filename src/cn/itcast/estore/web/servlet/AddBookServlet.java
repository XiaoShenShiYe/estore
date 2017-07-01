package cn.itcast.estore.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.estore.domain.Book;
import cn.itcast.estore.service.BookService;
import cn.itcast.estore.utils.UUIDUtils;

public class AddBookServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//doGet(req, resp);
		// 创建磁盘文件项工厂
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// 创建核心解析器
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		// 创建一个Map集合接收数据
		Map<String, String> map = new HashMap<String, String>();
		String filename = null;
		// 创建book对象
		Book book = new Book();
		try {
			// 解析请求对象
			List<FileItem> fileItems = fileUpload.parseRequest(req);
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {// 是普通项
					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8");
					map.put(name, value);
				} else {// 是文件上传项
						// 获得上传的文件名
					filename = fileItem.getName();
					// 获得上传的绝对路径
					String path = this.getServletContext().getRealPath("/book_img");
					// 获得代表文件内容的输入流
					InputStream is = fileItem.getInputStream();
					// 创建写入硬盘的输出流
					OutputStream os = new FileOutputStream(path + "\\" + filename);
					int len = 0;
					byte[] by = new byte[1024];
					while ((len = is.read(by)) != -1) {
						os.write(by, 0, len);
					}
					is.close();
					os.close();
				}
			}
			// 把map 中的数据封装到book中
			BeanUtils.populate(book, map);
			book.setBid(UUIDUtils.getUUID());
			book.setIsdel(0);
			book.setImage("book_img/" + filename);

			BookService bookService = new BookService();
			bookService.addBook(book);

		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/adminBookServlet?method=findAll").forward(req, resp);
	}

}
