package com.example.coe.repositories;

import com.example.coe.entities.Blocker;
import com.example.coe.entities.BlockerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockerTypeRepository extends JpaRepository<BlockerType, Integer> {
}
