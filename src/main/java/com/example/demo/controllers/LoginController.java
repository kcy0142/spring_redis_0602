package com.example.demo.controllers;

import com.example.demo.domain.Login;
import com.example.demo.services.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	private LoginService loginService;

	@Autowired
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@RequestMapping({ "/login" })
	public String listCar(Model model) {
		return "login/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String saveOrUpdatePerson(@ModelAttribute("loginVO") Login loginVO, Model model,
			HttpServletRequest request) {

		// session 세팅
		HttpSession session = request.getSession(true);
		session.invalidate();
		session = request.getSession(true);
		session.setAttribute("id", loginVO.getId());

		// 접근제한 데이터베이스 조회 시작
		String dbList = loginService.findAll(loginVO);
		System.out.println("====================dbList:" + dbList);
		session.setAttribute("authDenyedDb", dbList);
		// 접근제한 데이터베이스 조회 끝

		if (loginVO.getId().equals("admin")) {
			return "redirect:/auth/new";

		} else {
			return "redirect:/product/list";
		}

	}

	@RequestMapping("/auth/new")
	public String retrieveDb(Model model) {

		Set<String> database = loginService.retrieveDb();
		model.addAttribute("database", database);
		return "login/authform";
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public String insertAuth(Model model, @RequestParam("id") java.lang.String userId,
			@RequestParam("authdb") List<String> authdb) {

		String dbVar = "";
		for (String db : authdb) {
			String databaseVar = new String(db);
			dbVar += "/" + databaseVar;
		}
		Login login = new Login();
		login.setId(userId);
		login.setDatabase(dbVar);
		loginService.save(login);

		Set<String> database = loginService.retrieveDb();
		model.addAttribute("database", database);
		return "login/authform";
	}

}
