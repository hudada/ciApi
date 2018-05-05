package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.example.bean.UserBean;
import com.example.dao.CiDao;
import com.example.dao.LikeDao;
import com.example.dao.NameDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;
import com.fasterxml.jackson.databind.util.Named;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/like")
public class ApiLikeController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private LikeDao likeDao;
	@Autowired
	private CiDao ciDao;
	@Autowired
	private NameDao nameDao;
	
	@RequestMapping(value = "/dolike", method = RequestMethod.POST)
	public BaseBean<LikeBean> dolike(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		String cid = request.getParameter("cid");
		if(likeDao.findById(Long.parseLong(uid), Long.parseLong(cid))==null) {
			LikeBean bean = new LikeBean();
			bean.setUid(Long.parseLong(uid));
			bean.setCid(Long.parseLong(cid));
			return ResultUtils.resultSucceed(likeDao.save(bean));
		}else {
			return ResultUtils.resultError("不能重复收藏");
		}
	}

	@RequestMapping(value = "/unlike", method = RequestMethod.POST)
	public BaseBean<LikeBean> unlike(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		String cid = request.getParameter("cid");
		if(likeDao.findById(Long.parseLong(uid), Long.parseLong(cid))==null) {
			return ResultUtils.resultError("取消收藏");
		}else {
			LikeBean bean = likeDao.findById(Long.parseLong(uid), Long.parseLong(cid));
			likeDao.delete(bean);
			return ResultUtils.resultSucceed("");
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<CiBean>> list(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		List<LikeBean> likeBeans = likeDao.findByUId(Long.parseLong(uid));
		List<CiBean> list = new ArrayList<>();
		for (LikeBean likeBean : likeBeans) {
			CiBean ciBean = ciDao.findOne(likeBean.getCid());
			ciBean.setLike(true);
			ciBean.setTname(nameDao.findOne(ciBean.getNid()).getName());
			list.add(ciBean);
		}
		return ResultUtils.resultSucceed(list);
	}
}
