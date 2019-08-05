package com.t_systems.webstore.model.entity;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "tag")
public class Tag extends AbstractEntity{
    private String name;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categories;
}
