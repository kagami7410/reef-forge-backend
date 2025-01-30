package com.spring_ecommerce.himalayanfresh;

import com.spring_ecommerce.himalayanfresh.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReefForgeBackendApplication {

	@Autowired
	Config config;
	static int [] nums = {3,4,5,6};

	public static void main(String[] args) {
		SpringApplication.run(ReefForgeBackendApplication.class, args);
//		System.out.println("---------------------- This is for Tutorial! ----------------");
//
//
//		Animal dog1 = new Dog();
//		dog1.setName("panda");
//		dog1.setAge(12);
//		dog1.makeSound("Dog");
//		dog1.makeSound();
//		Farm farm1 = new Farm<Dog>();
//		farm1.setFarmName("Lazy Farm");
//		farm1.setLocation("York");
//		farm1.addAnimals(dog1);
//		System.out.println("name: " + dog1.getName() + " age: " + dog1.getAge() );
//		List<Dog> dogs = farm1.getAnimals().stream().toList();
//		dogs.stream().forEach(
//				dog -> System.out.println("dogs name is: " + dog.getName())
//		);
//
//	}

//

	}

}
