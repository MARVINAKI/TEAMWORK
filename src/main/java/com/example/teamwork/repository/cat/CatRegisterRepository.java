package com.example.teamwork.repository.cat;

import com.example.teamwork.model.CatRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatRegisterRepository extends JpaRepository<CatRegister, Long> {

	Optional<CatRegister> findByAdoptersChatId(Long adoptersChatId);

	Optional<CatRegister> findByCat_Id(Long catId);

	Optional<CatRegister> findByCatAdopter_Id(Long catAdopterId);
}
