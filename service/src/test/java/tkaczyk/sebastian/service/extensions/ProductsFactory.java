package tkaczyk.sebastian.service.extensions;

import tkaczyk.sebastian.persistence.Product;

import java.math.BigDecimal;

public interface ProductsFactory {
    Product productId8 = Product
            .builder()
            .id(8)
            .name("Telewizor")
            .category("Elektronika")
            .price(BigDecimal.valueOf(6400))
            .build();

    Product productId5 = Product
            .builder()
            .id(5)
            .name("Ogniem i mieczem")
            .category("Ksiazka")
            .price(BigDecimal.valueOf(78))
            .build();

    Product productId6 = Product
            .builder()
            .id(6)
            .name("Sok pomaranczowy")
            .category("Spozywcze")
            .price(BigDecimal.valueOf(5))
            .build();
}
