package com.beka.chatapp.service;

import com.beka.chatapp.repo.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final AppUserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userRepo.getAppUserByUsername(username);
        MyUserDetails userDetails;
        if(optionalUser.isPresent()){
            userDetails = new MyUserDetails(optionalUser.get());
        }else {
            throw new UsernameNotFoundException(String.format("User with name: {0} was not found", username));
        }
        return userDetails;
    }
}
