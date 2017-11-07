package com.tax.core.util;

import java.util.ArrayList;
import java.util.List;
/**
           查询工具类QueryHelper，里面需要对将要查询的hql和参数进行组装，然后将此对象传递给service运用并返回结果。
	该工具类主要方法有：
		①QueryHelper(Class clazz, String alias) 构造器，主要组装from 子句
		②addCondition(String condition, Object... param) 组装where子句
		③addOrderByProperty(String property, String order) 组装order by 子句
		④getListQueryHql() 返回查询列表的hql
		⑤getCountQueryHql() 返回统计总记录数的hql
		⑥getParameters() 返回参数列表
*/

/**
 * QueryHelper
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年11月7日 下午4:14:10
 * @version  v1.0
 */

public class QueryHelper {
	
	/** from 子句 */
	private String fromClause = "";
	/** where 子句 */
	private String whereClause = "";
	/** orderBy 子句 */
	private String orderByClause = "";
	/** 参数集合 */
	private List<Object> parameters;
	
	/** 升序 */
	public static final String ORDER_BY_ASC = "ASC";
	/** 降序 */
	public static final String ORDER_BY_DESC = "DESC";
	
	
	/**
	 * 构造方法，组装from语句
	 * @param clazz pojo类Class
	 * @param alias 别名
	 */
	public QueryHelper(Class<?> clazz, String alias) {
		this.fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}
	
	/**
	 * 组装where子句
	 * @param condition 条件
	 * @param params 条件中?对应的参数值
	 * @return QueryHelper
	 */
	public QueryHelper addCondition(String condition, Object...params){
		// 这里设置成可变参数，主要是为了解决condition 里面有多个？的情况
		if(whereClause.length() > 0){
			whereClause += " AND " + condition;
		} else {
			whereClause = " WHERE " + condition;
		}
		if(parameters == null){
			parameters = new ArrayList<Object>();
		}
		for(Object param: params){
			parameters.add(param);
		}
		return this;
	}
	
	/**
	 * 构建order by 子句
	 * @param property 排序的属性
	 * @param order 升序(asc)或是降序(desc)
	 * @return QueryHelper
	 */
	public QueryHelper addOrderByProperty(String property, String order){
		if(orderByClause.length() > 0){
			orderByClause += "," + property + " " + order;
		} else {
			orderByClause += " ORDER BY " + property + " " + order;
		}
		return this;
	}
	
	/** 返回列表查询hql语句  */
	public String getListQueryHql(){
		return fromClause + whereClause + orderByClause;
	}
	
	/** 返回查询总记录数的hql */
	public String getCountHql(){
		return "SELECT Count(*) " + fromClause + whereClause;
	}
	
	/** 返回参数集合 */
	public List<Object> getParameters() {
		return parameters;
	}
	
	
}
