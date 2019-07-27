package com.t_systems.webstore.controller;

import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;

    @GetMapping
    public String getAdminPage(Model model) {

        model.addAttribute("categories",productService.getAllCategories());
        model.addAttribute("categoryDto", new CategoryDto());
        return "admin";
    }
}
