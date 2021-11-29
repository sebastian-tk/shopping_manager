package tkaczyk.sebastian.service;

import lombok.Getter;
import tkaczyk.sebastian.exception.ShoppingServiceException;
import tkaczyk.sebastian.persistence.Customer;
import tkaczyk.sebastian.persistence.Product;
import tkaczyk.sebastian.persistence.converter.CustomerWithProductJsonConverter;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static tkaczyk.sebastian.persistence.CustomerWithProductUtils.toCustomer;
import static tkaczyk.sebastian.persistence.CustomerWithProductUtils.toProductsStream;

@Getter
public class ShoppingService {
    private Map<Customer,Map<Product,Long>> customerWithProduct;


    public ShoppingService(String fileName){
        if(fileName == null || fileName.isEmpty()){
            throw new ShoppingServiceException("Invalid fileName argument");
        }
        this.customerWithProduct = getCustomerWithProducts(fileName);
    }

    /**
     *
     * @param fileName String as file name
     * @return Map with Customers as keys and Map<Product,Long> as value.
     *         Type Long describes how many products  of type have customer
     */
    private Map<Customer,Map<Product,Long>> getCustomerWithProducts(String fileName){
        return  new CustomerWithProductJsonConverter(fileName)
                .fromJson()
                .orElseThrow(()-> new ShoppingServiceException("Cannot read data from json filename: " + fileName))
                .stream()
                .collect(groupingBy(
                        toCustomer,
                        collectingAndThen(flatMapping(
                                        toProductsStream,toList()),
                                        products -> products
                                                .stream()
                                                .collect(groupingBy(
                                                        identity(),
                                                        counting()))
                        )));
    }


}
