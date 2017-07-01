package com.test.dao.impl;
import com.tax.core.dao.impl.BaseDaoImpl;
import com.test.dao.StudentDao;
import com.test.pojo.Student;

public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {
	
	@Override
	public void saveOne(Student s) {
		// 由于继承了BaseDaoImpl，他就拥有了BaseDaoImpl的所有public修饰的方法
		this.save(s);
	}
	
}




