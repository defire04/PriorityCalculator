package com.example.services;


import java.util.List;
import java.util.Optional;
import com.example.model.MathExample;
import org.springframework.stereotype.Service;
import com.example.repositories.MathExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MathExampleService {

    private final MathExampleRepository mathExampleRepository;

    @Autowired
    public MathExampleService(MathExampleRepository mathExampleRepository) {
        this.mathExampleRepository = mathExampleRepository;
    }

    public List<MathExample> findAll() {
        return mathExampleRepository.findAll();
    }

    public MathExample findOne(int id) {
        Optional<MathExample> foundMathExample = mathExampleRepository.findById(id);
        return foundMathExample.orElse(null);
    }

    @Transactional
    public void save(MathExample mathExample) {
        mathExampleRepository.save(mathExample);
    }

    @Transactional
    public void update(int id, MathExample updatedMathExample) {
        updatedMathExample.setId(id);
        mathExampleRepository.save(updatedMathExample);
    }

    @Transactional
    public void delete(int id) {
        mathExampleRepository.deleteById(id);
    }



    public List<MathExample> findByResultLessThan(double result){
        return mathExampleRepository.findByResultLessThan(result);
    }
    public List<MathExample> findByResultGreaterThan(double result){
        return mathExampleRepository.findByResultGreaterThan(result);
    }
    public List<MathExample> findByResultEquals(double result){
        return mathExampleRepository.findByResultEquals(result);
    }

}
