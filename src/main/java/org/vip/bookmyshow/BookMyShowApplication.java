package org.vip.bookmyshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class BookMyShowApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}
}
