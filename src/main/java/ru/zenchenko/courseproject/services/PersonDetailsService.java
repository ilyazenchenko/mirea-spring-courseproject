package ru.zenchenko.courseproject.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.repositories.PeopleRepository;
import ru.zenchenko.courseproject.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> personOptional = peopleRepository.findByLogin(username);
        if(personOptional.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");
        return new PersonDetails(personOptional.get());
    }
}
