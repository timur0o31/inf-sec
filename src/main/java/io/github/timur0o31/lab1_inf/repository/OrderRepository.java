package io.github.timur0o31.lab1_inf.repository;

import io.github.timur0o31.lab1_inf.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
