package by.bsuir.app.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class Contract extends BaseEntity {

    static final long serialVersionUID = 42L;

    private Long id;
    private BigDecimal amount;
    private Date dateOfSigning;
    private Account responsible;
    private CreditInfo creditInfo;
    private Account author;
    private Currency currency;
    private String state;

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", amount=" + amount +
                ", dateOfSigning=" + dateOfSigning +
                ", responsible=" + responsible +
                ", creditInfo=" + creditInfo +
                ", author=" + author +
                ", currency=" + currency +
                ", state='" + state + '\'' +
                '}';
    }
}
