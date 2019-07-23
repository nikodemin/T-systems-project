package com.t_systems.webstore.controller;

import com.t_systems.webstore.model.dto.RegistrationForm;
import com.t_systems.webstore.service.UserService;
import com.t_systems.webstore.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController
{
    private final RegistrationValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder)
    {
        Object target = binder.getTarget();
        if (target==null)
            return;

        if (target.getClass() == RegistrationForm.class)
            binder.setValidator(validator);
    }

    @GetMapping
    public String register(Model model)
    {
        model.addAttribute("form",new RegistrationForm());
        return "register";
    }

    @PostMapping
    public String processRegistration(Model model,
                                      @Validated @ModelAttribute("form") RegistrationForm form,
                                      BindingResult result)
    {
        if (result.hasErrors())
            return "register";

        if(!userService.addUser(form.toUser(passwordEncoder)))
            return "register";

        return "redirect:/login";
    }
}
