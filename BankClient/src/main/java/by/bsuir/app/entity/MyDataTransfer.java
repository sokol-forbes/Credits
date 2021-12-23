package by.bsuir.app.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyDataTransfer extends BaseEntity {
    static final long serialVersionUID = 42L;

    private Long contract_id;
    private String login;

    public MyDataTransfer(Long contract_id, String login) {
        this.contract_id = contract_id;
        this.login = login;
    }


}
