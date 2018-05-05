package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.CiBean;
import com.example.bean.LikeBean;
import com.example.bean.NameBean;
import com.example.bean.UserBean;

public interface LikeDao extends JpaRepository<LikeBean, Long> {

	@Query("from LikeBean b where b.uid=:uid and b.cid=:cid")
	LikeBean findById(@Param("uid") Long uid,
    		@Param("cid") Long cid);
	
	@Query("from LikeBean b where b.uid=:uid")
	List<LikeBean> findByUId(@Param("uid") Long uid);
}
