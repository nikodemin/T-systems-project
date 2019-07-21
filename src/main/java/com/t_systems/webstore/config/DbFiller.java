package com.t_systems.webstore.config;

import com.t_systems.webstore.entity.*;
import com.t_systems.webstore.model.Category;
import com.t_systems.webstore.model.DeliveryMethod;
import com.t_systems.webstore.model.OrderStatus;
import com.t_systems.webstore.service.OrderService;
import com.t_systems.webstore.service.ProductService;
import com.t_systems.webstore.service.UserService;
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
public class DbFiller implements ApplicationListener
{
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    @Override
    public void onApplicationEvent(ApplicationEvent event)
    {
        if (event instanceof ContextRefreshedEvent)
        {
            User user = new User();
            user.setDateOfBirth(new Date());
            Address address = new Address();
            address.setCountry("Russia");
            address.setCity("SPB");
            address.setStreet("Veteranov");
            user.setAddress(address);
            user.setEmail("bob@gmail.com");
            user.setUsername("bob");
            userService.addUser(user);

            _Order order = new _Order();
            order.setDate(new Date());
            order.setByCard(true);
            order.setClient(user);
            order.setDeliveryMethod(DeliveryMethod.COURIER);
            order.setStatus(OrderStatus.PAID);
            List<Product> products = new ArrayList<>();

            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0; i < 6; i++)
            {
                Ingredient ingredient = new Ingredient();
                ingredient.setName("ingredient"+i);
                ingredient.setPrice(100);
                productService.addIngredient(ingredient);
                ingredients.add(ingredient);
            }

            for (int i = 0; i < 10; i++)
            {
                Product product = new Product();
                product.setName("pizza"+i);
                product.setImage("resources/img/pizza.jpg");
                product.setCategory(Category.PIZZA);
                product.setIngredients(ingredients);
                product.setPrice(999);
                products.add(product);
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
            List<Product> products2 = productService.getProductsByCategory(Category.PIZZA);
            products2.remove(9);
            products2.remove(8);
            order.setItems(products2);
            orderService.addOrder(order);

        }
    }
}
