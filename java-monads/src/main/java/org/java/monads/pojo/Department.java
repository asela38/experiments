package org.java.monads.pojo;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Department {
    private String name;
    private Employee head;
    private Set<Employee> staff;
}
