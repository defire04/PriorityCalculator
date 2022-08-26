package com.example.repositories;

import com.example.model.MathExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MathExampleRepository extends JpaRepository<MathExample, Integer> {

}
