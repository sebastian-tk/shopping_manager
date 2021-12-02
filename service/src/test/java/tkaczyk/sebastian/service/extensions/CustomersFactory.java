package tkaczyk.sebastian.service.extensions;

import tkaczyk.sebastian.persistence.Customer;

import java.math.BigDecimal;

public interface CustomersFactory {
    Customer zygmunt = Customer
            .builder()
            .id(1)
            .name("Zygmunt")
            .surname("Nowak")
            .age(65)
            .cash(BigDecimal.valueOf(30000))
            .build();

    Customer tomasz = Customer
            .builder()
            .id(2)
            .name("Tomasz")
            .surname("Jonak")
            .age(25)
            .cash(BigDecimal.valueOf(1500))
            .build();

    Customer iza = Customer
            .builder()
            .id(3)
            .name("Iza")
            .surname("Wolna")
            .age(21)
            .cash(BigDecimal.valueOf(8000))
            .build();

    Customer marta = Customer
            .builder()
            .id(4)
            .name("Marta")
            .surname("Bialek")
            .age(17)
            .cash(BigDecimal.valueOf(500))
            .build();
}
