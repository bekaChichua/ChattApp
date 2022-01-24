package com.beka.chatapp;

import com.beka.chatapp.entity.AppUser;
import com.beka.chatapp.entity.Role;
import com.beka.chatapp.repo.AppUserRepo;
import com.beka.chatapp.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SpringBootApplication
public class ChatAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatAppApplication.class, args);
    }


    private final AppUserRepo userRepo;
    private final RoleRepo roleRepo;

    @Bean
    CommandLineRunner commandLineRunner(){
        return args ->{
            Role role = new Role(null, "ADMIN", null);
            AppUser user = new AppUser(null, "beka", "123", Stream.of(role).collect(Collectors.toList()));
            role.setUser(user);
            userRepo.save(user);
            roleRepo.save(role);
        };
    }

}
