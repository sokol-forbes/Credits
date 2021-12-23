package by.bsuir.app.service.impl;

import by.bsuir.app.dao.AccountDao;
import by.bsuir.app.dao.ContractDao;
import by.bsuir.app.entity.ContractTransfer;
import by.bsuir.app.entity.MyDataTransfer;
import by.bsuir.app.dao.impl.AccountDaoImpl;
import by.bsuir.app.dao.impl.ContractDaoImpl;
import by.bsuir.app.entity.Account;
import by.bsuir.app.entity.Contract;
import by.bsuir.app.entity.enums.ContractStates;
import by.bsuir.app.service.ContractService;
import by.bsuir.app.util.HibernateUtil;

import java.util.Optional;

public class ContractServiceImpl implements ContractService {
    private ContractDao contractDao = new ContractDaoImpl();
    private AccountDao accountDao = new AccountDaoImpl();

    @Override
    public Contract findContractById(Long id) {
        Optional<Contract> contractOptional = contractDao.findById(id);
        Contract contract = contractOptional.get();
        return contract;
    }

    @Override
    public boolean approveContract(MyDataTransfer msg) {
        Optional<Contract> contractOptional = contractDao.findById(msg.getContract_id());
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();

            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            contract.setDateOfSigning(sqlDate);
            contract.setState(ContractStates.APPROVED.getState());
            Account account = accountDao.findByLogin(msg.getLogin());

            contract.setResponsible(account);

            contractDao.saveOrUpdate(contract);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean denyContract(MyDataTransfer msg) {
        Optional<Contract> contractOptional = contractDao.findById(msg.getContract_id());
        Contract contract = contractOptional.get();

        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        contract.setDateOfSigning(sqlDate);
        contract.setState(ContractStates.DENIED.getState());
        Account account = accountDao.findByLogin(msg.getLogin());

        contract.setResponsible(account);

        contractDao.saveOrUpdate(contract);
        return true;
    }

}
