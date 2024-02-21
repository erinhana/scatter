package com.example.coe.repositories;

import com.example.coe.entities.ActivityBlocker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityBlockerRepository extends JpaRepository<ActivityBlocker, Integer>
{
    @Query("SELECT ab FROM ActivityBlocker ab " +
            "JOIN Activity a ON ab.activity.id = a.id " +
            "JOIN Todo t ON a.todo.id = t.id " +
            "WHERE t.user.id = :userId")
    List<ActivityBlocker> findActivityBlockersByUserId(int userId);
}
