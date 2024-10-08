package com.bank.service.enumeration.auth;

import lombok.Getter;

@Getter
public enum UrlMapping {
    MANAGER(Role.MANAGER.name(), new String[]{
            "/api/v1/accounts/{id}/status/{status}",
            "/api/v1/accounts/search",
            "/api/v1/accounts",
            "/api/v1/support/**",
            "/api/v1/currency/file",
            "/api/v1/notifications/**",
            "/api/v1/users/**"
    }),

    ADMIN(Role.ADMIN.name(), new String[]{
            "/api/v1/transactions/**",
            "/api/v1/operations/deposit-scheduler",
            "/api/v1/currency",
            "/api/v1/exchange/**",
    }),

    PERMIT_ALL(new String[]{
            "/api/v1/auth/authenticate",
            "/api/v1/auth/register",
            "/api/v1/support/request",
            "/api/v1/exchange/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/swagger-ui/**",
            "/swagger-ui.html"
    }),

    ANY_AUTHENTICATED(new String[]{
            "/api/v1/auth/change-password",
            "/api/v1/operations/transfer",
            "/api/v1/operations/withdrawal",
            "/api/v1/deposits",
            "/api/v1/operations/{accountNumber}/balance"
    }),

    LIST_URL(new String[]{
            "/api/v1/accounts/{id}",
            "/api/v1/notifications/{id}",
            "/api/v1/users"
    });

    private final String role;
    private final String[] urls;

    UrlMapping(String role, String[] urls) {
        this.role = role;
        this.urls = urls;
    }

    UrlMapping(String[] urls) {
        this.role = null;
        this.urls = urls;
    }
}
