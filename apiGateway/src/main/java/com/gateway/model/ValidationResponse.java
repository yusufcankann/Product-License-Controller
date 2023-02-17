package com.gateway.model;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {
    private boolean isAuthenticated;
    private String username;
    private List<Authorities> authorities;
}