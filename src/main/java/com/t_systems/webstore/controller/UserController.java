package com.t_systems.webstore.controller;

import com.t_systems.webstore.model.dto.AddressDto;
import com.t_systems.webstore.model.dto.CardDto;
import com.t_systems.webstore.model.dto.OrderDto;
import com.t_systems.webstore.model.dto.UserDto;
import com.t_systems.webstore.service.api.MappingService;
import com.t_systems.webstore.service.api.OrderService;
import com.t_systems.webstore.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MappingService mappingService;
    private final OrderService orderService;

    @GetMapping("/settings")
    public String getSettingsPage(Model model, Principal principal) {
        UserDto userDto = mappingService.toUserDto(userService.findUser(principal.getName()));
        model.addAttribute("user", userDto);
        return "settings";
    }

    @PostMapping("/changeSettings")
    public String changeSettings(Model model, @Valid @ModelAttribute("user") UserDto userDto,
                                 BindingResult result, Principal principal,HttpServletRequest request)
            throws ParseException, ServletException {
        if (result.hasErrors())
            return "settings";

        request.logout();
        userService.changeUser(principal.getName(), userDto);
        model.addAttribute("text","Changes accepted!");
        return "text";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpSession session) {
        model.addAttribute("cart", session.getAttribute("cart"));
        return "cart";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/logout")
    public String getAfterLogoutPage(HttpServletRequest request) throws ServletException {
        request.logout();
        return "index";
    }

    @GetMapping("/payment")
    public String getPaymentPage(Model model, Principal principal){
        model.addAttribute("card",new CardDto());
        AddressDto addressDto = mappingService.toAddressDto(userService.
                findUser(principal.getName()).getAddress());
        model.addAttribute("address",addressDto);
        return "payment";
    }

    @GetMapping("/confirmation")
    public String getConfirmationPage(Model model){
        model.addAttribute("text","Order accepted! Wait for phone call!");
        return "text";
    }

    @GetMapping("/orders")
    public String getOrdersPage(Model model, Principal principal){
        List<OrderDto> orders = orderService.getOrdersByUser(principal.getName()).stream()
                .map(o->mappingService.toOrderDto(o)).collect(Collectors.toList());
        model.addAttribute("orders", orders);
        return "clientOrders";
    }

    @GetMapping("/customProduct")
    public String getCustomProductPage(){
        return "customProduct";
    }
}
