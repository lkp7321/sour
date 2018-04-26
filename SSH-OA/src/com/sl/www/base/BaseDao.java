package com.sl.www.base;

import java.util.List;

public interface BaseDao<T> {
	
	/**
	 * 查询所有
	 * @return 集合
	 */
	List<T> findAll();
	
	/**
	 * 保存实体
	 * @param entity 对象
	 */
	void save(T entity);
	
	/**
	 * 根据id删除实体
	 * @param id
	 */
	void delete(int id);
	
	/**
	 * 修改实体
	 * @param entity 对象
	 */
	void update(T entity);
	
	/**
	 * 根据id查找实体
	 * @param id
	 * @return 对象
	 */
	T getById(int id);
	
	/**
	 * 根据id数组查询一组实体
	 * @param ids 数组
	 * @return 集合
	 */
	List<T> getByIds(Integer[] ids);

}
