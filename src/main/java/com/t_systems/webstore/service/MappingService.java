package com.t_systems.webstore.service;

import com.t_systems.webstore.model.dto.*;
import com.t_systems.webstore.model.entity.*;
import com.t_systems.webstore.model.enums.OrderStatus;
import com.t_systems.webstore.model.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Lazy
@RequiredArgsConstructor
public class MappingService {
    private final ModelMapper modelMapper;
    private final FilesService filesService;
    private final UserService userService;
    private final ProductService productService;

    @PostConstruct
    public void init() {
        productService.setMappingService(this);
    }

    public ProductDto toProductDto(AbstractProduct product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        if (product instanceof CustomProduct)
            productDto.setIsCustom(true);
        return productDto;
    }

    public CategoryDto toCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public IngredientDto toIngredientDto(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    public TagDto toTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }

    public CatalogProduct toProduct(CatalogProduct product, ProductDto productDto) throws Exception{
        if (product == null)
        {
            product = new CatalogProduct();
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

    public Category toCategory(CategoryDto categoryDto, String path) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setImage(path);
        return category;
    }

    public Ingredient toIngredient(IngredientDto ingredientDto) {
        Ingredient res = modelMapper.map(ingredientDto, Ingredient.class);
        res.setCategories(ingredientDto.getCategories().stream()
            .map(productService::getCategory).collect(Collectors.toList()));
        return res;
    }

    public Tag toTag(TagDto tagDto) {
        List<Category> categories = tagDto.getCategories().stream()
                .map(productService::getCategory).collect(Collectors.toList());
        Tag tag = new Tag();
        tag.setCategories(categories);
        tag.setName(tagDto.getName());
        return tag;
    }

    public UserDto toUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setCountry(user.getAddress().getCountry());
        userDto.setCity(user.getAddress().getCity());
        userDto.setStreet(user.getAddress().getStreet());
        userDto.setHouse(user.getAddress().getHouse());
        userDto.setFlat(user.getAddress().getFlat());
        userDto.setPassword(null);
        return userDto;
    }

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

    public _Order toOrder(OrderDto order, String username) {
        _Order res = new _Order();
        res.setStatus(OrderStatus.UNPAID);
        res.setClient(userService.findUser(order.getUsername()));
        res.setDate(new Date());
        List<AbstractProduct> products = order.getItems().stream()
                .map(p->{
                    String user = p.getIsCustom()? username : null;
                    return (AbstractProduct) productService.getProduct(p.getName(),user);
                })
                .collect(Collectors.toList());
        res.setItems(products);
        return res;
    }

    public Address toAddress(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }

    public Card toCard(CardDto cardDto) {
        return modelMapper.map(cardDto, Card.class);
    }

    public AddressDto toAddressDto(Address address) {
        return modelMapper.map(address,AddressDto.class);
    }

    public User toUser(UserDto userDto) {
        Address address = modelMapper.map(userDto, Address.class);
        User user = modelMapper.map(userDto, User.class);
        user.setRole(UserRole.USER);
        user.setAddress(address);
        return user;
    }

    public CustomProduct toClientProduct(ProductDto productDto, String username) {
        CustomProduct product = modelMapper.map(productDto, CustomProduct.class);
        product.setAuthor(userService.findUser(username));
        product.setCategory(productService.getCategory(productDto.getCategory().getName()));
        product.setIngredients(productDto.getIngredients().stream()
        .map(i->productService.getIngredient(i.getName())).collect(Collectors.toList()));
        return product;
    }
}
