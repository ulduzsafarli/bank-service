package com.bank.service.model.users;

import com.bank.service.model.accounts.AccountsUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.bank.service.enumeration.auth.Role;

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
