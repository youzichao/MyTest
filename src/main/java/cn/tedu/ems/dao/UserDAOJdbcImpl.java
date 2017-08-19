package cn.tedu.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import cn.tedu.ems.entity.User;
/**
 * �־ò�ʵ����
 * @author Administrator
 *
 */

//@Repository("userDAO")  //�־�
public class UserDAOJdbcImpl implements UserDAO{
	@Resource(name="ds")
	private DataSource ds;
	
	public User findByUsername(String username) {
		User user=null;
		Connection conn=null;
		try {
			conn=ds.getConnection();
			String sql="select * from "
					+ "t_yzc where username=?";
			
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, username);
			System.out.println(sql);
			ResultSet rs=ps.executeQuery();
			//System.out.println(rs);
			if(rs.next()){
				user=new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPwd(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setGender(rs.getString("gender"));
			}
		} catch (SQLException e) {
			//����־
			e.printStackTrace();
			//�����ϵͳ������ֱ���׳� 
			//�Ժ����ԣ�����ֱ�ӽ�ϵͳ�쳣�׳����ɣ�
			//ע ����ʾ�����ͳһ����ϵͳ�쳣�����û���Ӧ��ʾ�������Ժ�����
			throw new RuntimeException(e);
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return user;
	}
}
