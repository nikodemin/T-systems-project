package com.t_systems.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @GetMapping("/settings")
    public String getSettingsPage() {
        return "settings";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpSession session) {
        model.addAttribute("cart", session.getAttribute("cart"));
        return "cart";
    }

    @GetMapping("/logout")
    public String getAfterLogoutPage(HttpServletRequest request) throws ServletException {
        request.logout();
        return "index";
    }
}
