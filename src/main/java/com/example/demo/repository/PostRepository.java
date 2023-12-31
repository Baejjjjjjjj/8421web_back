package com.example.demo.repository;

import com.example.demo.domain.mapping.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByCreatedAt(Pageable pageable);
}
