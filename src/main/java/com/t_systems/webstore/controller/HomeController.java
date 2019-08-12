package com.t_systems.webstore.controller;

import com.t_systems.webstore.service.MappingService;
import com.t_systems.webstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final MappingService mappingService;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("leaders",
                productService.getTopProducts().stream()
                        .map(p->mappingService.toProductDto(p)).collect(Collectors.toList()));
        model.addAttribute("categories",productService.getAllCategories().stream()
                .map(c->mappingService.toCategoryDto(c)).collect(Collectors.toList()));
        return "index";
    }
}
