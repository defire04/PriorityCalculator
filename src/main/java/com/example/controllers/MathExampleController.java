package com.example.controllers;

import com.example.model.MathExample;
import com.example.services.MathExampleService;
import com.example.sql.util.MathExampleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Controller
@RequestMapping("/calculator")
public class MathExampleController {

    private final MathExampleService mathExampleService;

    private final MathExampleValidator mathExampleValidator;

    @Autowired
    public MathExampleController(MathExampleService mathExampleService, MathExampleValidator mathExampleValidator) {
        this.mathExampleService = mathExampleService;
        this.mathExampleValidator = mathExampleValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("examples", mathExampleService.findAll());
        return "calculator/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("example", mathExampleService.findOne(id));
        return "calculator/show";
    }


    @GetMapping("/new")
    public String newMathExample(@ModelAttribute("example") MathExample mathExample) {
        return "calculator/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("mathExample") @Valid MathExample mathExample, BindingResult bindingResult, Model model) {

        if (checkingErrors(mathExample, bindingResult, model)) {
            return "calculator/error";
        }
        mathExampleService.save(mathExample);
        return "redirect:/calculator/" + mathExample.getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("example", mathExampleService.findOne(id));
        return "calculator/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("mathExample") @Valid MathExample mathExample, BindingResult bindingResult, @PathVariable("id") int id, Model model) {

        if (checkingErrors(mathExample, bindingResult, model)) {
            return "calculator/error";
        }

        mathExampleService.update(id, mathExample);
        return "redirect:/calculator/" + mathExample.getId();
    }

    private boolean checkingErrors(@ModelAttribute("mathExample") @Valid MathExample mathExample, BindingResult bindingResult, Model model) {
        mathExampleValidator.validate(mathExample, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
            return true;
        }

        mathExample.setResult(Double.toString(Calculator.preparingExpression(mathExample.getExample())));
        return false;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        mathExampleService.delete(id);
        return "redirect:/calculator";
    }
}
