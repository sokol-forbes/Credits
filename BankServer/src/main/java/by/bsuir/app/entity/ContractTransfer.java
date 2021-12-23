package by.bsuir.app.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class ContractTransfer implements Serializable {
    static final long serialVersionUID = 42L;

    private Long id;
    private BigDecimal amount;
    private Date dateOfSigning;
    private Account responsible;
    private CreditInfo creditInfo;
    private Account author;
    private Currency currency;
    private String state;

    public ContractTransfer() {
    }

    public ContractTransfer(Contract contract) {
        id = contract.getId();
        amount = contract.getAmount();
        dateOfSigning = contract.getDateOfSigning();
        responsible = contract.getResponsible();
        creditInfo = contract.getCreditInfo();
        author = contract.getAuthor();
        currency = contract.getCurrency();
        state = contract.getState();
    }
}
