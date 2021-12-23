package by.bsuir.app.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class CurrencyTransfer implements Serializable {

    static final long serialVersionUID = 42L;

    private Long id;
    private String name;
    private String genericSymbol;
    private String symbol;

    public CurrencyTransfer(Long id, String name, String genericSymbol, String symbol) {
        this.id = id;
        this.name = name;
        this.genericSymbol = genericSymbol;
        this.symbol = symbol;
    }

    public CurrencyTransfer(Currency c) {
        this(c.getId(), c.getName(), c.getGenericSymbol(), c.getSymbol());
    }
}
