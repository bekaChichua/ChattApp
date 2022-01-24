package com.beka.chatapp.repo;

import com.beka.chatapp.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {
    public Optional<AppUser> getAppUserByUsername(String username);
}
