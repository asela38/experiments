package test.functional;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

    private String    firstName;
    private String    lastName;
    private LocalDate birthDate;

}
