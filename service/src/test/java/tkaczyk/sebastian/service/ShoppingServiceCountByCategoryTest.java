package tkaczyk.sebastian.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tkaczyk.sebastian.service.extensions.ShoppingServiceExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static tkaczyk.sebastian.service.extensions.CustomersFactory.*;

@ExtendWith(ShoppingServiceExtension.class)
@RequiredArgsConstructor
public class ShoppingServiceCountByCategoryTest {
    private final ShoppingService shoppingService;

    @Test
    @DisplayName("Should return map with pairs categories and List Customers who bought most often in this category")
    public void test1(){

        var categoryCustomersExpected = Map.of(
                "Spozywcze", List.of(tomasz),
                "Ksiazka", List.of(iza),
                "Elektronika", List.of(zygmunt,marta));

        assertThat(shoppingService.countByCategory())
                .containsExactlyInAnyOrderEntriesOf(categoryCustomersExpected);
    }
}
