package com.example.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.annotations.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.bean.AdminBean;
import com.example.bean.BaseBean;
import com.example.bean.NameBean;
import com.example.bean.UserBean;
import com.example.dao.AdminDao;
import com.example.dao.NameDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.example.WebSecurityConfig;

@Controller
@RequestMapping(value = "/page/name")
public class PageNameController {

	@Autowired
	private NameDao nameDao;	

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<NameBean> add(@RequestBody NameBean nameBean) {
		return ResultUtils.resultSucceed(nameDao.save(nameBean));
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<NameBean> edit(@RequestBody NameBean nameBean) {
		return ResultUtils.resultSucceed(nameDao.save(nameBean));
	}
	
}
