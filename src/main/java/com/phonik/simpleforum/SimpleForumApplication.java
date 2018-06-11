package com.phonik.simpleforum;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Jpa Repository auto configuration has to be excluded for manual bean configuration to work
 * */
@SpringBootApplication(scanBasePackages = "com.phonik.simpleforum", exclude = {JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@EnableJpaRepositories(basePackages = "com.phonik.simpleforum.repository")
public class SimpleForumApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SimpleForumApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
	}

}
