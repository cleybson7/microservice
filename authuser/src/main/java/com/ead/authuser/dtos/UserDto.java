package com.ead.authuser.dtos;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public interface UserView{
        public static interface RegistrationPost{}
        public static interface UserPut{}
        public static interface PassowrdPut{}
        public static interface ImagePut{}
    }

    @JsonView(UserView.RegistrationPost.class)
    private UUID id;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Size(min = 4, max = 50)
    @JsonView(UserView.RegistrationPost.class)
    private String username;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Email
    @JsonView(UserView.RegistrationPost.class)
    private String email;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PassowrdPut.class})
    @Size(min = 6, max = 20)
    @JsonView({UserView.RegistrationPost.class, UserView.PassowrdPut.class})
    private String password;

    @NotBlank(groups = UserView.PassowrdPut.class)
    @Size(min = 6, max = 20)
    @JsonView(UserView.PassowrdPut.class)
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullname;

    @JsonView({UserView.RegistrationPost.class})
    private UserStatus userStatus;

    @JsonView({UserView.RegistrationPost.class})
    private UserType userType;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView(UserView.ImagePut.class)
    private String imageUrl;

    @JsonView(UserView.RegistrationPost.class)
    private LocalDateTime creationDate;

    @JsonView(UserView.RegistrationPost.class)
    private LocalDateTime lastUpdateDate;
}
