package com.mascot.campuscloud;

import org.springframework.beans.factory.annotation.Value;

public interface CampusCloudSettings {
	
	@Value("${campuscloud.name}") String CAMPUS_CLOUD_NAME = "CampusCloud";
	@Value("${campuscloud.version}") String CAMPUS_CLOUD_VERSION = "1.0.0.release";
	@Value("${campuscloud.upload}") String CAMPUS_CLOUD_UPLOAD_LOCATION = "CampusCloud/CampusCloud_Upload/";
	@Value("${server.domain}") String CAMPUS_CLOUD_DEPLOY_DOMAIN = "localhost";
	@Value("${server.port}") int CAMPUS_CLOUD_DEPLOY_PORT = 8080;

}
