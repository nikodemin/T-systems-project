package com.t_systems.webstore.service.impl;

import com.t_systems.webstore.model.dto.*;
import com.t_systems.webstore.model.entity.*;
import com.t_systems.webstore.model.enums.DeliveryMethod;
import com.t_systems.webstore.model.enums.OrderStatus;
import com.t_systems.webstore.model.enums.PaymentMethod;
import com.t_systems.webstore.service.api.FilesService;
import com.t_systems.webstore.service.api.MappingService;
import com.t_systems.webstore.service.api.ProductService;
import com.t_systems.webstore.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MappingServiceImpl implements MappingService {
    private final ModelMapper modelMapper;
    private final FilesService filesService;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public ProductDto toProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public CategoryDto toCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public IngredientDto toIngredientDto(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    @Override
    public TagDto toTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public Product toProduct(Product product, ProductDto productDto) throws Exception{
        if (product == null)
        {
            product = new Product();
            product.setName(String.valueOf((new Date()).getTime()));
        }
        if (productDto.getName() != null && productDto.getName().trim().length() != 0)
            product.setName(productDto.getName());
        if (productDto.getPrice() != null)
            product.setPrice(productDto.getPrice());
        List<String> image = filesService.saveUploadedFiles(productDto.getFiles());
        if (image.size() > 0) {
            product.setImage(image.get(0));
        }
        if (productDto.getSpicy() != null)
            product.setSpicy(productDto.getSpicy());
        return product;
    }

    @Override
    public OrderDto toOrderDto(_Order order) {
        OrderDto res = new OrderDto();
        res.setDate(order.getDate().toString());
        res.setDeliveryMethod(order.getDeliveryMethod().toString());
        List<ProductDto> items = order.getItems().stream()
                .map(p->toProductDto(p)).collect(Collectors.toList());
        res.setItems(items);
        res.setStatus(order.getStatus().toString());
        res.setUsername(order.getClient().getUsername());
        res.setPaymentMethod(order.getPaymentMethod().toString());
        res.setId(order.getId());
        res.setPrice(order.getTotal());
        return res;
    }

    @Override
    public Category toCategory(CategoryDto categoryDto, String path) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setImage(path);
        return category;
    }

    @Override
    public Ingredient toIngredient(IngredientDto ingredientDto) {
        return modelMapper.map(ingredientDto, Ingredient.class);
    }

    @Override
    public Tag toTag(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    @Override
    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public OrderStatus toOrderStatus(String status){
        OrderStatus res = null;
        switch (status)
        {
            case "Unpaid":
                res = OrderStatus.UNPAID;
                break;
            case "Paid":
                res = OrderStatus.PAID;
                break;
            case "Delivered":
                res = OrderStatus.DELIVERED;
                break;
        }
        return res;
    }

    @Override
    public DeliveryMethod toDeliveryMethod(String method){
        DeliveryMethod res = null;
        switch (method)
        {
            case "Courier":
                res = DeliveryMethod.COURIER;
                break;
            case "Pick up":
                res = DeliveryMethod.PICKUP;
                break;
        }
        return res;
    }

    @Override
    public PaymentMethod toPaymentMethod(String method){
        PaymentMethod res = null;
        switch (method)
        {
            case "Cash":
                res = PaymentMethod.CASH;
                break;
            case "Card":
                res = PaymentMethod.CARD;
                break;
        }
        return res;
    }

    @Override
    public _Order toOrder(OrderDto order) {
        _Order res = new _Order();
        res.setStatus(OrderStatus.UNPAID);
        res.setClient(userService.findUser(order.getUsername()));
        res.setDate(new Date());
        List<Product> products = order.getItems().stream().map(p->productService
                .getProduct(p.getName())).collect(Collectors.toList());
        res.setItems(products);
        return res;
    }

    @Override
    public Address toAddress(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }

    @Override
    public Card toCard(CardDto cardDto) {
        return modelMapper.map(cardDto, Card.class);
    }
}
