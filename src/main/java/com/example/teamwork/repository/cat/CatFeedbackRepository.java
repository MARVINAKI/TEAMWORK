package com.example.teamwork.repository.cat;

import com.example.teamwork.model.CatFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatFeedbackRepository extends JpaRepository<CatFeedback, Long> {

}
