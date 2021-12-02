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
public class ShoppingServiceFindCheapestProductForCategoriesTest {
    private final ShoppingService shoppingService;

    @Test
    @DisplayName("Should return correct map with pairs categories and product with the lowest price")
    public void test1(){

        var categoryMinPriceExpected = Map.of(
                "Spozywcze", productId9,
                "Ksiazka", productId13,
                "Elektronika", productId12);

        assertThat(shoppingService.findCheapestProductForCategories())
                .containsExactlyInAnyOrderEntriesOf(categoryMinPriceExpected);
    }
}
