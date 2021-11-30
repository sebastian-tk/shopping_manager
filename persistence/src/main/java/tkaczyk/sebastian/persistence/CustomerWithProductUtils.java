package tkaczyk.sebastian.persistence;


import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.Stream;

public interface CustomerWithProductUtils {
    Function<CustomerWithProduct,Customer> toCustomer =customerWithProduct -> customerWithProduct.customer;
    Function<CustomerWithProduct, Stream<Product>> toProductsStream = customerWithProduct -> customerWithProduct.products.stream();
    Function<Product, BigDecimal> toPrice = product -> product.price;
}
