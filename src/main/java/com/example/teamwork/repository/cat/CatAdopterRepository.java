package com.example.teamwork.repository.cat;

import com.example.teamwork.model.CatAdopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatAdopterRepository extends JpaRepository<CatAdopter, Long> {

	Optional<CatAdopter> findFirstByFullName(String fullName);
}
