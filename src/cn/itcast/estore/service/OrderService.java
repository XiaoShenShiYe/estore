package cn.itcast.estore.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import cn.itcast.estore.dao.OrderDao;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.utils.JDBCUtils;

public class OrderService {

	public void save(Order order) {
		Connection connection = JDBCUtils.getConnection();
		try {
			connection.setAutoCommit(false);
			OrderDao orderDao = new OrderDao();
			orderDao.save(connection, order);
			for (OrderItem orderItem : order.getOrderItems()) {
				orderDao.save(connection, orderItem);
			}

			DbUtils.commitAndCloseQuietly(connection);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(connection);
			e.printStackTrace();
		}
	}

	public Order findByOid(String oid) {
		OrderDao orderDao = new OrderDao();

		return orderDao.findByOid(oid);
	}

	/**
	 * 查询所有订单
	 * @return
	 */
	public List<Order> findAll() {
		OrderDao orderDao = new OrderDao();
		List<Order> list = orderDao.findAll();

		return list;
	}
	/**
	 * 根据订单状态查询订单
	 * @param value
	 * @return
	 */
	public List<Order> findByState(int value) {
		OrderDao orderDao = new OrderDao();

		return orderDao.findByState( value);
	}

	public void updateOrder(Order order) {
		OrderDao orderDao = new OrderDao();
		orderDao.updateOrder(order);

	}

	public List<OrderItem> showDetail(String oid) {
		OrderDao orderDao = new OrderDao();
		List<OrderItem> list= orderDao.showDetail(oid);
		return list;
	}

}
