package com.example.websait.controllers;

import com.example.websait.models.Post;
import com.example.websait.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

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

    // @PathVariable (value = "id") вказуємо який динамічний параметр ми приймаємо
    // чи відслідковуємо повинні співпадати
    @GetMapping("/blog/{id}") // прописуємо динамічний параметр
    public String blogDetails(@PathVariable(value = "id") long id, Model model) { // від статті
        if (!postRepository.existsById(id)) { // якщо блога нема то редирект на головну
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res); // тут вже буде запис з бази данних
        return "blog-details"; // визов шаблона
    }

    // метод для форми оновлення
    @GetMapping("/blog/{id}/edit") // прописуємо динамічний параметр
    public String blogEdit(@PathVariable(value = "id") long id, Model model) { // від статті
        if (!postRepository.existsById(id)) { // якщо блога нема то редирект на головну
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res); // тут вже буде запис з бази данних
        return "blog-edit"; // визов шаблона
    }

    // метод для додавання оновлення блогу
    @PostMapping("/blog/{id}/edit") // адреса яку ми відслідковуємо
    public String blogPostUpdate(@PathVariable(value = "id") long id, // + таккож приймаємо параметр для обробки
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text, Model model) {
   // + orElseThrow() викине виключення якщо запис не був знайдений
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog"; // далі переадрес на блог
    }
    // пост метод для видалення
    @PostMapping("/blog/{id}/remove") // адреса яку ми відслідковуємо
    public String blogPostDelete(@PathVariable(value = "id") long id, // + таккож приймаємо параметр для обробки
                                 Model model) {
        // + orElseThrow() викине виключення якщо запис не був знайдений
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog"; // далі переадрес на блог
    }


}
