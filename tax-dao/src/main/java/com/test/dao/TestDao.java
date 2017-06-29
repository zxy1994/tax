package com.test.dao;

import com.tax.core.dao.BaseDao;
import com.test.pojo.Student;

public interface TestDao extends BaseDao<Student> {
	void saveOne(Student s);
}
