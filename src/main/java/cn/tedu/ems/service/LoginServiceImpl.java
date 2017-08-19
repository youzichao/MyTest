package cn.tedu.ems.service;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.tedu.ems.dao.UserDAO;
import cn.tedu.ems.entity.User;
/**
 * ҵ���ʵ����
 * @author Administrator
 *
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {
	@Resource(name="userDAO")
	private UserDAO dao;

	public User checkLogin(String username, String pwd) {
		User user=dao.findByUsername(username);
		if(user==null){
			/**
			 * �û���������
			 * �����׳�һ��Ӧ���쳣
			 * ע��
			 * 	��ʾ���ڲ���Ӧ���쳣֮����Ҫ��ȷ��ʾ�û���ȡ��ȷ�Ĳ���
			 */
			throw new ApplicationException("�û���������");
		}
		if(!user.getPwd().equals(pwd)){
			throw new ApplicationException("�������");
		}
		//��¼��֤ͨ��
		return user;
	}

}
