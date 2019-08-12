package com.t_systems.webstore.controller;

import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.IngredientDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.dto.TagDto;
import com.t_systems.webstore.model.enums.OrderStatus;
import com.t_systems.webstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;

    @GetMapping
    public String getAdminPage(Model model) {

        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("categoryDto", new CategoryDto());
        model.addAttribute("ingredientDto", new IngredientDto());
        model.addAttribute("tagDto", new TagDto());
        model.addAttribute("productDto", new ProductDto());
        return "admin";
    }

    @GetMapping("/stats")
    public String getStatsAdminPage(Model model){
        List<String> statusList = Stream.of(OrderStatus.values())
                .map(OrderStatus::toString).collect(Collectors.toList());
        model.addAttribute("statusList",statusList);
        return "statsAdmin";
    }
}
