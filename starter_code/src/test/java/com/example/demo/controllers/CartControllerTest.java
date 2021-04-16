package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void testAddToCart() {
        Cart cart = new Cart();
        cart.setId(0L);
        User user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setPassword("password");
        user.setCart(cart);
        cart.setUser(user);
        when(userRepository.findByUsername("username")).thenReturn(user);

        Item item = new Item();
        item.setId(0L);
        item.setName("Kielbasa");
        item.setDescription("Pyszna");
        item.setPrice(new BigDecimal("12.32"));
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0);
        modifyCartRequest.setUsername("username");
        modifyCartRequest.setQuantity(10);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getId().longValue());
        assertEquals(10, response.getBody().getItems().size());
    }

    @Test
    public void test() {
        Cart cart = new Cart();
        cart.setId(0L);
        User user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setPassword("password");
        user.setCart(cart);
        cart.setUser(user);
        when(userRepository.findByUsername("username")).thenReturn(user);

        Item item = new Item();
        item.setId(0L);
        item.setName("Kielbasa");
        item.setDescription("Pyszna");
        item.setPrice(new BigDecimal("12.32"));
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0);
        modifyCartRequest.setUsername("username");
        modifyCartRequest.setQuantity(10);

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getId().longValue());
        assertEquals(0, response.getBody().getItems().size());
    }
}
