package com.gol.shop;

import com.gol.shop.database.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EShopApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(EShopApplication.class, args);
        System.out.println(context.getBeanDefinitionCount());
        var companyRepository = context.getBean(ProductRepository.class);

    }

}
