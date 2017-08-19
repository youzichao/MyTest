package test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.ems.dao.UserDAO;
import cn.tedu.ems.entity.User;
import cn.tedu.ems.service.LoginService;

public class TestCase {
	@Test
	public void test1() throws SQLException{
		String  config="springmvc.xml";
		ApplicationContext ac=new ClassPathXmlApplicationContext(config);
		DataSource ds=ac.getBean("ds",DataSource.class);
		System.out.println(ds.getConnection());
	}
	@Test
	//≤‚ ‘≥÷æ√≤„
	public void test2(){
		String  config="springmvc.xml";
		ApplicationContext ac=new ClassPathXmlApplicationContext(config);
		UserDAO dao=ac.getBean("userDAO",UserDAO.class);
		User user=dao.findByUsername("Sally");
		System.out.println(user);
	}
	@Test
	public void test3(){
		String  config="springmvc.xml";
		ApplicationContext ac=new ClassPathXmlApplicationContext(config);
		LoginService loginService=ac.getBean("loginService",LoginService.class);
		User user=loginService.checkLogin("Sally", "1254");
		System.out.println(user);
	}
	
	@Test
	public  void test4(){
		String  config="springmvc.xml";
		ApplicationContext ac=new ClassPathXmlApplicationContext(config);
		UserDAO dao=ac.getBean("userDAO",UserDAO.class);
		User user=dao.findByUsername("Sa22lly");
		System.out.println(user);
		
	}
	public void haha(){
		
	}
}
