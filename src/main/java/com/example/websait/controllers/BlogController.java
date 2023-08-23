package com.example.websait.controllers;

import com.example.websait.models.Post;
import com.example.websait.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    // Iterable - масів даних отриманий з енної таблиці з бази данних
    // Iterable<Post> вказуємо модель з якою працюємо
    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts); // передаємо всі найдені пости
        return "blog-main"; // визов шаблона
    }

    @GetMapping("/blog/add") // тут вже будемо відслідковувати нову статю
    public String blogAdd(Model model) {
        return "blog-add"; // визов шаблона
    }


// @RequestParam String title отримаємо дані з параметра (blog-add) для полів title, anons i full_text
    @PostMapping("/blog/add") // тут будемо отримувати данні з форми сайту
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model) {

        Post post = new Post(title, anons, full_text);
        postRepository.save(post); // зберігаємо отриманий обьект
        return "redirect:/blog"; // далі переадрес на блог

    }


}
