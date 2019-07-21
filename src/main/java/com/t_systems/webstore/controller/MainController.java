package com.t_systems.webstore.controller;

import com.t_systems.webstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController
{
  private final ProductService productService;

  @RequestMapping("/")
  public String indexPage(Model model)
  {
    model.addAttribute("leaders",productService.getTopProducts());
    return "index";
  }
}
