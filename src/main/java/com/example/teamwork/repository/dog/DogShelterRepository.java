package com.example.teamwork.repository.dog;

import com.example.teamwork.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogShelterRepository extends JpaRepository<Dog, Long> {
}
