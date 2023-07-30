package com.example.teamwork.repository.cat;

import com.example.teamwork.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatShelterRepository extends JpaRepository<Cat, Long> {
}
