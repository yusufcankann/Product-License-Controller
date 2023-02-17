package com.auth.repository;

import com.auth.domain.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<SystemUser,Long> {

    SystemUser findByUsername(String username);

}
