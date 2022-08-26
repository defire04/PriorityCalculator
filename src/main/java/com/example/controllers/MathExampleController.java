package com.example.controllers;

import com.example.model.MathExample;
import com.example.services.MathExampleService;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/calculator")
public class MathExampleController {

    private final MathExampleService mathExampleService;

    @Autowired
    public MathExampleController(MathExampleService mathExampleService) {
        this.mathExampleService = mathExampleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("examples", mathExampleService.findAll());
        return "calculator/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("example", mathExampleService.findOne(id));
        return "calculator/show";
    }


    @GetMapping("/new")
    public String newMathExample(@ModelAttribute("example") MathExample mathExample) {
        return "calculator/new";
    }


    @PostMapping()
    public String create(@RequestParam("example") String mathExample) {


        MathExample newMathExample = new MathExample();
        newMathExample.setExample(mathExample);

        // Решение
        newMathExample.setResult("2323");

        mathExampleService.save(newMathExample);
        return "redirect:/calculator/" + newMathExample.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable ("id") int id){
        model.addAttribute("example", mathExampleService.findOne(id));
        return "calculator/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("mathExample")  MathExample mathExample,  @PathVariable("id") int id){

        mathExampleService.update(id, mathExample);
        return "redirect:/calculator";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        mathExampleService.delete(id);
        return "redirect:/calculator";
    }
}
