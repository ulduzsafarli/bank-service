package java.az.bankservice.model.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.az.bankservice.model.users.profile.UserProfileDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {

    @Email(message = "Invalid email format", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    @NotBlank(message = "Email must not be null")
    private String email;

    private UserProfileDto userProfile;

}