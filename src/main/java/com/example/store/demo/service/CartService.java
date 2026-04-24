package com.example.store.demo.service;

import com.example.store.demo.entity.*;
import com.example.store.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository cartRepository, GameRepository gameRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
    }

    public Cart getOrCreateCart(AppUser user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart(user);
                    return cartRepository.save(newCart);
                });
    }

    @Transactional
    public void addToCart(AppUser user, Integer gameId) {
        Cart cart = getOrCreateCart(user);
        Game game = gameRepository.findById(gameId).orElseThrow();

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getGame().getId().equals(gameId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
        } else {
            CartItem newItem = new CartItem(cart, game, 1);
            cart.addItem(newItem);
        }
        cartRepository.save(cart);
    }

    @Transactional
    public void removeFromCart(AppUser user, Integer gameId) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().removeIf(item -> item.getGame().getId().equals(gameId));
        cartRepository.save(cart);
    }

    @Transactional
    public Order checkout(AppUser user) {
        Cart cart = getOrCreateCart(user);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            Game game = item.getGame();
            if (game.getStock() < item.getQuantity()) {
                throw new IllegalStateException("Not enough stock for game: " + game.getTitle());
            }
            
            // Deduct stock
            game.setStock(game.getStock() - item.getQuantity());
            gameRepository.save(game);
            
            OrderItem orderItem = new OrderItem(order, game, item.getQuantity(), game.getPrice());
            order.addItem(orderItem);
            
            total = total.add(game.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        order.setTotalPrice(total);
        Order savedOrder = orderRepository.save(order);

        // Clear cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }
}
