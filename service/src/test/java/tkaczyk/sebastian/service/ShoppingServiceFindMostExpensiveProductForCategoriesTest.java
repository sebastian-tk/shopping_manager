package tkaczyk.sebastian.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tkaczyk.sebastian.service.extensions.ShoppingServiceExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static tkaczyk.sebastian.service.extensions.ProductsFactory.*;

@ExtendWith(ShoppingServiceExtension.class)
@RequiredArgsConstructor
public class ShoppingServiceFindMostExpensiveProductForCategoriesTest {
    private final ShoppingService shoppingService;

    @Test
    @DisplayName("Should return correct map with pairs categories and product with max price")
    public void test1(){

        var categoryMaxPriceExpected = Map.of(
                "Spozywcze", productId6,
                "Ksiazka", productId5,
                "Elektronika", productId8);

        assertThat(shoppingService.findMostExpensiveProductForCategories())
                .containsExactlyInAnyOrderEntriesOf(categoryMaxPriceExpected);
    }
}
