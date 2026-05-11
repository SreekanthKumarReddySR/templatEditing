package com.classplus.greetings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAuthRequest {
    private String token;
    private String name;
    private String email;
    private String profilePictureUrl;
}
