package com.example.controllers;

import java.util.List;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.ui.Model;
import com.example.model.MathExample;
import com.example.util.MathExampleValidator;
import com.example.services.MathExampleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;


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
            model.addAttribute("errors", bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
            return true;
        }

        mathExample.setResult(Calculator.preparingExpression(mathExample.getExample()));
        return false;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        mathExampleService.delete(id);
        return "redirect:/calculator";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "calculator/search";
    }

    @PostMapping("/search")

    public String search( @RequestParam("searchingRequest") String searchingRequest, Model model) {

        try {
            if (searchingRequest.charAt(0) == '<') {
                model.addAttribute("expressions", mathExampleService.findByResultLessThan(Double.parseDouble(searchingRequest.substring(1))));
            } else if (searchingRequest.charAt(0) == '>') {
                model.addAttribute("expressions", mathExampleService.findByResultGreaterThan(Double.parseDouble(searchingRequest.substring(1))));
            } else if (searchingRequest.charAt(0) == '='){
                model.addAttribute("expressions", mathExampleService.findByResultEquals(Double.parseDouble(searchingRequest.substring(1))));
            } else{
                model.addAttribute("expressions", mathExampleService.findByResultEquals(Double.parseDouble(searchingRequest)));
            }
        } catch (SpelEvaluationException | TemplateProcessingException exception){
            model.addAttribute("errors", new ArrayList<>(List.of("Возможно, вы ввели лишние символы!\nВ вашем запросе должен быть только знак и числовое значени!")));
            return "calculator/error";
        }

        return "calculator/searchAnswers";
    }
}


