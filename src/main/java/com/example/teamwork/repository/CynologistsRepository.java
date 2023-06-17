package com.example.teamwork.repository;

import com.example.teamwork.model.Cynologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CynologistsRepository extends JpaRepository<Cynologist, Long> {

}
