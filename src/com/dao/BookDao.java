package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.entity.Book;

@Repository // 注册dao层bean等同于@Component
public class BookDao extends BaseDao{

	
	/**
	 * 	通过名称搜索
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Book> getList(String name, int page, int size){
		return getSession().createQuery("from Book where name like '%"+name+"%'", Book.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取名称总数
	 * @param name
	 * @return
	 */
	public long getTotal(String name){
		return getSession().createQuery("select count(*) from Book where name like '%"+name+"%'", Long.class)
				.uniqueResult();
	}
	
	/**
	 * 	通过分类搜索
	 * @param categoryid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Book> getCategoryList(int categoryid, int page, int size){
		return getSession().createQuery("from Book where category_id=:categoryid", Book.class)
				.setParameter("categoryid", categoryid).setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取分类总数
	 * @param categoryid
	 * @return
	 */
	public long getCategoryTotal(int categoryid){
		return getSession().createQuery("select count(*) from Book where category_id=:categoryid", Long.class)
				.setParameter("categoryid", categoryid).uniqueResult();
	}
	
	/**
	 * 	获取特卖列表
	 * @param type 1推荐/2新书/3特价
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Book> getSpecialList(int type, int page, int size){
		return getSession().createQuery("from Book "+packWhereSql(type), Book.class)
				.setFirstResult((page-1)*size).setMaxResults(size).list();
	}
	
	/**
	 * 	获取特卖总数
	 * @param type 1推荐/2新书/3特价
	 * @return
	 */
	public long getSpecialTotal(int type){
		return getSession().createQuery("select count(*) from Book "+packWhereSql(type), Long.class).uniqueResult();
	}
	
	/**
	 * 封装sql
	 * @param type
	 * @return
	 */
	private String packWhereSql(int type) {
		String sql = "";
		switch (type) {
		case Book.type_special:
			sql += "where special=1";
			break;
		case Book.type_new:
			sql += "where news=1";
			break;
		case Book.type_sale:
			sql += "where sale=1";
			break;
		}
		return sql;
	}

	/**
	 * 获取列表
	 * @param page
	 * @param rows
	 * @return 无记录返回空集合
	 */
	public List<Book> getList(int status, int page, int rows){
		return getSession().createQuery("from Book "+packSql(status)+" order by id desc", Book.class)
				.setFirstResult(rows*(page-1)).setMaxResults(rows).list();
	}

	/**
	 * 总数
	 * @return
	 */
	public long getTotal(int status) {
		return getSession().createQuery("select count(*) from Book "+packSql(status), Long.class).uniqueResult();
	}
	
	/**
	 * 封装sql
	 * @param status
	 * @return
	 */
	private String packSql(int status) {
		String sql = "";
		switch (status) {
		case 1:
			sql += " where special=1";
			break;
		case 2:
			sql += " where news=1";
			break;
		case 3:
			sql += " where sale=1";
			break;
		}
		return sql;
	}
	
}
