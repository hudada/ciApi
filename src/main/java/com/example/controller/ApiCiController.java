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
import com.example.bean.LikeBean;
import com.example.bean.NameBean;
import com.example.bean.UserBean;
import com.example.dao.CiDao;
import com.example.dao.LikeDao;
import com.example.dao.NameDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/ci")
public class ApiCiController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private NameDao nameDao;
	@Autowired
	private CiDao ciDao;
	@Autowired
	private LikeDao likeDao;

	@RequestMapping(value = "/rank", method = RequestMethod.GET)
	public BaseBean<CiBean> rank(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		List<CiBean> ciBeans = ciDao.findAll();
		Random random = new Random();
		int select = random.nextInt(ciBeans.size());
		CiBean ciBean = ciBeans.get(select);
		LikeBean bean = likeDao.findById(Long.parseLong(uid), 
				ciBean.getId());
		if (bean == null) {
			ciBean.setLike(false);
		}else {
			ciBean.setLike(true);
		}
		ciBean.setTname(nameDao.findOne(ciBean.getNid()).getName());
		return ResultUtils.resultSucceed(ciBean);
	}
	
	@RequestMapping(value = "/one", method = RequestMethod.GET)
	public BaseBean<CiBean> one(HttpServletRequest request) {
		String nid = request.getParameter("nid");
		String uid = request.getParameter("uid");
		List<CiBean> list = ciDao.findByNid(Long.parseLong(nid));
		if (list == null) {
			return ResultUtils.resultSucceed("");
		}else {
			CiBean ciBean = list.get(0);
			LikeBean bean = likeDao.findById(Long.parseLong(uid), 
					ciBean.getId());
			if (bean == null) {
				ciBean.setLike(false);
			}else {
				ciBean.setLike(true);
			}
			ciBean.setTname(nameDao.findOne(ciBean.getNid()).getName());
			return ResultUtils.resultSucceed(ciBean);
		}
	}
}
