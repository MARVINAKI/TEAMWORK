package com.example.teamwork.repository;

import com.example.teamwork.model.DogVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogVolunteerRepository extends JpaRepository<DogVolunteer, Long> {

	/**
	 * Выборка только индентификационных номеров волонтёров собачего приюта из таблицы БД.
	 * @return список всех идентификационных номеров
	 */
	@Query(value = "SELECT dv.id FROM DogVolunteer dv")
	List<Long> getAllId();
}
