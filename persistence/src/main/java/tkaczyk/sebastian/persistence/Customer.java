package tkaczyk.sebastian.persistence;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Customer {
    Integer id;
    String name;
    String surname;
    Integer age;
    BigDecimal cash;
}
