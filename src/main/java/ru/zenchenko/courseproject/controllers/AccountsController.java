package ru.zenchenko.courseproject.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.security.PersonDetails;
import ru.zenchenko.courseproject.services.PeopleService;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/account")
public class AccountsController {

    private final PeopleService peopleService;

    public AccountsController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public String accountPage(@AuthenticationPrincipal PersonDetails personDetails,
                              Model model){
        model.addAttribute("person", personDetails.getPerson());
        return "account/account";
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("levelsList", IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.toList()));
        model.addAttribute("people",peopleService.findAll());
        return "account/admin";
    }

    @PostMapping("/delete/{id}")
    public String deletePerson(@PathVariable int id){
        peopleService.delete(id);
        return "redirect:/account/admin";
    }

    @PostMapping("/setlevel/{id}")
    public String setLevel(@PathVariable int id, @ModelAttribute("level") Integer level){
        peopleService.setLevel(id, level);
        return "redirect:/account/admin";
    }
}
