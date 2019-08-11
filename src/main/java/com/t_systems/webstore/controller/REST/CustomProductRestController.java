package com.t_systems.webstore.controller.REST;

import com.t_systems.webstore.model.dto.IngredientDto;
import com.t_systems.webstore.model.dto.OrderDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.service.api.MappingService;
import com.t_systems.webstore.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customProduct")
public class CustomProductRestController {
    private final ProductService productService;
    private final MappingService mappingService;

    @GetMapping("/ingredients/{category}")
    public List<IngredientDto> getIngredients(@PathVariable("category") String category){
        return productService.getIngredientsByCategory(category).stream()
                .map(i->mappingService.toIngredientDto(i)).collect(Collectors.toList());
    }

    @GetMapping("/userProducts/{category}")
    public List<ProductDto> getUserProductsByCategory(@PathVariable("category") String category,
                                                      Principal principal){
        return productService.getProductsByCategoryAndUser(category, principal.getName()).stream()
                .map(p->mappingService.toProductDto(p)).collect(Collectors.toList());
    }

    @PostMapping("/userProducts")
    public void addUserProduct(@RequestBody ProductDto productDto, Principal principal){
        productService.addProduct(mappingService.toClientProduct(productDto, principal.getName()));
    }

    @PutMapping("/addToCart/{product}")
    public void addToCart(@PathVariable("product") String product,
                          HttpSession session){
        OrderDto order = (OrderDto)session.getAttribute("order");
        Product clientProduct = productService.getProduct(product);
        order.getItems().add(mappingService.toProductDto(clientProduct));
        session.setAttribute("order",order);
    }
}
