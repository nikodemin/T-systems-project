package com.t_systems.webstore.controller;

import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.service.api.MappingService;
import com.t_systems.webstore.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CatalogController {
    private final ProductService productService;
    private final MappingService mappingService;

    @GetMapping("/catalog/{category}")
    public String getCatalogPage(@PathVariable("category") String category, Model model){
        model.addAttribute("tags", productService.getTagsByCategory(category).stream()
        .map(t->mappingService.toTagDto(t)).collect(Collectors.toList()));
        return "catalog";
    }

    @GetMapping("/getProducts/{category}")
    @ResponseBody
    public List<ProductDto> getProductsByCategory(@PathVariable("category") String category){
        return productService.getProductsByCategory(category).stream()
                .map(p->mappingService.toProductDto(p)).collect(Collectors.toList());
    }
}
