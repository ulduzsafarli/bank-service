package java.az.bankservice.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.az.bankservice.enumeration.auth.Role;
import java.az.bankservice.model.accounts.AccountsUserResponse;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserAccountsResponse {

    private Long id;

    private String email;

    private String cif;

    private Role role;

    private List<AccountsUserResponse> accounts;

}
