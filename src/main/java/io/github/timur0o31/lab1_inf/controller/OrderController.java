package io.github.timur0o31.lab1_inf.controller;

import io.github.timur0o31.lab1_inf.dto.OrderResponseDto;
import io.github.timur0o31.lab1_inf.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/data")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

}
