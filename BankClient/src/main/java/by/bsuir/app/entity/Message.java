package by.bsuir.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BaseEntity{

    static final long serialVersionUID = 42L;

    private String recipient;
    private String topic;
    private String Message;

}
