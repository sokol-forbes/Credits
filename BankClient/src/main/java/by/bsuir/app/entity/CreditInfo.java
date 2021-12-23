package by.bsuir.app.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditInfo extends BaseEntity {

    static final long serialVersionUID = 42L;

    private Long id;
    private String name;
    private BigDecimal percentRate;
    private Integer term;
    private String loanType;
    private String providingWay;
    private String securityType;
    private String iconUrl;
    private String author;

}
