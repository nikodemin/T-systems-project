package com.t_systems.webstore.model;

import com.t_systems.webstore.model.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<Product> products;

    //todo
    /*public List<Pair<Product,Integer>> getUniqueProducts() {

        List<Pair<Product,Integer>> res = Arrays.stream(products.toArray(Product[]::new)).distinct()
                .map(t->Pair.of(t,0)).collect(Collectors.toList());

        return res;
    }*/
}
