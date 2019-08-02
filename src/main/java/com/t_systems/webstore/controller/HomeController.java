package com.t_systems.webstore.controller;

import com.t_systems.webstore.service.api.MappingService;
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
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:server.properties")
public class HomeController {

    private final ProductService productService;
    private final MappingService mappingService;
    @Value("${server.uploadDir}")
    private String UPLOAD_DIR;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("leaders",
                productService.getTopProducts().stream()
                        .map(p->mappingService.toProductDto(p)).collect(Collectors.toList()));
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
