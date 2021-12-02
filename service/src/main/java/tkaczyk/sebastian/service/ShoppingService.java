package tkaczyk.sebastian.service;

import lombok.Getter;
import tkaczyk.sebastian.exception.ShoppingServiceException;
import tkaczyk.sebastian.persistence.Customer;
import tkaczyk.sebastian.persistence.CustomerWithProduct;
import tkaczyk.sebastian.persistence.CustomerWithProductUtils;
import tkaczyk.sebastian.persistence.Product;
import tkaczyk.sebastian.persistence.converter.CustomerWithProductJsonConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static java.util.Collections.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.eclipse.collections.impl.collector.Collectors2.summarizingBigDecimal;
import static tkaczyk.sebastian.persistence.CustomerWithProductUtils.*;

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

    /**
     *
     * @return Optional with Customer , who spent most money for shopping
     */
    public Optional<Customer> withCustomerSpendMostMoney(){
        return customerWithProduct
                .entrySet()
                .stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> entry
                                .getValue()
                                .entrySet()
                                .stream()
                                .flatMap(productWithLong -> nCopies(
                                        productWithLong.getValue().intValue(),
                                        productWithLong.getKey()).stream())
                                .map(toPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }


    /**
     *
     * @param category String as category name
     * @return  List with Customers, which spent most money in category
     */
    public List<Customer> withCustomerSpendMostMoneyFromCategory(String category){
        if(category == null || category.isEmpty()){
            throw new ShoppingServiceException("Invalid category argument");
        }
        if(isCategoryNotExist(category)){
            throw new ShoppingServiceException("Invalid category. Category not exist");
        }
        return customerWithProduct
                .entrySet()
                .stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> entry
                                .getValue()
                                .entrySet()
                                .stream()
                                .flatMap(productWithLong -> nCopies(
                                        productWithLong.getValue().intValue(),
                                        productWithLong.getKey()).stream())
                                .collect(groupingBy(
                                        toCategory,
                                        mapping(toPrice,toList())))
                                .entrySet()
                                .stream()
                                .collect(toMap(
                                        Map.Entry::getKey,
                                        ent -> ent.getValue()
                                                .stream()
                                                .reduce(BigDecimal.ZERO,BigDecimal::add)
                                ))
                                ))
                .entrySet()
                .stream()
                .filter(customerMapEntry -> customerMapEntry.getValue().containsKey(category))
                .collect(toMap(
                        Map.Entry::getKey,
                        entry-> entry.getValue().get(category)
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .stream()
                .toList();
    }


    /**
     *
     * @return  Map with pairs, age as key and category as value, which describes the most often
     *          bought products from this category in customers in this age
     */
    public Map<Integer,String> findAgeWithCategory(){
        return customerWithProduct
                .entrySet()
                .stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry-> entry
                                        .getValue()
                                        .entrySet()
                                        .stream()
                                        .flatMap(productLongEntry -> nCopies(
                                                productLongEntry.getValue().intValue(),
                                                productLongEntry.getKey()).stream())
                                        .collect(groupingBy(toCategory))
                                        .entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByValue(Comparator.comparingInt(List::size)))
                                        .orElseThrow(()-> new ShoppingServiceException("Products status incorrect when searching for max value"))
                                .getValue()))
                .entrySet()
                .stream()
                .flatMap(customerProductEntry -> customerProductEntry
                                                .getValue()
                                                .stream()
                                                .map(product ->  CustomerWithProduct
                                                                .builder()
                                                                .customer(customerProductEntry.getKey())
                                                                .products(customerProductEntry.getValue())
                                                                .build()))
                .collect(toMap(
                        toAge,
                        toProducts,
                        (age1,age2)->age1.size() > age2.size() ? age1 : age2
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(toMap(
                        Map.Entry::getKey,
                        products-> products.getValue().stream().map(toCategory).toList().get(0),
                        (e1,e2)->e1,
                        LinkedHashMap::new
                ));
    }

    /**
     *
     * @return Map with category as keys and BigDecimal as average price of products from this category
     */
    public Map<String,BigDecimal> calculateAverageForAllCategory(){
        return customerWithProduct
                .values()
                .stream()
                .flatMap(productLongMap -> productLongMap
                                            .entrySet()
                                            .stream()
                                            .flatMap(productLongEntry -> nCopies(
                                                                            productLongEntry.getValue().intValue(),
                                                                            productLongEntry.getKey()).stream()))
                .collect(groupingBy(
                        toCategory,
                        collectingAndThen(
                                mapping(toPrice,toList()),
                                prices-> prices
                                        .stream()
                                        .collect(summarizingBigDecimal(price->price))
                                        .getAverage()
                                        .setScale(2, RoundingMode.FLOOR)
                        )
                ));
    }

    /**
     *
     * @return Map with pairs,key as String describes category and Product with maximum price form
     *          this category as value
     */
    public Map<String,Product> findMostExpensiveProductForCategories(){
        return   findCategoriesWithProductsAndPrice()
                .entrySet()
                .stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        customerProductEntry-> customerProductEntry
                                .getValue()
                                .entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .orElseThrow(()-> new ShoppingServiceException("Invalid state in categories"))
                                .getKey()
                ));
    }

    /**
     *
     * @return Map with pairs,key as String describes category and Product with the cheapest price form
     *          this category as value
     */
    public Map<String,Product> findCheapestProductForCategories(){
        return   findCategoriesWithProductsAndPrice()
                .entrySet()
                .stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        customerProductEntry-> customerProductEntry
                                .getValue()
                                .entrySet()
                                .stream()
                                .min(Map.Entry.comparingByValue())
                                .orElseThrow(()-> new ShoppingServiceException("Invalid state in categories"))
                                .getKey()
                ));
    }

    /**
     *
     * @return Map with Categories as key and map as values with products and their price
     */
    private Map<String,Map<Product,BigDecimal>> findCategoriesWithProductsAndPrice(){
        return customerWithProduct
                .values()
                .stream()
                .flatMap(productLongMap -> productLongMap
                        .entrySet()
                        .stream()
                        .flatMap(productLongEntry -> nCopies(
                                productLongEntry.getValue().intValue(),
                                productLongEntry.getKey()).stream()))
                .collect(groupingBy(
                        toCategory,
                        collectingAndThen(
                                mapping(identity(),toList()),
                                products-> products
                                        .stream()
                                        .collect(toMap(
                                                identity(),
                                                toPrice,
                                                (p1,p2)->p1
                                        )))));
    }
    /**
     *
     * @param category String as name category
     * @return  true, if category is not exist, else false
     */
    private boolean isCategoryNotExist(String category){
        return customerWithProduct
                .entrySet()
                .stream()
                .flatMap(customerMapEntry -> customerMapEntry
                        .getValue()
                        .keySet()
                        .stream()
                        .map(toCategory))
                .noneMatch(cat-> cat.equals(category));

    }
}
