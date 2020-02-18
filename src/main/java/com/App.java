package com;

import com.solera.apac1.qualitydashboard.repo.TestRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.solera.apac1.qualitydashboard.repo")
@EntityScan("com.solera.apac1.qualitydashboard.model")
@SpringBootApplication(scanBasePackages = {"com.solera.apac1.qualitydashboard"})
public class App implements CommandLineRunner{

    @Autowired
    private TestRunRepository testRunRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
