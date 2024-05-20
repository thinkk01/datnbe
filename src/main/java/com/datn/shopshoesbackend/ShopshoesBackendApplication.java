package com.datn.shopshoesbackend;

import com.datn.shopshoesbackend.entity.Attribute;
import com.datn.shopshoesbackend.entity.Product;
import com.datn.shopshoesbackend.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ShopshoesBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShopshoesBackendApplication.class, args);
	}
	@Autowired
	AttributeRepository attributeRepo;

	@Override
	public void run(String... args) throws Exception {
		List<Attribute> attributes = attributeRepo.findAll();
		for(Attribute attribute: attributes){
			Product p = attribute.getProduct();
			attribute.setName(p.getName());
			attributeRepo.save(attribute);
		}
	}
}
