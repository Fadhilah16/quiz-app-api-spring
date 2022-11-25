package com.quiz.app.dtos.auth;

import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    @NonNull
    private String token;
    private String type = "Bearer";
    @NonNull
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private List<String> roles;
}
