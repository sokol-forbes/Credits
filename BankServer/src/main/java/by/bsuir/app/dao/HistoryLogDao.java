package by.bsuir.app.dao;

import by.bsuir.app.entity.HistoryLog;

import java.util.List;

public interface HistoryLogDao extends BaseDao<Long, HistoryLog> {
    List<HistoryLog> findUserLogHistoryByLogin(String userLogin);
    List<Object[]> findAllGropedByDate();
    List<HistoryLog> findAllUserLaunches();
}
