package com.t_systems.webstore.controller.restController;

import com.t_systems.webstore.model.dto.OrderDto;
import com.t_systems.webstore.model.dto.TotalGainDto;
import com.t_systems.webstore.model.dto.UserDto;
import com.t_systems.webstore.service.MappingService;
import com.t_systems.webstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    private final MappingService mappingService;

    @GetMapping("/getOrders")
    public List<OrderDto> getOrders(){
        return orderService.getRecentOrderDtos();
    }

    @PutMapping("/changeOrderStatus/{id}/{newStatus}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable("id") Long id,
                                               @PathVariable("newStatus") String newStatus){
        orderService.changeStatus(id,newStatus);
        return new ResponseEntity<>("Status changed!", HttpStatus.OK);
    }

    @GetMapping("/getTotalGain")
    public TotalGainDto getTotalGain(){
        return orderService.getTotalGainDto();
    }

    @GetMapping("/getTopClients")
    List<UserDto> getTopClients(){
        return orderService.getTopClients();
    }
}
