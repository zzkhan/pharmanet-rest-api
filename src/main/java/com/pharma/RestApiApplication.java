package com.pharma;

import com.pharma.service.TransporterFacade;
import com.pharma.temp.AuthBuilder;
import com.pharma.temp.AuthBuilderFactory;
import com.pharma.temp.Authorisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

}
