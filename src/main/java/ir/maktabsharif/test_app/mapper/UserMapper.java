package ir.maktabsharif.test_app.mapper;

import ir.maktabsharif.test_app.dto.user.UserRegisterRequest;
import ir.maktabsharif.test_app.dto.user.UserResponse;
import ir.maktabsharif.test_app.model.enums.Status;
import ir.maktabsharif.test_app.model.User;

public class UserMapper {
    public static User userRegisterToEntity(UserRegisterRequest userRegisterRequest){
        return User.builder()
                .email(userRegisterRequest.getEmail())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .password(userRegisterRequest.getPassword())
                .role(userRegisterRequest.getRole())
                .status(Status.PENDING)
                .build();
    }
    public static UserResponse toDTO(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus())
                .build();

    }
}
