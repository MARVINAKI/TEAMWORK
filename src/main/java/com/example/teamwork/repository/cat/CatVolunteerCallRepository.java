package com.example.teamwork.repository.cat;

import com.example.teamwork.model.CatVolunteerCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatVolunteerCallRepository extends JpaRepository<CatVolunteerCall, Long> {

}
