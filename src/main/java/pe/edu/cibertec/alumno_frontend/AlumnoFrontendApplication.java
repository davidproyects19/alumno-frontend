package pe.edu.cibertec.alumno_frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AlumnoFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlumnoFrontendApplication.class, args);
	}

}
