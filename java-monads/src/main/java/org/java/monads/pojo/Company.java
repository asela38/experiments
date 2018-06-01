package org.java.monads.pojo;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Company {

    private String name;
    private Employee ceo;
    private Set<Department> departments;
    
}
