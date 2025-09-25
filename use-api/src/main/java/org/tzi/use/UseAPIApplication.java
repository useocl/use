package org.tzi.use;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.tzi.use.model.Attribute;
import org.tzi.use.model.UseClass;
import org.tzi.use.model.Operation;
import org.tzi.use.repository.ClassRepo;
//localhost 8080 not in use anymore
@SpringBootApplication
public class UseAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(UseAPIApplication.class, args);
    }

//    @Bean
//    ApplicationRunner init(ClassRepo classRepo) {
//        return args -> {
//            Attribute name = new Attribute("name","String");
//            Operation operation = new Operation("operation()");
//
//            UseClass mclass2 = classRepo.save(new UseClass("TestClass2",name, operation));
//
//
//        };
//    }
}
