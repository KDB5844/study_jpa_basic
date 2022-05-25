package hellojpa.equalvalue;

import hellojpa.embedded.Address;
import hellojpa.template.JpaTemplate;

import javax.persistence.EntityManager;

public class ValueExam {

    public static void main(String[] args) {

        int a = 10;
        int b = 10;

        System.out.println("(a == b) = " + (a == b));

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");

        System.out.println("(address1.equals(address2)) = " + (address1.equals(address2)));
    }

}
