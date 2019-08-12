package com.t_systems.webstore.controller.REST;

import com.t_systems.webstore.model.dto.*;
import com.t_systems.webstore.model.entity.Product;
import com.t_systems.webstore.model.entity.User;
import com.t_systems.webstore.model.entity._Order;
import com.t_systems.webstore.service.FilesService;
import com.t_systems.webstore.service.MappingService;
import com.t_systems.webstore.service.OrderService;
import com.t_systems.webstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.persistence.NoResultException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> addIngredient(@RequestBody IngredientDto ingredientDto) {
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
    public ResponseEntity<?> addTag(@RequestBody TagDto tagDto) {
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

    @GetMapping("/getCatTags/{category}")
    public List<TagDto> getTagsByCategory(@PathVariable("category") String category){
        return productService.getTagsByCategory(category).stream()
                .map(t->mappingService.toTagDto(t)).collect(Collectors.toList());
    }

    @GetMapping("/getCatIngs/{category}")
    public List<IngredientDto> getIngsByCategory(@PathVariable("category") String category){
        return productService.getIngredientsByCategory(category).stream()
                .map(i->mappingService.toIngredientDto(i)).collect(Collectors.toList());
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
        return orderService.getRecentOrders().stream().map(o->mappingService.toOrderDto(o))
                .collect(Collectors.toList());
    }

    @PutMapping("/changeOrderStatus/{id}/{newStatus}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable("id") Long id,
                                               @PathVariable("newStatus") String newStatus){
        orderService.changeStatus(id,newStatus);
        return new ResponseEntity<>("Status changed!",HttpStatus.OK);
    }

    @GetMapping("/getTotalGain")
    public TotalGainDto getTotalGain(){
        TotalGainDto res = new TotalGainDto();
        Integer forMonth = 0, forWeek = 0;

        LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);

        List<_Order> orders = orderService.getRecentOrders();
        for (_Order order:orders) {
            LocalDate orderDate = order.getDate().toInstant().
                    atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (!orderDate.isBefore(monthStart))
                forMonth += order.getTotal();
            if (!orderDate.isBefore(weekStart))
                forWeek += order.getTotal();
        }

        res.setMonth(forMonth);
        res.setWeek(forWeek);
        return res;
    }

    @GetMapping("/getTopClients")
    List<UserDto> getTopClients(){
        Map<User,Integer> map = new HashMap<>();
        for (_Order order:orderService.getRecentOrders()) {
            User user = order.getClient();
            if (map.containsKey(user))
                map.put(user,map.get(user)+1);
            else
                map.put(user,1);
        }

        return map.entrySet().stream().sorted((e1,e2)->e2.getValue().compareTo(e1.getValue()))
                .limit(10).map(e->mappingService.toUserDto(e.getKey())).collect(Collectors.toList());
    }
}
