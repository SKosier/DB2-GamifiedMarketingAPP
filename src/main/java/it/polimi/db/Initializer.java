package it.polimi.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.polimi.db.entity.User;
import it.polimi.db.service.UserService;
import it.polimi.db.util.Util;

@Component
public class Initializer {
	@Autowired
	UserService userService;
	
	@Value("${db2.test.user.names}")
	private String testNames;
	
	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		String[] names = testNames.split(",");
		User user = null;
		for(int i = 0; i < names.length; i++) {
			user = makeUser(names[i]);
			userService.createUser(user);
		}
	}

	private User makeUser(String name) {
		User user = new User();
		user.setUsername(name);
		user.setEmail(name + "@gmail.com");
		user.setPasswordHash(Util.calculateHash(name+"123"));
		return user;
	}
}
