package com.example.teamwork.repository.dog;

import com.example.teamwork.model.DogFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogFeedbackRepository extends JpaRepository<DogFeedback, Long> {
}
