package com.weirong.Hibernate;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init() {
		Configuration configuration = new Configuration().configure();
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		
		transaction = session.beginTransaction();
	}
	
	@After
	public void destroy() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	public void testSession() {
		News news = (News) session.get(News.class, 1);
	
		System.out.println(news);
		
		News news2 = (News) session.get(News.class, 1);
		System.out.println(news);
	}
	
	public void testSessionFlush() {
		News news = (News) session.get(News.class, 1);
		
		news.setAuthor("Oracle");
	}
	
	public void testSave() {
		News news = new News();
		news.setId(2);
		news.setTitle("AA");
		news.setAuthor("aa");
		news.setDate(new Date());
		System.out.println(news);
		
		session.save(news);
		
		System.out.println(news);
	}
	
	public void tsetPersist() {
		News news = new News();
		news.setId(3);
		news.setTitle("BB");
		news.setAuthor("aa");
		news.setDate(new Date());
		System.out.println(news);
		
		session.persist(news);
		
		System.out.println(news);
	}
	
	public void testGet() {
		News news = (News) session.get(News.class, 1);
		
		System.out.println(news);
	}
	
	/*
	 * 1. ����get������ ���������d����
	 * 2. ����load������ ������ԓ���� �t�����������в�ԃ������ ���Ƿ���һ�����팦��
	 * 3. get�������z���� load�����t�z��
	 * 4. ���������Л]���������Ĕ�����sessionҲ�]���P�]�� get����һ��null, load�t����һ������
	 * 5. load�������ܒ�LazyInitializationException(�м��d����), ����Ҫ��ʼ�����팦��֮ǰ�ѽ��P�]��session
	 * 
	 * 
	 */
	
	public void testLoad() {
		News news = (News) session.load(News.class, 1);
		
		System.out.println(news);
	}
	
	@Test
	public void testUpdate() {
		News news = (News) session.get(News.class, 1);
	
		news.setAuthor("weirong");
	}
}
