package com.test.dao.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tax.core.dao.impl.BaseDaoImpl;
import com.test.dao.TestDao;
import com.test.pojo.Student;

public class TestDaoImpl extends BaseDaoImpl<Student> implements TestDao {
	
	private Logger log = LogManager.getLogger(getClass());
	
	@Override
	public void saveOne(Student s) {
		this.save(s);
	}
	
	public TestDaoImpl(){
		log.info("实例化了TestDaoImpl");
	}
	
}
