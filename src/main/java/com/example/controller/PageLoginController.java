package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.bean.CiBean;
import com.example.bean.NameBean;
import com.example.bean.UserBean;
import com.example.dao.AdminDao;
import com.example.dao.CiDao;
import com.example.dao.NameDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.spring.web.json.Json;

import com.example.WebSecurityConfig;

@Controller
public class PageLoginController {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private NameDao nameDao;
	@Autowired
	private CiDao ciDao;


	// 返回登录页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(ModelMap map) {
		map.addAttribute("title","后台管理");
		return "newlogin";
	}

	// 登录接口
	@RequestMapping(value = "/dologin", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<String> userLogin(@RequestBody AdminBean adminBean, HttpSession session) {
		AdminBean admin = adminDao.findByNameAndPwd(adminBean.getUserName(), adminBean.getPwd());
		if (admin != null) {
			session.setAttribute(WebSecurityConfig.SESSION_KEY, adminBean);
			return ResultUtils.resultSucceed("登陆成功");
		} else {
			return ResultUtils.resultError("账号或密码错误");
		}
	}

	// 退出登录接口
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginOut(HttpSession session) {
		session.removeAttribute(WebSecurityConfig.SESSION_KEY);
		return "redirect:/login";
	}

	private ModelMap getPub(ModelMap map, HttpSession session,int position) {
		AdminBean admin = (AdminBean) session.getAttribute(WebSecurityConfig.SESSION_KEY);
		map.addAttribute("name", admin.getUserName());
		map.addAttribute("title","后台管理");
		map.addAttribute("left",""
				+ "<li>"
					+ "<a href=\"#\">词牌名</a>"	
				+ "</li>"
				+ "<li "+isActive(1,position)+">"
					+ "<a href=\"/nameAdd\">"
					+ "<i class=\"icon-chevron-right\"></i>新增词牌名</a>"
				+ "</li>"
				+ "<li "+isActive(2,position)+">"
					+ "<a href=\"/nameManager\">"
					+ "<i class=\"icon-chevron-right\"></i>词牌名列表</a>"
				+ "</li>"
				+ "<li>"
					+ "<a href=\"#\">词</a>"	
				+ "</li>"
				+ "<li "+isActive(3,position)+">"
					+ "<a href=\"/ciAdd\">"
					+ "<i class=\"icon-chevron-right\"></i>新增词</a>"
				+ "</li>"
				+ "<li "+isActive(4,position)+">"
					+ "<a href=\"/ciManager\">"
					+ "<i class=\"icon-chevron-right\"></i>词列表</a>"
				+ "</li>");
		return map;
	}

	private String isActive(int curr,int position) {
		return position==curr?"class=\"active\"":"";
	}

	// 返回管理首页
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap map, HttpSession session) {
		getPub(map, session,0);
		return "newindex";
	}

	// ----------------------------------------------------------------
	@RequestMapping(value = "/nameManager", method = RequestMethod.GET)
	public String nameManager(ModelMap map, HttpSession session) {
		getPub(map, session,2);
		List<NameBean> list = nameDao.findAll();
		map.addAttribute("list", list);
		return "name/newtable";
	}
	
	@RequestMapping(value = "/nameAdd", method = RequestMethod.GET)
	public String nameAdd(ModelMap map, HttpSession session) {
		getPub(map, session,1);
		return "name/addform";
	}
	
	@RequestMapping(value = "/nameEdit/{id}", method = RequestMethod.GET)
	public String nameEdit(@PathVariable String id,
			ModelMap map, HttpSession session) {
		getPub(map, session,2);
		map.addAttribute("data", nameDao.findOne(Long.parseLong(id)));
		return "name/editform";
	}
	
	@RequestMapping(value = "/ciManager", method = RequestMethod.GET)
	public String ciManager(ModelMap map, HttpSession session) {
		getPub(map, session,4);
		List<CiBean> list = ciDao.findAll();
		for (CiBean ciBean : list) {
			ciBean.setTname(nameDao.findOne(ciBean.getNid()).getName());
		}
		map.addAttribute("list", list);
		return "ci/newtable";
	}
	
	@RequestMapping(value = "/ciAdd", method = RequestMethod.GET)
	public String ciAdd(ModelMap map, HttpSession session) {
		getPub(map, session,3);
		map.addAttribute("list", nameDao.findAll());
		return "ci/addform";
	}
	
	@RequestMapping(value = "/ciEdit/{id}", method = RequestMethod.GET)
	public String ciEdit(@PathVariable String id,
			ModelMap map, HttpSession session) {
		getPub(map, session,4);
		map.addAttribute("list", nameDao.findAll());
		map.addAttribute("data", ciDao.findOne(Long.parseLong(id)));
		return "ci/editform";
	}
}
