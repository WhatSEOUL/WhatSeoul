package com.example.whatseoul.dto.response;

import com.example.whatseoul.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String email;
    private String username;
    public static UserResponseDto from(User user){
        return new UserResponseDto(
                user.getUserEmail(),
                user.getUserName());
    }
}
