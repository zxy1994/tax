package com.test.dao;
import com.tax.core.dao.BaseDao;
import com.test.pojo.Student;

public interface StudentDao extends BaseDao<Student> {
	
	/**
	 * 自定义的Dao方法，保存
	 * @param s
	 */
	void saveOne(Student s);
}
