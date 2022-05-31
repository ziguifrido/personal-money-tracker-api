package dev.marcosoliveira.personalmoneytrackerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class PersonalMoneyTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalMoneyTrackerApiApplication.class, args);
	}

}
