package com.manerajona.openapigen.rs;

import com.manerajona.openapigen.core.domain.Consumer;
import com.manerajona.openapigen.core.domain.Order;
import com.manerajona.openapigen.core.domain.OrderItem;
import com.manerajona.openapigen.rs.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrdersControllerMapper {

    public Order orderRequestToOrder(OrderRequest request) {
        if (request == null) {
            return null;
        }
        Set<OrderItem> items = this.orderItemRequestListToOrderItemSet(request.getOrderItems());
        Consumer consumer = this.consumerRequestToConsumer(request.getConsumer());
        String notes = request.getNotes();
        return new Order(consumer, items, notes);
    }

    public OrderResponse orderToOrderResponse(Order order) {
        if (order == null) {
            return null;
        }
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderItems(this.orderItemSetToOrderItemResponseList(order.items()));
        if (order.id() != null) {
            orderResponse.setId(order.id().toString());
        }
        if (order.state() != null) {
            orderResponse.setState(OrderResponse.StateEnum.fromValue(order.state().name()));
        }
        orderResponse.setNotes(order.notes());
        orderResponse.setConsumer(this.consumerToConsumerResponse(order.consumer()));
        return orderResponse;
    }

    private OrderItem orderItemRequestToOrderItem(OrderItemRequest orderItemRequest) {
        if (orderItemRequest == null) {
            return null;
        }
        String name = orderItemRequest.getName();
        Integer quantity = orderItemRequest.getQuantity();
        return new OrderItem(name, quantity);
    }

    private Set<OrderItem> orderItemRequestListToOrderItemSet(List<OrderItemRequest> list) {
        return list.stream()
                .map(this::orderItemRequestToOrderItem)
                .collect(Collectors.toCollection(
                        () -> new LinkedHashSet<>(Math.max((int) (list.size() / 0.75F) + 1, 16)))
                );
    }

    private Consumer consumerRequestToConsumer(ConsumerRequest consumerRequest) {
        if (consumerRequest == null) {
            return null;
        }
        String name = consumerRequest.getName();
        String address = consumerRequest.getAddress();
        String phone = consumerRequest.getPhone();
        return new Consumer(name, address, phone);
    }

    private OrderItemResponse orderItemToOrderItemResponse(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setName(orderItem.name());
        orderItemResponse.setQuantity(orderItem.quantity());
        return orderItemResponse;
    }

    private List<OrderItemResponse> orderItemSetToOrderItemResponseList(Set<OrderItem> set) {
        return set.stream()
                .map(this::orderItemToOrderItemResponse)
                .collect(Collectors.toCollection(() -> new ArrayList<>(set.size())));
    }

    private ConsumerResponse consumerToConsumerResponse(Consumer consumer) {
        if (consumer == null) {
            return null;
        }
        ConsumerResponse consumerResponse = new ConsumerResponse();
        consumerResponse.setName(consumer.name());
        consumerResponse.setAddress(consumer.address());
        consumerResponse.setPhone(consumer.phone());
        return consumerResponse;
    }
}