package cn.itcast.estore.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.estore.domain.Book;
import cn.itcast.estore.service.BookService;

/**
 * 修改图书的Servlet
 * 
 * @author Admin
 *
 */
public class UpdateBookServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		Map<String, Object> map = new HashMap<String, Object>();
		Book book = new Book();
		String filename = null;
		try {
			List<FileItem> list = fileUpload.parseRequest(req);
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {

					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8");
					map.put(name, value);
					/*if (name.equals("price")) {
						
						String value = fileItem.getString("UTF-8");
						Double value1 = Double.parseDouble(value);
						map.put(name, value1);
					} else {
						String value = fileItem.getString("UTF-8");
						map.put(name, value);
					}*/
					

				} else {
					// 获得上传的文件的名字
					filename = fileItem.getName();
					// 判断文件的名字是否为空或者不存在,如果不是,那么就是修改了图片,这是就需要把原来的图片删除
					if (filename != null && !"".equals(filename)) {
						// 得到磁盘的绝对路径
						String path = this.getServletContext().getRealPath("/book_img");
						// 获得原有图片的路径
						String image = (String) map.get("image");
						// 获得"/"的位置
						int idx = image.lastIndexOf("/");
						// 获得原有图片的名字
						String fpath = image.substring(idx + 1);
						File file = new File(path + "\\" + fpath);
						// 删除原有的图片
						if (file.exists()) {
							file.delete();
						}

						InputStream is = fileItem.getInputStream();

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

			}
			BeanUtils.populate(book, map);
			if (filename != null && !"".equals(filename)) {

				book.setImage("book_img/" + filename);
			}
			BookService bookService = new BookService();
			bookService.updateBook(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 页面跳转
		req.getRequestDispatcher("/adminBookServlet?method=findAll").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);

	}
}
