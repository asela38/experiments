package org.java.monads.pojo;

import java.util.HashSet;
import java.util.Set;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

public class CompanyTestUtil {
    
    private static Lorem lorem = LoremIpsum.getInstance();
    
    public static Company sampleCompany1() {
        Company company = new Company();
        
        Employee ceo = new Employee();
        ceo.setName(lorem.getName());
        ceo.setDesignation(Designation.CEO);
        ceo.setAddress(getAddress());
        
        company.setCeo(ceo);
        
        company.setDepartments(createDepartments(10));
        
        return company;
    }

    private static Set<Department> createDepartments(int no) {
        Set<Department> set = new HashSet<>();
        return set;
    }

    private static Address getAddress() {
        Address address = new Address();
        address.setLine1(lorem.getWords(2, 3));
        address.setLine2(lorem.getWords(2, 3));
        address.setCity(lorem.getCity());
        address.setCountry(lorem.getCountry());
        return address;
    }
    
    

}
