package com.mascot.campuscloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ViewController {

//	@RequestMapping(method = RequestMethod.GET)
//	public ModelAndView getLoginView() {
//		return new ModelAndView("login");
//	}
//
//	@RequestMapping(value = "home", method = RequestMethod.GET)
//	public ModelAndView getHomeView() {
//		return new ModelAndView("home");
//	}
//
//	@RequestMapping(value = {"other", "404", "*"}, method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
//			RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.TRACE })
//	public ModelAndView other() {
//		return new ModelAndView("errors/other");
//	}
	
	@RequestMapping(value = {"CampusCloud_Upload/{category}/{fileDigest}", "CampusCloud_Upload/{fileDigest}"}, 
			method = RequestMethod.GET)
	public String getFile(@PathVariable(required = false) String category, @PathVariable String fileDigest) {
		return "errors/404";
	}

}
