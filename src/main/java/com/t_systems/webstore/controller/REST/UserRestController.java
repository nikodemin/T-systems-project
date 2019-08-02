package com.t_systems.webstore.controller.REST;

import com.t_systems.webstore.model.dto.OrderDto;
import com.t_systems.webstore.model.dto.ProductDto;
import com.t_systems.webstore.model.dto.TulipDto;
import com.t_systems.webstore.model.entity._Order;
import com.t_systems.webstore.service.api.MappingService;
import com.t_systems.webstore.service.api.OrderService;
import com.t_systems.webstore.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final OrderService orderService;
    private final ProductService productService;
    private final MappingService mappingService;

    @PutMapping("/addToCart/{product}")
    public ResponseEntity<?> addToCart(@PathVariable("product") String product,
                                       HttpSession session){
        OrderDto orderDto = (OrderDto) session.getAttribute("order");
        ProductDto productDto = mappingService.toProductDto(productService.getProduct(product));
        orderDto.getItems().add(productDto);
        session.setAttribute("order",orderDto);
        return new ResponseEntity<>("Product added to cart!", HttpStatus.OK);
    }

    @PutMapping("/removeFromCart/{product}")
    public ResponseEntity<?> removeFromCart(@PathVariable("product") String product,
                                       HttpSession session){
        OrderDto orderDto = (OrderDto) session.getAttribute("order");
        ProductDto productDto = mappingService.toProductDto(productService.getProduct(product));
        orderDto.getItems().remove(productDto);
        session.setAttribute("order",orderDto);
        return new ResponseEntity<>("Product removed from cart!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllFromCart/{product}")
    public ResponseEntity<?> deleteAllFromCart(@PathVariable("product") String product,
                                            HttpSession session){
        OrderDto orderDto = (OrderDto) session.getAttribute("order");
        ProductDto productDto = mappingService.toProductDto(productService.getProduct(product));
        orderDto.getItems().removeIf(p->p.equals(productDto));
        session.setAttribute("order",orderDto);
        return new ResponseEntity<>("Product deleted from cart!", HttpStatus.OK);
    }

    @PostMapping("/submitOrder")
    public ResponseEntity<?> submitOrder(HttpSession session) throws Exception{
        _Order order = mappingService.toOrder((OrderDto)session.getAttribute("order"));
        orderService.addOrder(order);
        return new ResponseEntity<>("Order submitted!", HttpStatus.OK);
    }

    @GetMapping("/getCartProducts")
    public List<TulipDto<ProductDto,Integer>> getCartProducts(HttpSession session){
        return ((OrderDto)session.getAttribute("order")).getUniqueProducts();
    }
}
