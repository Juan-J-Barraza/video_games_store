package com.example.store.demo.controller;

import com.example.store.demo.repository.GameRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {

    private final GameRepository gameRepository;

    public StoreController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/")
    public String home(Model model, Authentication auth) {
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("isLoggedIn", auth != null && auth.isAuthenticated());
        model.addAttribute("username", auth != null ? auth.getName() : null);
        return "store";
    }
}
