package tkaczyk.sebastian.persistence;


import java.util.function.Function;
import java.util.stream.Stream;

public interface CustomerWithProductUtils {
    Function<CustomerWithProduct,Customer> toCustomer =customerWithProduct -> customerWithProduct.customer;
    Function<CustomerWithProduct, Stream<Product>> toProductsStream = customerWithProduct -> customerWithProduct.products.stream();

}
