package com.example.teamwork.repository;

import com.example.teamwork.model.VolunteerCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerCallRepository extends JpaRepository<VolunteerCall, Long> {

}
