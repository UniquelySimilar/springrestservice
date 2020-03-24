package com.tcoveney.springrestservice.controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
	private static final Logger logger = LogManager.getLogger(TestController.class);
	
	@GetMapping("/authuser")
	public String getAuthenticatedUser() {
		String retValue = "Authenticated user: ";

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (null == authentication) {
			retValue = "Authentication object is NULL";
		}
		else {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				retValue += "UserDetails: " + ((UserDetails)principal).getUsername();
			} else {
				retValue += principal.toString();
			}
		}
		
		//logger.debug(retValue);

		return retValue;
	}

}
