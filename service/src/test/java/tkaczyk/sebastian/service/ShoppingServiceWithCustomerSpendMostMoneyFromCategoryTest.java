package tkaczyk.sebastian.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tkaczyk.sebastian.exception.ShoppingServiceException;
import tkaczyk.sebastian.service.extensions.ShoppingServiceExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tkaczyk.sebastian.service.extensions.FactoryCustomer.*;

@ExtendWith(ShoppingServiceExtension.class)
@RequiredArgsConstructor
public class ShoppingServiceWithCustomerSpendMostMoneyFromCategoryTest {
    private final ShoppingService shoppingService;

    @Test
    @DisplayName("Should throw ShoppingServiceException when category is null")
    public void test1(){
        assertThatThrownBy(()-> shoppingService.withCustomerSpendMostMoneyFromCategory(null))
                .isInstanceOf(ShoppingServiceException.class)
                .hasMessage("Invalid category argument");
    }
    @Test
    @DisplayName("Should throw ShoppingServiceException when category is empty")
    public void test2(){
        assertThatThrownBy(()-> shoppingService.withCustomerSpendMostMoneyFromCategory(""))
                .isInstanceOf(ShoppingServiceException.class)
                .hasMessage("Invalid category argument");
    }
    @Test
    @DisplayName("Should throw ShoppingServiceException when category is not exist")
    public void test3(){
        assertThatThrownBy(()-> shoppingService.withCustomerSpendMostMoneyFromCategory("exampleCategory"))
                .isInstanceOf(ShoppingServiceException.class)
                .hasMessage("Invalid category. Category not exist");
    }

    @ParameterizedTest
    @DisplayName("Should return Customer which spent most money in category")
    @ValueSource(strings={
            "Spozywcze",
            "Ksiazka",
            "Elektronika"
    })
    public void test4(String category){
        var customersExpected = Map.of(
                "Spozywcze", List.of(tomasz),
                "Ksiazka", List.of(zygmunt),
                "Elektronika", List.of(iza)
        );
        assertThat(shoppingService.withCustomerSpendMostMoneyFromCategory(category))
                .containsExactlyInAnyOrderElementsOf(customersExpected.get(category));
    }



}
