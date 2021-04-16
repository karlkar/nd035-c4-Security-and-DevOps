package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void testSubmit() {
        Cart cart = new Cart();
        cart.setId(0L);
        cart.setItems(new ArrayList<>());
        User user = new User();
        user.setId(0L);
        user.setUsername("username");
        user.setPassword("password");
        user.setCart(cart);
        cart.setUser(user);
        when(userRepository.findByUsername("username")).thenReturn(user);

        ResponseEntity<UserOrder> response = orderController.submit("username");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getItems().size());
    }

    @Test
    public void testSubmit_fail() {
        ResponseEntity<UserOrder> response = orderController.submit("username");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testOrderHistory() {
        Cart cart = new Cart();
        cart.setId(0L);
        cart.setItems(new ArrayList<>());
        User user = new User();
        user.setId(0L);
        user.setUsername("username");
        user.setPassword("password");
        user.setCart(cart);
        cart.setUser(user);
        when(userRepository.findByUsername("username")).thenReturn(user);

        when(orderRepository.findByUser(user)).thenReturn(new ArrayList<>());
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("username");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testOrderHistory_fail() {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("username");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
