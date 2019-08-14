package com.t_systems.webstore.controller.exceptionHandelrs;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = {"com.t_systems.webstore.controller.controller"} )
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String defaultExceptionHandler(Model model, Exception e) throws Exception{
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        e.printStackTrace();
        model.addAttribute("text", "500 Internal server error");
        return "text";
    }
}
