package com.t_systems.webstore.controller.controller;

import com.t_systems.webstore.model.dto.UserDto;
import com.t_systems.webstore.service.MappingService;
import com.t_systems.webstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;

@Controller
@RequiredArgsConstructor
public class UserSettingsController {
    private final UserService userService;
    private final MappingService mappingService;

    @GetMapping("/settings")
    public String getSettingsPage(Model model, Principal principal) {
        UserDto userDto = mappingService.toUserDto(userService.findUser(principal.getName()));
        model.addAttribute("user", userDto);
        return "settings";
    }

    @PostMapping("/changeSettings")
    public String changeSettings(Model model, @Valid @ModelAttribute("user") UserDto userDto,
                                 BindingResult result, Principal principal, HttpServletRequest request)
            throws ParseException, ServletException {
        if (result.hasErrors()) {
            return "settings";
        }

        request.logout();
        userService.changeUser(principal.getName(), userDto);
        model.addAttribute("text","Changes accepted!");
        return "text";
    }
}
