package com.example.websait.repo;

import com.example.websait.models.Post;
import org.springframework.data.repository.CrudRepository;



// CrudRepository  сервіс для роботи з круд сервісами
// <Post> з якою моделю працюємо, Long тип данних для унікального індифікатора

public interface PostRepository extends CrudRepository<Post, Long> { // для кожної поделі создають свій репозиторій



}
