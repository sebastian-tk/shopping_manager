package tkaczyk.sebastian.service;

import lombok.RequiredArgsConstructor;
import tkaczyk.sebastian.persistence.Customer;
import tkaczyk.sebastian.persistence.Product;

import java.util.Map;

@RequiredArgsConstructor
public class ShoppingService {
    private Map<Customer,Map<Product,Long>> customerWithProduct;

}
