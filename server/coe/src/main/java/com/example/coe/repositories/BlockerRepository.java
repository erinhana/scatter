package com.example.coe.repositories;

import com.example.coe.entities.Blocker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockerRepository extends JpaRepository<Blocker, Integer> {
//    @EntityGraph(attributePaths = {"user", "blockerType"})
//    Blocker save(Blocker blocker);
}
