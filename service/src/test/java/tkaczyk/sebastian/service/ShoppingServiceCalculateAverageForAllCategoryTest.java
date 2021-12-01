package tkaczyk.sebastian.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tkaczyk.sebastian.service.extensions.ShoppingServiceExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ShoppingServiceExtension.class)
@RequiredArgsConstructor
public class ShoppingServiceCalculateAverageForAllCategoryTest {
    private final ShoppingService shoppingService;

    @Test
    @DisplayName("Should return  map all category as keys")
    public void test1(){

        var categoryExpected = List.of(
                "Spozywcze",
                "Ksiazka",
                "Elektronika");

        assertThat(shoppingService.calculateAverageForAllCategory().keySet())
                .containsExactlyInAnyOrderElementsOf(categoryExpected);
    }

    @ParameterizedTest
    @DisplayName("Should return map with correct average")
    @ValueSource(strings = {
            "Spozywcze",
            "Ksiazka",
            "Elektronika"
    })
    public void test2(String category){
        var categoryAverageExpected = Map.of(
                "Spozywcze", new BigDecimal("3.66"),
                "Ksiazka", new BigDecimal("37"),
                "Elektronika", new BigDecimal("2249.66"));

        assertThat(shoppingService.calculateAverageForAllCategory().get(category))
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(categoryAverageExpected.get(category));

    }
}
