package com.t_systems.webstore.controller;

import com.t_systems.webstore.exception.UserExistsException;
import com.t_systems.webstore.model.dto.UserDto;
import com.t_systems.webstore.model.entity.User;
import com.t_systems.webstore.service.api.UserService;
import com.t_systems.webstore.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final RegistrationValidator validator;
    private final UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target == null)
            return;

        if (target.getClass() == UserDto.class)
            binder.setValidator(validator);
    }

    @GetMapping
    public String getRegisterPage(Model model) {
        model.addAttribute("form", new UserDto());
        return "register";
    }

    @PostMapping
    public String processRegistration(Model model,
                                      @Validated @ModelAttribute("form") UserDto form,
                                      BindingResult result) {
        if (result.hasErrors())
            return "register";

        User user = userService.convertToUser(form);

        try {
            userService.addUser(user);
        } catch (UserExistsException e) {
            e.printStackTrace();
            return "register";
        }

        return "redirect:/login";
    }
}
