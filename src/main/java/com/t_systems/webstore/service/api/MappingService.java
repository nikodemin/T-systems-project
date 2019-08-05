package com.t_systems.webstore.service.api;

import com.t_systems.webstore.model.dto.*;
import com.t_systems.webstore.model.entity.*;
import com.t_systems.webstore.model.enums.DeliveryMethod;
import com.t_systems.webstore.model.enums.OrderStatus;
import com.t_systems.webstore.model.enums.PaymentMethod;

public interface MappingService {
    ProductDto toProductDto(Product product);

    CategoryDto toCategoryDto(Category category);

    IngredientDto toIngredientDto(Ingredient ingredient);

    TagDto toTagDto(Tag tag);

    Product toProduct(Product product, ProductDto productDto) throws Exception;

    OrderDto toOrderDto(_Order order);

    Category toCategory(CategoryDto categoryDto, String path);

    Ingredient toIngredient(IngredientDto ingredientDto);

    Tag toTag(TagDto tagDto);

    UserDto toUserDto(User user);

    OrderStatus toOrderStatus(String status);

    DeliveryMethod toDeliveryMethod(String method);

    PaymentMethod toPaymentMethod(String type);

    _Order toOrder(OrderDto order) throws Exception;

    Address toAddress(AddressDto addressDto);

    Card toCard(CardDto cardDto);

    AddressDto toAddressDto(Address address);

    User toUser(UserDto form);
}
