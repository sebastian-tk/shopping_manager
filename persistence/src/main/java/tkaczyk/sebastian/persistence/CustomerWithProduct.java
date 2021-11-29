package tkaczyk.sebastian.persistence;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CustomerWithProduct {
    Customer customer;
    List<Product> products;
}
