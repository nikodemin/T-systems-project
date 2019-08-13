package com.t_systems.webstore.controller.Controller;

import com.t_systems.webstore.model.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/admin/stats")
    public String getStatsAdminPage(Model model){
        List<String> statusList = Stream.of(OrderStatus.values())
                .map(OrderStatus::toString).collect(Collectors.toList());
        model.addAttribute("statusList",statusList);
        return "statsAdmin";
    }
}
