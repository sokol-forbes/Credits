package by.bsuir.app.entity;

import by.bsuir.app.entity.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MyDataTransfer extends BaseEntity {
    static final long serialVersionUID = 42L;

    private Long contract_id;
    private String login;
}
