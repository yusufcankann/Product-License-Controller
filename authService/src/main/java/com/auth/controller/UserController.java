package com.auth.controller;

import com.auth.domain.Role;
import com.auth.domain.SystemUser;
import com.auth.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


//If you want to control User or Role information or want to add register endpoint you can use commented features.

//    @GetMapping("/users")
//    public ResponseEntity<List<SystemUser>> getUser(){
//        return ResponseEntity.ok().body(userService.getUsers());
//    }
//
//    @PostMapping("/user/save")
//    public ResponseEntity<SystemUser> saveUser(@RequestBody SystemUser systemUser){
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.saveUser(systemUser));
//    }
//
//    @PostMapping("/role/save")
//    public ResponseEntity<Role> saveRole(@RequestBody Role role){
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.saveRole(role));
//    }
//
//    @PostMapping("/role/addtouser")
//    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserFrom form){
//        userService.addRoleToUser(form.getUsername(), form.getRolename());
//        return ResponseEntity.ok().build();
//    }

    @GetMapping(value = "/token/validate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ValidationResponse> validate(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        List<SimpleGrantedAuthority> grantedAuthorities = (List<SimpleGrantedAuthority>) request.getAttribute("roles");
        return ResponseEntity.ok(ValidationResponse.builder().username(username).authorities(grantedAuthorities)
                .isAuthenticated(true).build());
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                /*algorithm can be put in a util class.*/
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();

                SystemUser systemUser = userService.getUser(username);

                String access_token= JWT.create()
                        .withSubject(systemUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000)) // ten minute
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", systemUser.getRoles().stream().map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);


                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception e){
                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());

                Map<String,String> error = new HashMap<>();
                error.put("error_message",e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        }else{
            throw new RuntimeException("Refresh Token is Missing");
        }
    }

    @Getter
    @Builder
    @ToString
    public static class ValidationResponse {
        private boolean isAuthenticated;
        private String username;
        private List<SimpleGrantedAuthority> authorities;
    }

}