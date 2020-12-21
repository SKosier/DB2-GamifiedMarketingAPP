package it.polimi.db;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GamifiedMarketingAPP {

	public static void main(String[] args) {
		//new File(FileUploadController.uploadDirectory).mkdir();
		SpringApplication.run(GamifiedMarketingAPP.class, args);
	}

}
