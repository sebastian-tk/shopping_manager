package tkaczyk.sebastian.persistence;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Product {
    Integer id;
    String name;
    String category;
    BigDecimal price;
}
