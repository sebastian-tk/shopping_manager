package tkaczyk.sebastian.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tkaczyk.sebastian.service.extensions.ShoppingServiceExtension;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ShoppingServiceExtension.class)
@RequiredArgsConstructor
public class ShoppingServiceFindAgeWithCategoryTest {
    private final ShoppingService shoppingService;

    @Test
    @DisplayName("Should return correct map with pairs age and category")
    public void test1(){

        Map<Integer,String> ageCategoryExpected = new LinkedHashMap<>();
        ageCategoryExpected.put(17, "Elektronika");
        ageCategoryExpected.put(21,"Ksiazka");
        ageCategoryExpected.put(25, "Spozywcze");
        ageCategoryExpected.put(65, "Spozywcze");

        assertThat(shoppingService.findAgeWithCategory())
                .containsExactlyEntriesOf(ageCategoryExpected);
    }
}
