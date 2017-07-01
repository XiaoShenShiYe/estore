package cn.itcast.estore.service;

/*
 * 业务层
 */
import java.util.List;

import cn.itcast.estore.dao.BookDao;
import cn.itcast.estore.domain.Book;

public class BookService {
/**
 * 查找所有
 * @return
 */
	public List<Book> findAll() {
		BookDao bookDao = new BookDao();
		return bookDao.findAll();
	}
/**
 * 通过cid查找 
 * @param cid
 * @return
 */
	public List<Book> findByCid(String cid) {

		BookDao bookDao=new BookDao();
		return bookDao.findByCid(cid);
	}

	public Book findByBid(String bid) {
		
		BookDao bookDao=new BookDao();
		return bookDao.findByBid(bid);
	}
	public void addBook(Book book) {

		BookDao bookDao = new BookDao();
		bookDao.addBook(book);
	}
	/**
	 * 修改图书
	 * @param book
	 */
	public void updateBook(Book book) {
		BookDao bookDao = new BookDao();
		bookDao.update(book);
	}
/*	public void pushDown(String bid) {

		BookDao bookDao = new BookDao();
		bookDao.pushDowm(bid);
	}*/
	public List<Book> findUpBook() {
		BookDao bookDao = new BookDao();
		List<Book> list=bookDao.findUpBook();
		return list;
	}

}
