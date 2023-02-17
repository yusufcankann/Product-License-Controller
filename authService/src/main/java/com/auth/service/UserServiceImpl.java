package com.auth.service;

import com.auth.domain.Role;
import com.auth.domain.SystemUser;
import com.auth.repository.RoleRepo;
import com.auth.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = userRepo.findByUsername(username);

        if(systemUser == null){
            log.error("User not found in the database {}",username);
            throw new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User found in the database {}",username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        systemUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(systemUser.getUsername(), systemUser.getPassword(),authorities);
    }

    @Override
    public SystemUser saveUser(SystemUser systemUser) {
        log.info("saving new user to the database {}", systemUser.getUsername());
        systemUser.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        return userRepo.save(systemUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role to the database {}",role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding new role:{}  to the user: {}",roleName,username);
        SystemUser systemUser = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        systemUser.getRoles().add(role);
    }

    @Override
    public SystemUser getUser(String username) {
        log.info("geting user from the database {}",username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<SystemUser> getUsers() {
        log.info("geting all users from the database");
        return userRepo.findAll();
    }

}

