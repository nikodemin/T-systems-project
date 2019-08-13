package com.t_systems.webstore.controller.restController;

import com.t_systems.webstore.model.dto.*;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.service.FilesService;
import com.t_systems.webstore.service.MappingService;
import com.t_systems.webstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;
    private final MappingService mappingService;
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
    public List<ProductDto> getProductsByCategory(@PathVariable("category") String category){
        return productService.getProductDtosByCategory(category);
    }

    @PostMapping("/getProductsWithTags/{category}")
    public List<ProductDto> getProductsWithTags(@PathVariable("category") String category,
                                                @RequestBody List<TagDto> tags){
        return productService.getProductDtosWithTags(category,tags);
    }

    @GetMapping("customProduct/ingredients/{category}")
    public List<IngredientDto> getIngredients(@PathVariable("category") String category){
        return productService.getIngredientDtosByCategory(category);
    }

    @GetMapping("customProduct/userProducts/{category}")
    public List<ProductDto> getUserProductsByCategory(@PathVariable("category") String category,
                                                      Principal principal){
        return productService.getProductDtosByCategoryAndUser(category, principal.getName());
    }

    @PostMapping("customProduct/userProducts")
    public void addUserProduct(@RequestBody ProductDto productDto, Principal principal){
        productService.setPrice(productDto);
        productService.addProduct(mappingService.toClientProduct(productDto, principal.getName()));
    }

    @PutMapping("customProduct/addToCart/{product}")
    public void addToCart(@PathVariable("product") String product,
                          HttpSession session){
        OrderDto order = (OrderDto)session.getAttribute("order");
        Product clientProduct = productService.getProduct(product);
        order.getItems().add(mappingService.toProductDto(clientProduct));
        session.setAttribute("order",order);
    }

    //Admin page
    @GetMapping("/admin/getProducts/{category}")
    public List<ProductDto> getProducts(@PathVariable("category") String category) {
        return productService.getProductDtosByCategory(category);
    }

    @PostMapping("/admin/addCategory")
    public ResponseEntity<?> addCategory(@ModelAttribute("categoryDto") CategoryDto category) throws Exception{
            String path = filesService.saveUploadedFiles(category.getFiles()).get(0);
            productService.addCategory(mappingService.toCategory(category, path));
            return new ResponseEntity<String>("Category added!", HttpStatus.OK);
    }

    @GetMapping("/admin/getCategories")
    public List<CategoryDto> getCategories() {
        return productService.getAllCategoryDtos();
    }

    @DeleteMapping("/admin/deleteCategory/{category}")
    public ResponseEntity<?> deleteCategory(@PathVariable("category") String category) {
        productService.removeCategory(category);
        return new ResponseEntity<>("Category deleted!", HttpStatus.OK);
    }

    @GetMapping("/admin/getIngredients")
    public List<IngredientDto> getAllIngredients() {
        return productService.getAllIngredientDtos();
    }

    @DeleteMapping("/admin/deleteIngredient/{ingredient}")
    public ResponseEntity<?> deleteIngredient(@PathVariable("ingredient") String name) {
        productService.removeIngredient(name);
        return new ResponseEntity<>("Ingredient deleted!", HttpStatus.OK);
    }

    @PostMapping("/admin/addIngredient")
    public ResponseEntity<?> addIngredient(@RequestBody IngredientDto ingredientDto) {
        productService.addIngredient(mappingService.toIngredient(ingredientDto));
        return new ResponseEntity<>("Ingredient added!", HttpStatus.OK);
    }

    @GetMapping("/admin/getTags")
    public List<TagDto> getTags() {
        return productService.getAllTagDtos();
    }

    @DeleteMapping("/admin/deleteTag/{tag}")
    public ResponseEntity<?> deleteTag(@PathVariable("tag") String name) {
        productService.removeTag(name);
        return new ResponseEntity<>("Tag deleted!", HttpStatus.OK);
    }

    @PostMapping("/admin/addTag")
    public ResponseEntity<?> addTag(@RequestBody TagDto tagDto) {
        productService.addTag(mappingService.toTag(tagDto));
        return new ResponseEntity<>("Tag added!", HttpStatus.OK);
    }

    @DeleteMapping("/admin/deleteTag/{category}/{product}/{tag}")
    public ResponseEntity<?> deleteTagFromProduct(@PathVariable("category") String category,
                                                  @PathVariable("product") String product,
                                                  @PathVariable("tag") String tag) {
        productService.removeTagFromProduct(product, tag);
        return new ResponseEntity<>("Tag deleted!", HttpStatus.OK);
    }

    @GetMapping("/admin/getCatTags/{category}")
    public List<TagDto> getTagsByCategory(@PathVariable("category") String category){
        return productService.getTagDtosByCategory(category);
    }

    @GetMapping("/admin/getCatIngs/{category}")
    public List<IngredientDto> getIngsByCategory(@PathVariable("category") String category){
        return productService.getIngredientDtosByCategory(category);
    }

    @DeleteMapping("/admin/deleteIngredient/{category}/{product}/{ingredient}")
    public ResponseEntity<?> deleteIngredientFromProduct(@PathVariable("category") String category,
                                                         @PathVariable("product") String product,
                                                         @PathVariable("ingredient") String ingredient) {
        productService.removeIngredientFromProduct(product, ingredient);
        return new ResponseEntity<>("Ingredient deleted!", HttpStatus.OK);
    }

    @PutMapping("/admin/addTagToProduct/{category}/{product}/{tag}")
    public ResponseEntity<?> addTagToProduct(@PathVariable("category") String category,
                                             @PathVariable("product") String product,
                                             @PathVariable("tag") String tag) {
        if (productService.getProduct(product).getTags().contains(productService.getTag(tag)))
            return new ResponseEntity<>("Tag already exists!", HttpStatus.BAD_REQUEST);

        productService.addTagToProduct(product, tag);
        return new ResponseEntity<>("Tag added!", HttpStatus.OK);
    }

    @PutMapping("/admin/addIngToProduct/{category}/{product}/{ingredient}")
    public ResponseEntity<?> addIngToProduct(@PathVariable("category") String category,
                                             @PathVariable("product") String product,
                                             @PathVariable("ingredient") String ingredient) {
        if (productService.getProduct(product).getIngredients().contains(productService.getIngredient(ingredient)))
            return new ResponseEntity<>("Ingredient already exists!", HttpStatus.BAD_REQUEST);

        productService.addIngToProduct(product, ingredient);
        return new ResponseEntity<>("Ingredient added!", HttpStatus.OK);
    }

    @PostMapping("/admin/updateProduct/{category}/{product}")
    public ResponseEntity<?> updateProduct(@PathVariable("category") String category,
                                           @PathVariable("product") String productName,
                                           @ModelAttribute("productDto") ProductDto productDto) throws Exception {
        Product product = productService.getProduct(productName);
        mappingService.toProduct(product,productDto);
        productService.updateProduct(product);
        return new ResponseEntity<>("Product updated!", HttpStatus.OK);
    }

    @PostMapping("/admin/addProdcut/{category}")
    public ResponseEntity<?> addProduct(@PathVariable("category") String category,
                                        @ModelAttribute("productDto") ProductDto productDto) throws Exception{
        Product product = mappingService.toProduct(null,productDto);
        product.setCategory(productService.getCategory(category));
        productService.addProduct(product);
        return new ResponseEntity<>("Product added!", HttpStatus.OK);
    }

    @DeleteMapping ("/admin/deleteProduct/{category}/{product}")
    public ResponseEntity<?> deleteProduct(@PathVariable("category") String category,
                                           @PathVariable("product") String productName) {
        productService.detachOrRemoveProduct(productName);
        return new ResponseEntity<>("Product removed!", HttpStatus.OK);
    }

    @GetMapping("/admin/getTopProducts")
    public List<ProductDto> getTopProducts(){
        return productService.getTopProductsDtoForAdmin();
    }
}
