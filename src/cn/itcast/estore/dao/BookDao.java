package cn.itcast.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.domain.Book;
import cn.itcast.estore.utils.JDBCUtils;

public class BookDao {

	public List<Book> findAll() {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from book where isdel=?";
		List<Book> list;
		try {
			list = queryRunner.query(sql, new BeanListHandler<Book>(Book.class),0);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
	}
	public List<Book> findByCid(String cid) {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from book where cid = ? and isdel = ?";
		List<Book> list;
		try {
			list = queryRunner.query(sql, new BeanListHandler<Book>(Book.class), cid, 0);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
	}

	public Book findByBid(String bid) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from book where bid = ? ";

		Book book;
		try {
			book = queryRunner.query(sql, new BeanHandler<Book>(Book.class), bid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return book;
	}

	public void addBook(Book book) {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into book values(?,?,?,?,?,?,?)";
		Object[] params = { book.getBid(), book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(),
				book.getCid(), book.getIsdel() };
		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void update(Book book) {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql="update book set bname=?,price=?,author=?,image=?,cid=?,isdel=? where bid=?";
		Object[] param={book.getBname(),book.getPrice(),book.getAuthor(),book.getImage(),book.getCid(),book.getIsdel(),book.getBid()};
		
		try {
			queryRunner.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	public List<Book> findUpBook() {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from book where isdel=?";
		List<Book> list;
		try {
			list = queryRunner.query(sql, new BeanListHandler<Book>(Book.class),1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
		
	}

	/*public void pushDowm(String bid) {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql="update book set isdel=? where bid=?";
		
		try {
			queryRunner.update(sql, 1,bid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}*/
//		
//	}

}
