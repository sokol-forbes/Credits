package by.bsuir.app.service.snapshot;

import by.bsuir.app.dao.BaseDao;
import by.bsuir.app.dao.ContractDao;
import by.bsuir.app.dao.CreditInfoDao;
import by.bsuir.app.dao.impl.ContractDaoImpl;
import by.bsuir.app.dao.impl.CreditDaoImpl;
import by.bsuir.app.entity.Contract;
import by.bsuir.app.entity.CreditInfo;

import java.util.List;


public class Manipulator {
    private Originator originator = new Originator();
    private Caretaker caretaker = new Caretaker();
    private CreditInfoDao creditInfoDao = new CreditDaoImpl();

    public boolean saveObjectInMemory() {
        List<CreditInfo> credits = creditInfoDao.findAll();

        originator.setStorage(credits);

        caretaker.setMemento(originator.createMemento());
        System.out.println(caretaker.getMemento().getStorage());

        return true;
    }

    public boolean getObjectFromMemory() {
        Memento storage = caretaker.getMemento();
        List<CreditInfo> credits = (List<CreditInfo>) storage.getStorage();
        if (credits == null)
            return false;

        creditInfoDao.hqlTruncate();

        for (CreditInfo m : credits)
            creditInfoDao.save(m);

        return true;
    }
}
