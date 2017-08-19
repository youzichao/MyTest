package cn.tedu.ems.dao;

import org.springframework.stereotype.Repository;

import cn.tedu.ems.annotations.MyBatisRepository;
import cn.tedu.ems.entity.User;
@Repository("userDAO")
@MyBatisRepository
public interface UserDAO {
	public User findByUsername(String username);
}
