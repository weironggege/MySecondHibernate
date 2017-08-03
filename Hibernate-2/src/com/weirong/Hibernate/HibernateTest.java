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
	 * 1. 執行get方法： 會立即加載對象
	 * 2. 執行load方法： 若不用該對象， 則不會立即執行查詢操作， 而是返回一個代理對象
	 * 3. get是立即檢索， load是延遲檢索
	 * 4. 若數據表中沒有所對應的數據，session也沒有關閉， get返回一個null, load則拋出一個異常
	 * 5. load方法可能拋LazyInitializationException(懶加載異常), 在需要初始化代理對象之前已經關閉了session
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
