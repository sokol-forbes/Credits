package by.bsuir.app.entity;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Account extends BaseEntity {

    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String email;
    private String role;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_id")
    private PassportData data;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    List<HistoryLog> logs;


    public Account() {
    }

    public Account(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public void addLog(HistoryLog log) {
        if (logs == null)
            logs = new ArrayList<>();

        logs.add(log);
    }

}
