package cn.itcast.estore.service;

import java.util.List;

import cn.itcast.estore.dao.CategoryDao;
import cn.itcast.estore.domain.Category;

/**
 * 分类管理的业务层的类
 */
public class CategoryService {

	/**
	 * 业务层查询所有分类的方法
	 * @return
	 */
	public List<Category> findAll() {
		CategoryDao categoryDao = new CategoryDao();
		return categoryDao.findAll();
	}

	public void add(Category category) {

		CategoryDao categoryDao =new CategoryDao();
		categoryDao.add(category);
	}

	public Category findByCid(String cid) {
		CategoryDao categoryDao =new CategoryDao();
		Category category=categoryDao.findByCid(cid);
		return category;
	}

	public void save(Category category) {
		CategoryDao categoryDao =new CategoryDao();
		categoryDao.save(category);
	}

	public void delete(String cid) {
		CategoryDao categoryDao =new CategoryDao();
		categoryDao.delete(cid);
	}

}
