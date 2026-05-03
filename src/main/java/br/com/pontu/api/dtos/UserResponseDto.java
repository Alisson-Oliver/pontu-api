package br.com.pontu.api.dtos;

import br.com.pontu.api.entities.User;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        String photoUrl,
        LocalDateTime createdAt
) {

    public static UserResponseDto fromEntity(User user) {
        return  new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhotoUrl(),
                user.getUpdateAt()
        );
    }
}
