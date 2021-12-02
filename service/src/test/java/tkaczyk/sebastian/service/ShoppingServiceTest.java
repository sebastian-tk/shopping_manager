package tkaczyk.sebastian.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tkaczyk.sebastian.exception.ShoppingServiceException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static tkaczyk.sebastian.service.extensions.CustomersFactory.*;
import static tkaczyk.sebastian.service.extensions.ProductsFactory.*;

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

        var customersExpected = List.of( zygmunt, tomasz, iza, marta);

        assertThat(shoppingServiceTest.getCustomerWithProduct())
                .containsOnlyKeys(customersExpected);
    }
    @Test
    @DisplayName("Should contains all Products for Customer")
    public void test6(){
        var productsWitQuantityExpected = Map.of(
                productId11,1L,
                productId12,1L,
                productId6,2L
        );

        assertThat(new ShoppingService(fileNameCorrect).getCustomerWithProduct().get(marta))
                .containsExactlyInAnyOrderEntriesOf(productsWitQuantityExpected);
    }
}
