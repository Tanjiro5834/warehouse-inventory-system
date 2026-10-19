package com.warehouse.repository;

import com.warehouse.model.ToolCheckout;

import java.util.List;
import java.util.Optional;

public class ToolCheckoutRepository {

    List<ToolCheckout> findActive();

    List<ToolCheckout> findAll();

    Optional<ToolCheckout> findById(String id);

    ToolCheckout save(ToolCheckout checkout);

    void markReturned(String id);

    int countActive();
}