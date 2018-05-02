package com.mascot.campuscloud.config;

import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class CampusCloudWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private String MULTIPART_LOCATION = "";
	private long MULTIPART_MAX_FILE_SIZE = -1L;
	private long MULTIPART_MAX_REQUEST_SIZE = -1L;
	private int MULTIPART_FILE_SIZE_THRESHOLD = 0;

	{
		String systmpdir = System.getProperty("java.io.tmpdir");
		MULTIPART_LOCATION = systmpdir + File.separator + "CampusCloud" + File.separator;
		File campuscloud = new File(MULTIPART_LOCATION);
		if (campuscloud.isFile()) {
			// throw new Exception("工程需要的是文件夹" + campuscloud.getAbsolutePath() + ",但您的文件夹是文件");
			System.exit(-1);
		}
		if (!campuscloud.exists()) {
			campuscloud.mkdirs();
		}
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(MULTIPART_LOCATION, MULTIPART_MAX_FILE_SIZE,
				MULTIPART_MAX_REQUEST_SIZE, MULTIPART_FILE_SIZE_THRESHOLD));
	}

	/**
	 * 配置data repositories & business services
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.out.println("root context initialized");
		return new Class<?>[] { RootConfig.class };
	}

	/**
	 * 配置Web组件，如Controller，ViewResolver，HandlerMapping
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("servlet context initialized");
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("startup");
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter",
				CharacterEncodingFilter.class);
		characterEncodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
		super.onStartup(servletContext);
	}
}
