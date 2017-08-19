package cn.tedu.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import cn.tedu.ems.entity.User;
/**
 * 持久层实现类
 * @author Administrator
 *
 */

//@Repository("userDAO")  //持久
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
			//记日志
			e.printStackTrace();
			//如果是系统异样，直接抛出 
			//稍后重试（在这直接将系统异常抛出即可）
			//注 ：表示层可以统一处理系统异常，给用户响应提示，比如稍后重试
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
