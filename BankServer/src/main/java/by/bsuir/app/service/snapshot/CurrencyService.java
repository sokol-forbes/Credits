package by.bsuir.app.service.snapshot;

import by.bsuir.app.dao.CurrencyDao;
import by.bsuir.app.dao.impl.CurrencyDaoImpl;
import by.bsuir.app.entity.Currency;

import java.util.Optional;

public class CurrencyService {
    private CurrencyDao currencyDao = new CurrencyDaoImpl();

    public Currency findCurrecyById(Long id) {
        Optional<Currency> byId = currencyDao.findById(id);
        Currency currency = byId.get();

        return currency;
    }
}
