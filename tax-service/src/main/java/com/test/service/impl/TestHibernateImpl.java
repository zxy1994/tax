package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.StudentDao;
import com.test.pojo.Student;
import com.test.service.TestHibernate;

@Service("testHibernate")
public class TestHibernateImpl implements TestHibernate {
	
	@Autowired
	private StudentDao studentDao;

	/* 
	 * 添加一个学生
	 */
	@Override
	public void saveStudent(Student student) {
		studentDao.saveOne(student);
	}

}
