package java.az.bankservice.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.az.bankservice.enumeration.auth.Role;
import java.az.bankservice.model.users.profile.UserProfileDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private Long id;

    private String email;

    private String cif;

    private Role role;

    private UserProfileDto userProfileDto;
}
