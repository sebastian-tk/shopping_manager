package tkaczyk.sebastian.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tkaczyk.sebastian.exception.ShoppingServiceException;
import tkaczyk.sebastian.persistence.Customer;
import tkaczyk.sebastian.persistence.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ShoppingServiceTest {
    String fileNameCorrect = "src/test/resources/dataTest.json";

    @Test
    @DisplayName("Should throw ShoppingServiceException when fileName is null")
    public void test1(){
        assertThatThrownBy(()-> new ShoppingService(null))
                .isInstanceOf(ShoppingServiceException.class)
                .hasMessage("Invalid fileName argument");
    }

    @Test
    @DisplayName("Should throw ShoppingServiceException when fileName is empty")
    public void test2(){
        assertThatThrownBy(()-> new ShoppingService(""))
                .isInstanceOf(ShoppingServiceException.class)
                .hasMessage("Invalid fileName argument");
    }
    @Test
    @DisplayName("Should no throw when fileName is correct")
    public void test4(){
        String fileName = "src/test/resources/dataTest.json";

        assertThatCode(()-> new ShoppingService(fileName)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should contains all Customers from file")
    public void test5(){
        ShoppingService shoppingServiceTest = new ShoppingService(fileNameCorrect);

        var customersExpected = List.of(
                Customer
                        .builder()
                        .id(1)
                        .name("Zygmunt")
                        .surname("Nowak")
                        .age(65)
                        .cash(BigDecimal.valueOf(30000))
                        .build()
                ,
                Customer
                        .builder()
                        .id(2)
                        .name("Tomasz")
                        .surname("Jonak")
                        .age(25)
                        .cash(BigDecimal.valueOf(3500))
                        .build()
                ,
                Customer
                        .builder()
                        .id(3)
                        .name("Iza")
                        .surname("Wolna")
                        .age(21)
                        .cash(BigDecimal.valueOf(8000))
                        .build()
                ,
                Customer
                        .builder()
                        .id(4)
                        .name("Marta")
                        .surname("Białek")
                        .age(17)
                        .cash(BigDecimal.valueOf(900))
                        .build()
        );

        assertThat(shoppingServiceTest.getCustomerWithProduct())
                .containsOnlyKeys(customersExpected);
    }
    @Test
    @DisplayName("Should contains all Products for Customer")
    public void test6(){
        Customer customerTest = Customer
                .builder()
                .id(4)
                .name("Marta")
                .surname("Białek")
                .age(17)
                .cash(BigDecimal.valueOf(900))
                .build();
        var productsWitQuantityExpected = Map.of(
                Product
                        .builder()
                        .id(11)
                        .name("Telefon")
                        .category("Elektronika")
                        .price(BigDecimal.valueOf(500))
                        .build(), 1L,
                Product
                        .builder()
                        .id(6)
                        .name("Sok pomaranczowy")
                        .category("Spozywcze")
                        .price(BigDecimal.valueOf(5))
                        .build(), 2L
        );

        assertThat(new ShoppingService(fileNameCorrect).getCustomerWithProduct().get(customerTest))
                .containsExactlyInAnyOrderEntriesOf(productsWitQuantityExpected);
    }
}
