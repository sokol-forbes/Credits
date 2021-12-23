package by.bsuir.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "personal_data")
@Data
public class PassportData extends BaseEntity {

    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String thirdname;
    private Integer age;
    private String gender;

    @Column(name = "pass_number")
    private String passNumber;

    @Column(name = "iden_number")
    private String idenNumber;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Column(name = "date_of_expirity")
    private Date dateOfExpirity;

}
