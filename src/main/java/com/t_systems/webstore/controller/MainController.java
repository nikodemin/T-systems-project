package com.t_systems.webstore.controller;

import com.t_systems.webstore.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:server.properties")
public class MainController {

    private final ProductService productService;
    @Value("${server.uploadDir}")
    private String UPLOAD_DIR;

    @RequestMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("leaders", productService.getTopProducts());
        return "index";
    }

    @GetMapping("/uploads/{file}.{ext}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "file") String fileName,
                           @PathVariable(value = "ext") String ext) throws IOException {

        File serverFile = new File(UPLOAD_DIR + "/" + fileName + "." + ext);
        return Files.readAllBytes(serverFile.toPath());
    }
}
