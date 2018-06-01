package org.java.monads.pojo;

import java.util.concurrent.ThreadLocalRandom;

public enum Designation {
    TRAINEE_ENGINEER, SOFTWARE_ENGINEER, SYSTEM_ANALYST, 
    PROGRAMMER_ANALYST, SENIOR_SOFTWARE_ENGINEER, PROJECT_LEAD, 
    PROJECT_MANAGER, PROGRAM_MANAGER, ARCHITECT, TECHNICAL_SPECIALIST, 
    DELIVER_MANAGER, DELIVERY_HEAD, BUSINESS_ANALYST, 
    DIRECTOR_TECHNOLOGY, DIRECTOR, VICE_PRESIDENT, PRESIDENT, CEO;


    private static Designation[] staff = new Designation[] {
      TRAINEE_ENGINEER, SOFTWARE_ENGINEER, SYSTEM_ANALYST,
      PROGRAMMER_ANALYST, SOFTWARE_ENGINEER, PROJECT_LEAD,
      PROJECT_MANAGER, PROGRAM_MANAGER, ARCHITECT, TECHNICAL_SPECIALIST,
      BUSINESS_ANALYST
    };
    
    private static Designation[] head = new Designation[] {
      DELIVER_MANAGER, DELIVERY_HEAD, DIRECTOR_TECHNOLOGY,
      DIRECTOR, VICE_PRESIDENT, PRESIDENT
    };
     
    public static Designation randomStaff() {
        int l = staff.length;
        return staff[ThreadLocalRandom.current().nextInt(0,l)];
    }
    
    public static Designation randomHead() {
        int l = head.length;
        return head[ThreadLocalRandom.current().nextInt(0,l)];
    }

}