package loginStudy.totalLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TotalLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(TotalLoginApplication.class, args);
	}

}
