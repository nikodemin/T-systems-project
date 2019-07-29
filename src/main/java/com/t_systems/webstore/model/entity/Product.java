package com.t_systems.webstore.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Column(unique = true, nullable = false)
    private String name;
    private Integer price;
    private Integer spicy;
    private String image;
    @ManyToOne
    private Category category;
    @ManyToMany
    @JoinTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
    @ManyToMany
    @JoinTable(name = "product_ingredient", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public List<Ingredient> getSubListIngredients() {
        return ingredients.subList(0, ingredients.size() - 2);
    }

    public Ingredient getLastIngredient() {
        return ingredients.get(ingredients.size() - 1);
    }
}
