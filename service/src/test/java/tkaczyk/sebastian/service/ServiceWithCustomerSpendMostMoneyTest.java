package tkaczyk.sebastian.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tkaczyk.sebastian.persistence.Customer;
import tkaczyk.sebastian.service.extensions.ShoppingServiceExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ShoppingServiceExtension.class)
@RequiredArgsConstructor
public class ServiceWithCustomerSpendMostMoneyTest {
    private  final ShoppingService shoppingService;

    @Test
    @DisplayName("should return customer who spent most money")
    public void test1(){
        Customer customerExpected = Customer
                .builder()
                .id(3)
                .name("Iza")
                .surname("Wolna")
                .age(21)
                .cash(BigDecimal.valueOf(8000))
                .build();

        assertThat(shoppingService.withCustomerSpendMostMoney().orElse(null))
                .isEqualTo(customerExpected);
    }
}
