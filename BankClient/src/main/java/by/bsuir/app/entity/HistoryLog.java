package by.bsuir.app.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class HistoryLog extends BaseEntity {

    static final long serialVersionUID = 42L;

    private Long id;
    private Timestamp entrance;
    private Date entranceDate;
    private Long account_id;

    public HistoryLog(Timestamp timestamp) {
        super();
        entrance = timestamp;
    }
}
