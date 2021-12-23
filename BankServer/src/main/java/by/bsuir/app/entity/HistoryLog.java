package by.bsuir.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "history_log")
public class HistoryLog extends BaseEntity {

    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp entrance;

    @Temporal(TemporalType.DATE)
    @Column(name="entrance", insertable = false, updatable = false)
    private Date entranceDate;

    @Column(columnDefinition = "BIGINT, foreign key (account_id) REFERENCES account(id)")
    private Long account_id;

    public HistoryLog(Timestamp timestamp) {
        super();
        entrance = timestamp;
    }
}
