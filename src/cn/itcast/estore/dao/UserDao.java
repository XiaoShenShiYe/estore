package cn.itcast.estore.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.estore.domain.Book;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.utils.JDBCUtils;

/**
 * 用户管理的DAO的类
 */
public class UserDao {

	/**
	 * DAO中用户保存到数据库的方法
	 * 
	 * @param user
	 */
	public void save(User user) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into user values (?,?,?,?,?,?)";
		Object[] params = { user.getUid(), user.getUsername(),
				user.getPassword(), user.getEmail(), user.getState(),
				user.getCode() };
		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * DAO中根据用户名查询用户的方法
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ?";
		User user;
		try {
			user = queryRunner.query(sql, new BeanHandler<User>(User.class),
					username);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return user;
	}

	/**
	 * DAO中根据激活码查询用户的方法
	 * 
	 * @param code
	 * @return
	 */
	public User findByCode(String code) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where code = ?";
		User user;
		try {
			user = queryRunner.query(sql, new BeanHandler<User>(User.class),
					code);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return user;
	}

	/**
	 * DAO中修改用户的方法
	 * 
	 * @param user
	 */
	public void update(User user) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update user set username = ?,password = ?,email = ?,state = ?,code = ? where uid = ?";
		Object[] params = { user.getUsername(), user.getPassword(),
				user.getEmail(), user.getState(), user.getCode(), user.getUid() };
		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * DAO中用户登录的方法:
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? and state = ?";
		User existUser;
		try {
			existUser = queryRunner.query(sql, new BeanHandler<User>(User.class),
					user.getUsername(), user.getPassword(), 1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return existUser;
	}
	/**
	 * 多表联合查询
	 * 查询之后的数据封装
	 * @param uid
	 * @return
	 */
	public List<Order> findByUid(String uid) {
		List<Order> list = null;
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from orders where uid=? order by ordertime desc";
		try {
		list=queryRunner.query(sql, new BeanListHandler<Order>(Order.class), uid);
		for(Order order:list){
			sql="select * from orderitem o,book b where o.bid=b.bid and o.oid=?";
			List<Map<String ,Object>> olist=queryRunner.query(sql, new MapListHandler() , order.getOid());
			//每个map 就是一条记录
			for(Map<String,Object> map:olist){
				Book book=new Book();
				BeanUtils.populate(book, map);
				
				OrderItem orderItem=new OrderItem();
				BeanUtils.populate(orderItem, map);
				orderItem.setBook(book);
				orderItem.setOrder(order);
				
				order.getOrderItems().add(orderItem);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
