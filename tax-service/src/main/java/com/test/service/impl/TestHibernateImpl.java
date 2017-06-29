package com.test.service.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.TestDao;
import com.test.pojo.Student;
import com.test.service.TestHibernate;

@Service("testHibernate")
public class TestHibernateImpl implements TestHibernate {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private TestDao testDao;

	/* 
	 * 添加一个学生
	 */
	@Override
	public void saveStudent(Student student) {
		testDao.saveOne(student);
	}

}
