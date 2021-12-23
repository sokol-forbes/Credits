package by.bsuir.app.entity;

import by.bsuir.app.entity.enums.SecurityType;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "credit_info")
@Getter
@Setter
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class CreditInfo extends BaseEntity {

    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name="percent_rate")
    private BigDecimal percentRate;
    private Integer term;

    @Column(name = "loan_type")
    private String loanType;

    @Column(name = "providing_way")
    private String providingWay;

    @Column(name = "security")
    private String securityType;

    @Column(name = "icon_url")
    private String iconUrl;

    private String author;

    public CreditInfo() {

    }
}
