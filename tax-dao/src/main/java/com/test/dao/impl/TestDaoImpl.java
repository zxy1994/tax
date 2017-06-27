package com.test.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.test.dao.TestDao;
import com.test.pojo.Student;

public class TestDaoImpl implements TestDao {
	private SessionFactory sessionFactory;
	
	public TestDaoImpl (SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOne(Student s) {
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(s);
			tx.commit();
			System.out.println("添加成功，请到数据库查看");
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			if(null != session) {
				session.close();
			}
		}
	}
	
	
}
