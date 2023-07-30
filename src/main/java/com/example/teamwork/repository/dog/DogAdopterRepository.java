package com.example.teamwork.repository.dog;

import com.example.teamwork.model.DogAdopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogAdopterRepository extends JpaRepository<DogAdopter, Long> {

	Optional<DogAdopter> findDogAdopterByFullName(String fullName);
}
