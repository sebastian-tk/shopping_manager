package tkaczyk.sebastian.service.extensions;

import tkaczyk.sebastian.persistence.Product;
import java.math.BigDecimal;

public interface ProductsFactory {
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

    Product productId8 = Product
            .builder()
            .id(8)
            .name("Telewizor")
            .category("Elektronika")
            .price(BigDecimal.valueOf(6400))
            .build();

    Product productId9 = Product
            .builder()
            .id(9)
            .name("Jogurt")
            .category("Spozywcze")
            .price(BigDecimal.valueOf(2))
            .build();

    Product productId11 = Product
            .builder()
            .id(11)
            .name("Telefon")
            .category("Elektronika")
            .price(BigDecimal.valueOf(500))
            .build();
    Product productId12 = Product
            .builder()
            .id(12)
            .name("Słuchawki")
            .category("Elektronika")
            .price(BigDecimal.valueOf(200))
            .build();

    Product productId13 = Product
            .builder()
            .id(13)
            .name("Kot w butach")
            .category("Ksiazka")
            .price(BigDecimal.valueOf(10))
            .build();

}
