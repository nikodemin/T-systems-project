package com.t_systems.webstore.controller.REST;

import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.service.api.FilesService;
import com.t_systems.webstore.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final ProductService productService;
    private final FilesService filesService;


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == CategoryDto.class) {
            // Register to handle the conversion between the multipart object
            // and byte array.
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("/getProducts/{category}")
    public List<ProductDto> getProducts(@PathVariable("category") String category){
        System.out.println("NIGGA="+productService
                .getProductsByCategory(category));
        System.out.println("NIGGA2="+productService
                .getProductsByCategory(category).stream().map(e->productService.toProductDto(e))
                .collect(Collectors.toList()));
        return productService
                .getProductsByCategory(category).stream().map(e->productService.toProductDto(e))
                .collect(Collectors.toList());
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@ModelAttribute("categoryDto") CategoryDto category)
    {
        try{
            productService.getCategory(category.getName());
        }
        catch (NoResultException e) {

            String path;
            try{
                path = filesService.saveUploadedFiles(category.getFiles()).get(0);
            }
            catch (Exception e2) {
                e2.printStackTrace();
                return new ResponseEntity<>("Error: " + e2.getMessage(), HttpStatus.BAD_REQUEST);
            }

            Category cat = new Category();
            cat.setName(category.getName());
            cat.setImage(path);
            productService.addCategory(cat);

            return new ResponseEntity<String>("Category added!", HttpStatus.OK);
        }
            return new ResponseEntity<>("Category already exists!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getCategories")
    public List<CategoryDto> getCategories(){
        return productService.getAllCategories().stream().map(e->productService.toCategoryDto(e))
                .collect(Collectors.toList());
    }

    @PostMapping("/deleteCategory/{category}")
    public ResponseEntity<?> deleteCategory(@PathVariable("category") String category)
    {
        try{
            productService.getCategory(category);
        }
        catch (NoResultException e) {
            return new ResponseEntity<>("Category not exists!", HttpStatus.BAD_REQUEST);
        }
        productService.removeCategory(category);

        return new ResponseEntity<>("Category deleted!", HttpStatus.OK);
    }
}
