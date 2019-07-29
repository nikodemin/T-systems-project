package com.t_systems.webstore.config;

import com.t_systems.webstore.exception.UserExistsException;
import com.t_systems.webstore.model.entity.*;
import com.t_systems.webstore.model.enums.DeliveryMethod;
import com.t_systems.webstore.model.enums.OrderStatus;
import com.t_systems.webstore.model.enums.UserRole;
import com.t_systems.webstore.service.api.OrderService;
import com.t_systems.webstore.service.api.ProductService;
import com.t_systems.webstore.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbFiller implements ApplicationListener {
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            User user = new User();
            user.setDateOfBirth(new Date());
            Address address = new Address();
            address.setCountry("Russia");
            address.setCity("SPB");
            address.setStreet("Veteranov");
            user.setAddress(address);
            user.setEmail("niko.demin@gmail.com");
            user.setUsername("admin");
            user.setPassword("1234");
            user.setRole(UserRole.ADMIN);
            try {
                userService.addUser(user);
            } catch (UserExistsException e) {
                e.printStackTrace();
            }

            _Order order = new _Order();
            order.setDate(new Date());
            order.setByCard(true);
            order.setClient(user);
            order.setDeliveryMethod(DeliveryMethod.COURIER);
            order.setStatus(OrderStatus.PAID);
            List<Product> products = new ArrayList<>();

            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName("ingredient" + i);
                ingredient.setPrice(100);
                productService.addIngredient(ingredient);
                ingredients.add(ingredient);
            }

            Category pizza = new Category();
            pizza.setName("Pizza");
            List<Tag> tags = new ArrayList<>();
            Tag tag = new Tag();
            tag.setName("Spicy");
            productService.addTag(tag);
            tags.add(tag);
            tag = new Tag();
            tag.setName("With Mushrooms");
            productService.addTag(tag);
            tags.add(tag);
            productService.addCategory(pizza);
            for (int i = 0; i < 10; i++) {
                Product product = new Product();
                product.setName("pizza" + i);
                product.setImage("resources/img/pizza.jpg");
                product.setCategory(pizza);
                product.setIngredients(ingredients);
                product.setPrice(999);
                products.add(product);
                product.setTags(tags);
                productService.addProduct(product);
            }
            order.setItems(products);
            orderService.addOrder(order);

            order = new _Order();
            order.setDate(new Date());
            order.setByCard(true);
            order.setClient(user);
            order.setDeliveryMethod(DeliveryMethod.COURIER);
            order.setStatus(OrderStatus.PAID);
            List<Product> products2 = productService.getProductsByCategory("Pizza");
            products2.remove(9);
            products2.remove(8);
            order.setItems(products2);
            orderService.addOrder(order);

        }
    }
}
