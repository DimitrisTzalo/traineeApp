package myy803.springboot.trainee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class traineeApplication {


	public static void main(String[] args) {
		SpringApplication.run(traineeApplication.class, args);
	}

}
