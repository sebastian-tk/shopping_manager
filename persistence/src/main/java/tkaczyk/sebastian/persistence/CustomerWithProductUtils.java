package tkaczyk.sebastian.persistence;


import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface CustomerWithProductUtils {
    Function<Product, BigDecimal> toPrice = product -> product.price;
    Function<Product, String> toCategory = product -> product.category;
    Function<Customer,BigDecimal> toCash = customer -> customer.cash;

    Function<CustomerWithProduct,Customer> toCustomer =customerWithProduct -> customerWithProduct.customer;
    Function<CustomerWithProduct, Stream<Product>> toProductsStream = customerWithProduct -> customerWithProduct.products.stream();
    Function<CustomerWithProduct,Integer> toAge =customerWithProduct -> customerWithProduct.customer.age;
    Function<CustomerWithProduct, List<Product>> toProducts = customerWithProduct -> customerWithProduct.products;
}
