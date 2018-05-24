package com.mascot.campuscloud.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mascot.campuscloud.service.FileService;

import lombok.Cleanup;

@Controller
@RequestMapping("/")
public class ViewController {

	// @RequestMapping(method = RequestMethod.GET)
	// public ModelAndView getLoginView() {
	// return new ModelAndView("login");
	// }
	//
	// @RequestMapping(value = "home", method = RequestMethod.GET)
	// public ModelAndView getHomeView() {
	// return new ModelAndView("home");
	// }
	//
	// @RequestMapping(value = {"other", "404", "*"}, method = { RequestMethod.GET,
	// RequestMethod.POST, RequestMethod.DELETE,
	// RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PATCH,
	// RequestMethod.PUT, RequestMethod.TRACE })
	// public ModelAndView other() {
	// return new ModelAndView("errors/other");
	// }

	@RequestMapping(value = { "CampusCloud_Upload/{category}/{fileDigest}",
			"CampusCloud_Upload/{fileDigest}" }, method = RequestMethod.GET)
	public void getFile(@PathVariable(required = false) String category, @PathVariable String fileDigest,
			HttpServletResponse response) throws IOException {
		@Cleanup InputStream in = new FileInputStream(new File(FileService.FILE_BASE + File.separator + fileDigest));
		OutputStream out = response.getOutputStream();
		int hasRead;
		byte[] buffer = new byte[102400];
		while ((hasRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, hasRead);
		}
		// return "errors/404";
	}

}
