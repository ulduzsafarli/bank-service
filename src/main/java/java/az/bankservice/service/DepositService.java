package java.az.bankservice.service;

import java.az.bankservice.model.deposits.DepositRequest;
import java.az.bankservice.model.deposits.DepositResponse;
import java.util.List;

public interface DepositService {

    void save(DepositResponse depositResponse);

    List<DepositResponse> getByCreatedOnDate(int dayOfMonth);

    void create(DepositRequest depositRequest);

}
