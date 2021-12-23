package by.bsuir.app.service.impl;

import by.bsuir.app.dao.CreditInfoDao;
import by.bsuir.app.dao.CurrencyDao;
import by.bsuir.app.dao.impl.CreditDaoImpl;
import by.bsuir.app.dao.impl.CurrencyDaoImpl;
import by.bsuir.app.entity.CreditInfo;
import by.bsuir.app.entity.Currency;
import by.bsuir.app.entity.CurrencyTransfer;

import java.util.Optional;

public class CurrencyService {
    private CurrencyDao currencyDao = new CurrencyDaoImpl();

    public Currency findCreditById(Long id) {
        Optional<Currency> currency = currencyDao.findById(id);
        Currency currencyTransfer = currency.get();
        return currencyTransfer;
    }
}
