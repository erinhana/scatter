package com.example.coe.repositories;

import com.example.coe.entities.Todo;
import com.example.coe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    List<Todo> findByUserId(int userId);
}
