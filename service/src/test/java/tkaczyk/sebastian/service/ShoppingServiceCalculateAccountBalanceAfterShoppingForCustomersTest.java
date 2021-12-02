package tkaczyk.sebastian.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tkaczyk.sebastian.service.extensions.ShoppingServiceExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static tkaczyk.sebastian.service.extensions.CustomersFactory.*;

@ExtendWith(ShoppingServiceExtension.class)
@RequiredArgsConstructor
public class ShoppingServiceCalculateAccountBalanceAfterShoppingForCustomersTest{
    private final ShoppingService shoppingService;

    @Test
    @DisplayName("Should return map with pairs customers and BigDecimals as account balance")
    public void test1(){

        var customerAccountBalanceExpected = Map.of(
                marta, new BigDecimal(-210),
                iza, new BigDecimal(1516),
                tomasz, new BigDecimal("-524.95"),
                zygmunt,new BigDecimal("25396.03"));

        assertThat(shoppingService.calculateAccountBalanceAfterShoppingForCustomers())
                .containsExactlyInAnyOrderEntriesOf(customerAccountBalanceExpected);
    }

}
