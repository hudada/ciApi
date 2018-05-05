package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bean.BaseBean;
import com.example.bean.CiBean;
import com.example.bean.CiUserBean;
import com.example.bean.LikeBean;
import com.example.bean.NameBean;
import com.example.bean.UserBean;
import com.example.dao.CiDao;
import com.example.dao.CiUserDao;
import com.example.dao.LikeDao;
import com.example.dao.NameDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/ciuser")
public class ApiCiUserController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private NameDao nameDao;
	@Autowired
	private CiDao ciDao;
	@Autowired
	private LikeDao likeDao;
	@Autowired
	private CiUserDao ciUserDao;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public BaseBean<List<CiUserBean>> list(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		return ResultUtils.resultSucceed(ciUserDao.findByUid(Long.parseLong(uid)));
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public BaseBean<CiUserBean> add(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		String name = request.getParameter("name");
		String msg = request.getParameter("msg");
		CiUserBean bean = new CiUserBean();
		bean.setMsg(msg);
		bean.setName(name);
		bean.setUid(Long.parseLong(uid));
		return ResultUtils.resultSucceed(ciUserDao.save(bean));
	}
}
