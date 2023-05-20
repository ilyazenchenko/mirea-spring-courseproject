package ru.zenchenko.courseproject.services;

import jakarta.transaction.Transactional;
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
    public void updateLevel(Person person){
        person.setLevel(person.getLevel()+1);
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    @Transactional
    public void delete(int id){
        Optional<Person> p =  peopleRepository.findById(id);
        p.ifPresent(peopleRepository::delete);
    }

    @Transactional
    public void setLevel(int id, int level){
        Optional<Person> opt = peopleRepository.findById(id);
        if(opt.isPresent()) {
            Person p = opt.get();
            p.setLevel(level);
        }
    }
}
