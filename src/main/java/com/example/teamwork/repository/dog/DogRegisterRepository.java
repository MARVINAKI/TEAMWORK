package com.example.teamwork.repository.dog;

import com.example.teamwork.model.DogRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogRegisterRepository extends JpaRepository<DogRegister, Long> {

	Optional<DogRegister> findDogRegisterByAdoptersChatId(Long adoptersChatId);

	Optional<DogRegister> findByDog_Id(Long dogId);

	Optional<DogRegister> findByDogAdopter_Id(Long dogAdopterId);

}
