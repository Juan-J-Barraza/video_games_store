package com.example.store.demo.controller;

import com.example.store.demo.entity.AppUser;
import com.example.store.demo.entity.Cart;
import com.example.store.demo.entity.CartItem;
import com.example.store.demo.repository.AppUserRepository;
import com.example.store.demo.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class CartController {

    private final CartService cartService;
    private final AppUserRepository userRepository;

    public CartController(CartService cartService, AppUserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    private AppUser getCurrentUser(Authentication auth) {
        return userRepository.findByUsername(auth.getName()).orElseThrow();
    }

    @GetMapping("/cart")
    public String viewCart(Model model, Authentication auth) {
        AppUser user = getCurrentUser(auth);
        Cart cart = cartService.getOrCreateCart(user);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            total = total.add(item.getGame().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        model.addAttribute("cart", cart);
        model.addAttribute("cartTotal", total);
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("username", auth.getName());
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Integer gameId, Authentication auth) {
        AppUser user = getCurrentUser(auth);
        cartService.addToCart(user, gameId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Integer gameId, Authentication auth) {
        AppUser user = getCurrentUser(auth);
        cartService.removeFromCart(user, gameId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Authentication auth) {
        try {
            AppUser user = getCurrentUser(auth);
            cartService.checkout(user);
            return "redirect:/?success=true";
        } catch (IllegalStateException e) {
            return "redirect:/cart?error=" + e.getMessage();
        }
    }
}
