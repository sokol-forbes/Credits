package by.bsuir.app.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class PassportData extends BaseEntity {

    static final long serialVersionUID = 42L;

    private Long id;
    private String name;
    private String surname;
    private String thirdname;
    private Integer age;
    private String gender;
    private String passNumber;
    private String idenNumber;
    private Date dateOfBirth;
    private Date dateOfIssue;
    private Date dateOfExpirity;
}
