package com.t_systems.webstore.controller.restController;

import com.t_systems.webstore.model.dto.*;
import com.t_systems.webstore.model.entity.Address;
import com.t_systems.webstore.model.entity.Card;
import com.t_systems.webstore.model.entity._Order;
import com.t_systems.webstore.model.enums.DeliveryMethod;
import com.t_systems.webstore.model.enums.OrderStatus;
import com.t_systems.webstore.model.enums.PaymentMethod;
import com.t_systems.webstore.service.MappingService;
import com.t_systems.webstore.service.OrderService;
import com.t_systems.webstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final OrderService orderService;
    private final MappingService mappingService;
    private Address address = null;
    private Card card = null;


    @GetMapping("/getCartProducts")
    public List<TulipDto<ProductDto,Integer>> getCartProducts(HttpSession session){
        return ((OrderDto)session.getAttribute("order")).getUniqueProducts();
    }

    @PostMapping("/setAddress")
    public Boolean setAddress(@ModelAttribute("address") @Valid AddressDto addressDto, BindingResult result){
        if (result.hasErrors()) {
            return true;
        }
        address = mappingService.toAddress(addressDto);
        return false;
    }

    @PostMapping("/setCard")
    public Boolean setCard(@ModelAttribute("card") @Valid CardDto cardDto, BindingResult result){
        if (result.hasErrors()) {
            return true;
        }
        card = mappingService.toCard(cardDto);
        return false;
    }

    @GetMapping("/isCartEmpty")
    public Boolean isCartEmpty(HttpSession session){
        return ((OrderDto)session.getAttribute("order")).getItems().size() == 0;
    }

    @PostMapping("/submitOrder")
    public ResponseEntity<?> submitOrder(HttpSession session, Principal principal) throws Exception{
        _Order order = mappingService.toOrder((OrderDto)session.getAttribute("order"),principal.getName());
        if (address == null)
            order.setDeliveryMethod(DeliveryMethod.PICKUP);
        else{
            order.setDeliveryMethod(DeliveryMethod.COURIER);
            order.setAddress(address);
        }

        if (card == null)
            order.setPaymentMethod(PaymentMethod.CASH);
        else{
            order.setPaymentMethod(PaymentMethod.CARD);
            order.setStatus(OrderStatus.PAID);
            order.setCard(card);
        }

        OrderDto newOrder = new OrderDto();
        newOrder.setStatus(OrderStatus.UNPAID.toString());
        newOrder.setItems(new ArrayList<>());
        session.setAttribute("order", newOrder);

        order.setClient(userService.findUser(principal.getName()));
        orderService.addOrder(order);
        return new ResponseEntity<>("Order submitted!", HttpStatus.OK);
    }
}
