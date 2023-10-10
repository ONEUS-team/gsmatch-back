package oneus.GSMATCH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GsmatchApplication {
	// 하이 나 태연

	public static void main(String[] args) {
		SpringApplication.run(GsmatchApplication.class, args);
	}

}
