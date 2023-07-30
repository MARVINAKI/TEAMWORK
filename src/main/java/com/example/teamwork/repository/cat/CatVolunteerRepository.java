package com.example.teamwork.repository.cat;

import com.example.teamwork.model.CatVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatVolunteerRepository extends JpaRepository<CatVolunteer, Long> {

	/**
	 * Выборка только индентификационных номеров волонтёров кошачьего приюта из таблицы БД.
	 * @return список всех идентификационных номеров
	 */
	@Query(value = "SELECT cv.id FROM CatVolunteer cv")
	List<Long> getAllId();

}
