package ru.zenchenko.courseproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> optional = peopleService.findByLogin(person.getLogin());
        if (optional.isPresent())
            errors.rejectValue("login", "",
                    "Такое имя пользователя уже существует");
    }
}