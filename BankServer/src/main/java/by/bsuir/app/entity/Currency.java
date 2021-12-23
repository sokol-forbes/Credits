package by.bsuir.app.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class Currency extends BaseEntity {

    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "generic_symbol")
    private String genericSymbol;
    private String symbol;

    public Currency() {
    }

    public Currency(Long id) {
        this.id = id;
    }
}
