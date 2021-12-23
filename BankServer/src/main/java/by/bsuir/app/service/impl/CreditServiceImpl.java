package by.bsuir.app.service.impl;

import by.bsuir.app.dao.ContractDao;
import by.bsuir.app.dao.CreditInfoDao;
import by.bsuir.app.dao.impl.ContractDaoImpl;
import by.bsuir.app.dao.impl.CreditDaoImpl;
import by.bsuir.app.entity.Contract;
import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.service.CreditService;

import java.util.Optional;

public class CreditServiceImpl implements CreditService {
    private CreditInfoDao creditDao = new CreditDaoImpl();

    @Override
    public CreditInfo findCreditById(Long id) {
        Optional<CreditInfo> creditOpt = creditDao.findById(id);
        CreditInfo creditInfo = creditOpt.get();

        return creditInfo;
    }
}
