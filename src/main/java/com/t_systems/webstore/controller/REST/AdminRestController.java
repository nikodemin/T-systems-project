package com.t_systems.webstore.controller.REST;

import com.t_systems.webstore.model.dto.*;
import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import com.t_systems.webstore.service.api.FilesService;
import com.t_systems.webstore.service.api.MappingService;
import com.t_systems.webstore.service.api.OrderService;
import com.t_systems.webstore.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final ProductService productService;
    private final OrderService orderService;
    private final FilesService filesService;
    private final MappingService mappingService;


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == CategoryDto.class)
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        if (target.getClass() == ProductDto.class)
            dataBinder.setDisallowedFields("category", "tags", "ingredients");
    }

    @GetMapping("/getProducts/{category}")
    public List<ProductDto> getProducts(@PathVariable("category") String category) {
        System.out.println("CAT=" + productService
                .getProductsByCategory(category));
        return productService
                .getProductsByCategory(category).stream().map(e -> mappingService.toProductDto(e))
                .collect(Collectors.toList());
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@ModelAttribute("categoryDto") CategoryDto category) {
        try {
            productService.getCategory(category.getName());
        } catch (NoResultException e) {

            String path;
            try {
                path = filesService.saveUploadedFiles(category.getFiles()).get(0);
            } catch (Exception e2) {
                e2.printStackTrace();
                return new ResponseEntity<>("Error: " + e2.getMessage(), HttpStatus.BAD_REQUEST);
            }
            productService.addCategory(mappingService.toCategory(category, path));

            return new ResponseEntity<String>("Category added!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category already exists!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getCategories")
    public List<CategoryDto> getCategories() {
        return productService.getAllCategories().stream().map(e -> mappingService.toCategoryDto(e))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteCategory/{category}")
    public ResponseEntity<?> deleteCategory(@PathVariable("category") String category) {
        try {
            productService.getCategory(category);
        } catch (NoResultException e) {
            return new ResponseEntity<>("Category not exists!", HttpStatus.BAD_REQUEST);
        }
        productService.removeCategory(category);

        return new ResponseEntity<>("Category deleted!", HttpStatus.OK);
    }

    @GetMapping("/getIngredients")
    public List<IngredientDto> getAllIngredients() {
        return productService.getAllIngredients().stream().map(e -> mappingService.toIngredientDto(e))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteIngredient/{ingredient}")
    public ResponseEntity<?> deleteIngredient(@PathVariable("ingredient") String name) {
        try {
            productService.getIngredient(name);
        } catch (NoResultException e) {
            return new ResponseEntity<>("Ingredient not exists!", HttpStatus.BAD_REQUEST);
        }
        productService.removeIngredient(name);

        return new ResponseEntity<>("Ingredient deleted!", HttpStatus.OK);
    }

    @PostMapping("/addIngredient")
    public ResponseEntity<?> addIngredient(@ModelAttribute("ingredientDto") IngredientDto ingredientDto) {
        try {
            productService.getIngredient(ingredientDto.getName());
        } catch (NoResultException e) {
            productService.addIngredient(mappingService.toIngredient(ingredientDto));
            return new ResponseEntity<>("Ingredient added!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Ingredient already exists!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getTags")
    public List<TagDto> getTags() {
        return productService.getAllTags().stream().map(t -> mappingService.toTagDto(t))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteTag/{tag}")
    public ResponseEntity<?> deleteTag(@PathVariable("tag") String name) {
        try {
            productService.getTag(name);
        } catch (NoResultException e) {
            return new ResponseEntity<>("Tag not exists!", HttpStatus.BAD_REQUEST);
        }
        productService.removeTag(name);

        return new ResponseEntity<>("Tag deleted!", HttpStatus.OK);
    }

    @PostMapping("/addTag")
    public ResponseEntity<?> addTag(@ModelAttribute("tagDto") TagDto tagDto) {
        try {
            productService.getTag(tagDto.getName());
        } catch (NoResultException e) {
            productService.addTag(mappingService.toTag(tagDto));
            return new ResponseEntity<>("Tag added!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Tag already exists!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteTag/{category}/{product}/{tag}")
    public ResponseEntity<?> deleteTagFromProduct(@PathVariable("category") String category,
                                                  @PathVariable("product") String product,
                                                  @PathVariable("tag") String tag) {
        try {
            productService.removeTagFromProduct(product, tag);
            return new ResponseEntity<>("Tag deleted!", HttpStatus.OK);
        } catch (NoResultException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Tag not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteIngredient/{category}/{product}/{ingredient}")
    public ResponseEntity<?> deleteIngredientFromProduct(@PathVariable("category") String category,
                                                         @PathVariable("product") String product,
                                                         @PathVariable("ingredient") String ingredient) {
        try {
            productService.removeIngredientFromProduct(product, ingredient);
            return new ResponseEntity<>("Ingredient deleted!", HttpStatus.OK);
        } catch (NoResultException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ingredient not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/addTagToProduct/{category}/{product}/{tag}")
    public ResponseEntity<?> addTagToProduct(@PathVariable("category") String category,
                                             @PathVariable("product") String product,
                                             @PathVariable("tag") String tag) {
        try {
            if (productService.getProduct(product).getTags().contains(productService.getTag(tag)))
                return new ResponseEntity<>("Tag already exists!", HttpStatus.BAD_REQUEST);

            productService.addTagToProduct(product, tag);
            return new ResponseEntity<>("Tag added!", HttpStatus.OK);
        } catch (NoResultException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Product or tag not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/addIngToProduct/{category}/{product}/{ingredient}")
    public ResponseEntity<?> addIngToProduct(@PathVariable("category") String category,
                                             @PathVariable("product") String product,
                                             @PathVariable("ingredient") String ingredient) {
        try {
            if (productService.getProduct(product).getIngredients().contains(productService.getIngredient(ingredient)))
                return new ResponseEntity<>("Ingredient already exists!", HttpStatus.BAD_REQUEST);

            productService.addIngToProduct(product, ingredient);
            return new ResponseEntity<>("Ingredient added!", HttpStatus.OK);
        } catch (NoResultException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Product or ingredient not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateProduct/{category}/{product}")
    public ResponseEntity<?> updateProduct(@PathVariable("category") String category,
                                           @PathVariable("product") String productName,
                                           @ModelAttribute("productDto") ProductDto productDto) {
        try {
            Product product = productService.getProduct(productName);
            mappingService.toProduct(product,productDto);
            productService.updateProduct(product);
            return new ResponseEntity<>("Product updated!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addProdcut/{category}")
    public ResponseEntity<?> addProduct(@PathVariable("category") String category,
                                           @ModelAttribute("productDto") ProductDto productDto) {
        try {
            Product product = mappingService.toProduct(null,productDto);
            product.setCategory(productService.getCategory(category));
            productService.addProduct(product);
            return new ResponseEntity<>("Product added!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping ("/deleteProduct/{category}/{product}")
    public ResponseEntity<?> deleteProduct(@PathVariable("category") String category,
                                        @PathVariable("product") String productName) {
        try {
            productService.removeProduct(productName);
            return new ResponseEntity<>("Product removed!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTopProducts")
    public List<ProductDto> getTopProducts(){
        return productService.getTopProducts().stream().map(p->mappingService.toProductDto(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/getOrders")
    public List<OrderDto> getOrders(){
        return orderService.getAllOrders().stream().map(o->mappingService.toOrderDto(o))
                .collect(Collectors.toList());
    }
}
