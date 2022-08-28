package com.example.repositories;


import java.util.List;

import com.example.model.MathExample;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface MathExampleRepository extends JpaRepository<MathExample, Integer> {
    List<MathExample> findByResultLessThan(double result);
    List<MathExample> findByResultGreaterThan(double result);
    List<MathExample> findByResultEquals(double result);
}
