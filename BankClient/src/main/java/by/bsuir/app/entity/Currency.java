package by.bsuir.app.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency extends BaseEntity {

    static final long serialVersionUID = 42L;

    private Long id;
    private String name;
    private String genericSymbol;
    private String symbol;

    public Currency() {
    }

    public Currency(CurrencyTransfer c) {
        this(c.getId(), c.getName(), c.getGenericSymbol(), c.getSymbol());
    }

    public Currency(Long id) {
        this.id = id;
    }

    public Currency(Long id, String name, String genericSymbol, String symbol) {
        this.id = id;
        this.name = name;
        this.genericSymbol = genericSymbol;
        this.symbol = symbol;
    }


}
