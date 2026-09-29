package io.github.timur0o31.lab1_inf.service;

import io.github.timur0o31.lab1_inf.dto.OrderResponseDto;
import io.github.timur0o31.lab1_inf.entity.Order;
import io.github.timur0o31.lab1_inf.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> ans = new ArrayList<>();
        for (Order order : orders){
            OrderResponseDto dto = new OrderResponseDto(
                    order.getOrderId(),
                    HtmlUtils.htmlEscape(order.getData()),
                    order.getSize());
            ans.add(dto);
        }
        return ans;

    }
}
