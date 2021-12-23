package by.bsuir.app.dao;

import by.bsuir.app.entity.CreditInfo;

import java.util.List;

public interface CreditInfoDao extends BaseDao<Long, CreditInfo>{
    List<Object[]> findAllGroupedByCreditName();
    boolean save(CreditInfo creditInfo);
    boolean hqlTruncate();
}
