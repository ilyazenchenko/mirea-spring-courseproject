package ru.zenchenko.courseproject.services;

import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> findByLogin(String login){
        return peopleRepository.findByLogin(login);
    }

    @Transactional
    public void updateLevel(int id){
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        optionalPerson.ifPresent(p -> p.setLevel(p.getLevel()+1));
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void delete(int id){
        Optional<Person> p = peopleRepository.findById(id);
        p.ifPresent(peopleRepository::delete);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void setLevel(int id, int level){
        peopleRepository.findById(id).ifPresent(person -> person.setLevel(level));
    }
}
