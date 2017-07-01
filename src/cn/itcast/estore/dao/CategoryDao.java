package cn.itcast.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.domain.Category;
import cn.itcast.estore.utils.JDBCUtils;

/**
 * 分类管理的DAO的类
 */
public class CategoryDao {

	/**
	 * DAO中查询所有分类的方法
	 * @return
	 */
	public List<Category> findAll() {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		List<Category> list;
		try {
			list = queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
	}

	public void add(Category category) {

		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="insert into category values(?,?)";
		try {
			queryRunner.update(sql, category.getCid(),category.getCname());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
				
	}

	public Category findByCid(String cid) {
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from category where cid=?";
		Category category;
		try {
			category=queryRunner.query(sql, new BeanHandler<Category>(Category.class), cid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
				
		return category;
	}

	public void save(Category category) {
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="update category set cname=? where cid=?";
		try {
			queryRunner.update(sql, category.getCname(),category.getCid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
				
		
	}

	public void delete(String cid) {
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="delete from category where cid=?";
		try {
			queryRunner.update(sql, cid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

}
