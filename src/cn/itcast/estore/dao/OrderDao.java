package cn.itcast.estore.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.estore.domain.Book;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.utils.JDBCUtils;

public class OrderDao {

	/**
	 * 生成订单
	 * 
	 * @param connection
	 * @param order
	 */
	public void save(Connection connection, Order order) {

		QueryRunner quRunner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?)";
		Object[] param = { order.getOid(), order.getTotal(), order.getOrdertime(), order.getState(), order.getAddress(),
				order.getUser().getUid() };
		try {
			quRunner.update(connection, sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * 保存订单项
	 * 
	 * @param connection
	 * @param orderItem
	 */
	public void save(Connection connection, OrderItem orderItem) {
		QueryRunner quRunner = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] param = { orderItem.getItemid(), orderItem.getCount(), orderItem.getSubtotal(),
				orderItem.getBook().getBid(), orderItem.getOrder().getOid() };
		try {
			quRunner.update(connection, sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	/**
	 * 通过订单id查询订单
	 * 
	 * @param oid
	 * @return
	 */
	public Order findByOid(String oid) {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where oid=?";
		Order order;
		try {
			order = queryRunner.query(sql, new BeanHandler<Order>(Order.class), oid);

			sql = "select * from orderitem o,book b where o.bid=b.bid and o.oid=?";
			List<Map<String, Object>> olist = queryRunner.query(sql, new MapListHandler(), oid);
			for (Map<String, Object> map : olist) {

				Book book = new Book();
				BeanUtils.populate(book, map);
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				orderItem.setBook(book);
				orderItem.setOrder(order);
				order.getOrderItems().add(orderItem);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return order;
	}

	/**
	 * 查询所有订单
	 * 
	 * @return
	 */

	public List<Order> findAll() {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders";
		List<Order> list;
		try {
			list = queryRunner.query(sql, new BeanListHandler<Order>(Order.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
	}

	/**
	 * 通过订单状态查询订单
	 * 
	 * @param value
	 * @return
	 */
	public List<Order> findByState(int value) {

		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where state=? order by ordertime desc";
		List<Order> list;
		try {
			list = queryRunner.query(sql, new BeanListHandler<Order>(Order.class), value);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return list;
	}

	/**
	 * 修改订单
	 * 
	 * @param order
	 */
	public void updateOrder(Order order) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update orders set total=?,ordertime=?,state=?,address=? where oid=?";
		Object[] params = { order.getTotal(), order.getOrdertime(), order.getState(), order.getAddress(),
				 order.getOid() };

		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	public List<OrderItem> showDetail(String oid) {
		List<OrderItem> list= new ArrayList<OrderItem>();
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql=null;
		//sql="select * from order where oid=?";
		//Order order = queryRunner.query(sql, new BeanHandler<Order>(Order.class), oid);
		sql="select * from orderitem o,book b where o.bid=b.bid and oid=?";
		try {
			List<Map<String,Object>> o_list=queryRunner.query(sql, new MapListHandler(), oid);
		
			for(Map<String,Object> map:o_list){
				Book book= new Book();
				BeanUtils.populate(book, map);
				
				OrderItem orderItem= new OrderItem();
				BeanUtils.populate(orderItem, map);
				orderItem.setBook(book);
				list.add(orderItem);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return list;
	}

}
