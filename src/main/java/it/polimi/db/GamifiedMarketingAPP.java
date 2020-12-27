package it.polimi.db;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.polimi.db.rest.CreationController;


@SpringBootApplication
public class GamifiedMarketingAPP {

	public static void main(String[] args) {
		new File(CreationController.uploadDirectory).mkdir();
		SpringApplication.run(GamifiedMarketingAPP.class, args);
	}

}
