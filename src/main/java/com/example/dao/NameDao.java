package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.NameBean;
import com.example.bean.UserBean;

public interface NameDao extends JpaRepository<NameBean, Long> {

}
