package org.tzi.use;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
//            ClassDTO mclass2 = classRepo.save(new ClassDTO("TestClass2",name, operation));
//
//
//        };
//    }
}
