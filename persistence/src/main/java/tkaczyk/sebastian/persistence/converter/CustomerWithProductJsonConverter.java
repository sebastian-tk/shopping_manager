package tkaczyk.sebastian.persistence.converter;

import tkaczyk.sebastian.persistence.CustomerWithProduct;

import java.util.List;

public class CustomerWithProductJsonConverter extends JsonConverter<List<CustomerWithProduct>>{
    public CustomerWithProductJsonConverter(String fileName) {
        super(fileName);
    }
}
