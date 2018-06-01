package org.java.monads.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Employee {
    private String name;
    private Designation designation;
    private Address address;
}
