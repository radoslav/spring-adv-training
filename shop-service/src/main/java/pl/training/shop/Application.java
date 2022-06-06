package pl.training.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "pl.training")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
