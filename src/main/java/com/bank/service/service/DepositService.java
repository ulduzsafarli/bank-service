package com.bank.service.service;

import com.bank.service.model.deposits.DepositRequest;
import com.bank.service.model.deposits.DepositResponse;
import java.util.List;

public interface DepositService {

    void save(DepositResponse depositResponse);

    List<DepositResponse> getByCreatedOnDate(int dayOfMonth);

    void create(DepositRequest depositRequest);

}
