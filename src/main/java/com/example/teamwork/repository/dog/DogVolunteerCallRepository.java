package com.example.teamwork.repository.dog;

import com.example.teamwork.model.DogVolunteerCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogVolunteerCallRepository extends JpaRepository<DogVolunteerCall, Long> {
}
