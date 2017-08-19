package cn.tedu.ems.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.tedu.ems.entity.User;
//@Repository("userDAO")
public class UserDAOSpringJdbcImpl implements UserDAO{
	@Autowired
	@Qualifier("jt")
	private JdbcTemplate jt;

	public User findByUsername(String username) {
		String sql="select * from t_yzc "
				+ "where username=?";
		Object[] args={username};
		User user=null;
		try{
		user=jt.queryForObject(sql, args,new UserRowMapper());
		}catch(EmptyResultDataAccessException e1){
			return null;
		}
		return user;
	}
	
	class UserRowMapper implements RowMapper<User>{

		public User mapRow(ResultSet rs, int index) throws SQLException {
			User user=new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPwd(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setGender(rs.getString("gender"));
			return user;
		}
		
	}

}

