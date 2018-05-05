package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.CiBean;
import com.example.bean.CiUserBean;
import com.example.bean.NameBean;
import com.example.bean.UserBean;

public interface CiUserDao extends JpaRepository<CiUserBean, Long> {
	@Query("from CiUserBean b where b.uid=:uid")
    List<CiUserBean> findByUid(@Param("uid") Long uid);
}
