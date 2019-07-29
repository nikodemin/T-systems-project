package com.t_systems.webstore.controller.REST;

import com.t_systems.webstore.model.dto.CategoryDto;
import com.t_systems.webstore.model.dto.IngredientDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.dto.TagDto;
import com.t_systems.webstore.model.entity.Category;
import com.t_systems.webstore.model.entity.Ingredient;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.Tag;
import com.t_systems.webstore.service.api.FilesService;
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
    private final FilesService filesService;


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
                .getProductsByCategory(category).stream().map(e -> productService.toProductDto(e))
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

            Category cat = new Category();
            cat.setName(category.getName());
            cat.setImage(path);
            productService.addCategory(cat);

            return new ResponseEntity<String>("Category added!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category already exists!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getCategories")
    public List<CategoryDto> getCategories() {
        return productService.getAllCategories().stream().map(e -> productService.toCategoryDto(e))
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
        return productService.getAllIngredients().stream().map(e -> productService.toIngredientDto(e))
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
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientDto.getName());
            ingredient.setPrice(ingredientDto.getPrice());
            productService.addIngredient(ingredient);
            return new ResponseEntity<>("Ingredient added!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Ingredient already exists!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getTags")
    public List<TagDto> getTags() {
        return productService.getAllTags().stream().map(t -> productService.toTagDto(t))
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
            Tag tag = new Tag();
            tag.setName(tagDto.getName());
            productService.addTag(tag);
            return new ResponseEntity<>("Tag added!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Tag already exists!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteTag/{category}/{product}/{tag}")
    public ResponseEntity<?> deleteTagFromProduct(@PathVariable("category") String category,
                                                  @PathVariable("product") String product,
                                                  @PathVariable("tag") String tag) {
        try {
            productService.removeTagFromProduct(getProduct(category, product), tag);
            return new ResponseEntity<>("Tag deleted!", HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>("Tag not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteIngredient/{category}/{product}/{ingredient}")
    public ResponseEntity<?> deleteIngredientFromProduct(@PathVariable("category") String category,
                                                         @PathVariable("product") String product,
                                                         @PathVariable("ingredient") String ingredient) {
        try {
            productService.removeIngredientFromProduct(getProduct(category, product), ingredient);
            return new ResponseEntity<>("Ingredient deleted!", HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>("Ingredient not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/addTagToProduct/{category}/{product}/{tag}")
    public ResponseEntity<?> addTagToProduct(@PathVariable("category") String category,
                                             @PathVariable("product") String product,
                                             @PathVariable("tag") String tag) {
        try {
            if (getProduct(category, product).getTags().contains(productService.getTag(tag)))
                return new ResponseEntity<>("Tag already exists!", HttpStatus.BAD_REQUEST);

            productService.addTagToProduct(getProduct(category, product), tag);
            return new ResponseEntity<>("Tag added!", HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>("Product or tag not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/addIngToProduct/{category}/{product}/{ingredient}")
    public ResponseEntity<?> addIngToProduct(@PathVariable("category") String category,
                                             @PathVariable("product") String product,
                                             @PathVariable("ingredient") String ingredient) {
        try {
            if (getProduct(category, product).getIngredients().contains(productService.getIngredient(ingredient)))
                return new ResponseEntity<>("Ingredient already exists!", HttpStatus.BAD_REQUEST);

            productService.addIngToProduct(getProduct(category, product), ingredient);
            return new ResponseEntity<>("Ingredient added!", HttpStatus.OK);
        } catch (NoResultException e) {
            return new ResponseEntity<>("Product or ingredient not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateProduct/{category}/{product}")
    public ResponseEntity<?> updateProduct(@PathVariable("category") String category,
                                           @PathVariable("product") String productName,
                                           @ModelAttribute("productDto") ProductDto productDto) {
        try {
            Product product = getProduct(category, productName);
            if (productDto.getName() != null && productDto.getName().trim().length() != 0)
                product.setName(productDto.getName());
            if (productDto.getPrice() != null)
                product.setPrice(productDto.getPrice());
            List<String> image = filesService.saveUploadedFiles(productDto.getFiles());
            if (image.size() > 0) {
                product.setImage(image.get(0));
            }
            if (productDto.getSpicy() != null)
                product.setSpicy(productDto.getSpicy());
            productService.updateProduct(product);
            return new ResponseEntity<>("Product updated!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private Product getProduct(String category, String product) throws NoResultException {
        List<Product> productList = productService.getProductsByCategory(category).stream()
                .filter(p -> p.getName().equals(product))
                .collect(Collectors.toList());
        if (productList.size() == 0)
            throw new NoResultException();
        return productList.get(0);
    }
}
