package by.bsuir.app.dao;

import by.bsuir.app.entity.PassportData;

import java.util.List;

public interface PassportDao extends BaseDao<Long, PassportData> {
    public List<Object[]> findAgePercentProportion();

}