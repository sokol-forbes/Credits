package by.bsuir.app.dao;

import by.bsuir.app.entity.Contract;

import java.util.List;

public interface ContractDao extends BaseDao<Long, Contract> {

    List<Contract>findAllByUserId(Long userId);
    boolean save(Contract contract);
    int findUserContractsCountByLogin(String login);

}
