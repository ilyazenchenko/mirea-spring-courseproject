package ru.zenchenko.courseproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.repositories.PeopleRepository;
import ru.zenchenko.courseproject.services.PeopleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    private PeopleService peopleService;

    @BeforeEach
    public void setUp() {
        peopleService = new PeopleService(peopleRepository);
    }


    @Test
    public void testFindByLogin() {
        String login = "testuser";
        Person person = new Person();
        person.setLogin(login);

        when(peopleRepository.findByLogin(login)).thenReturn(Optional.of(person));

        Optional<Person> result = peopleService.findByLogin(login);

        assertEquals(Optional.of(person), result);
    }

    @Test
    void updateLevelTest() {
        Person person = new Person();
        person.setLevel(4);
        person.setId(1);

        when(peopleRepository.findById(person.getId())).thenReturn(Optional.of(person));

        peopleService.updateLevel(person.getId());

        Mockito.verify(peopleRepository).findById(person.getId());
        assertEquals(5, person.getLevel());
    }

    @Test
    void testFindAll() {
        Person p1 = new Person();
        p1.setId(1);
        Person p2 = new Person();
        p1.setId(2);
        Person p3 = new Person();
        p1.setId(3);
        List<Person> peopleList = new ArrayList<>(List.of(p1, p2, p3));

        when(peopleRepository.findAll()).thenReturn(peopleList);

        List<Person> result = peopleService.findAll();

        verify(peopleRepository).findAll();
        assertEquals(peopleList, result);
    }

    @Test
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void testDelete() {
        int id = 1;
        Optional<Person> personOptional = Optional.of(new Person());
        when(peopleRepository.findById(id)).thenReturn(personOptional);

        peopleService.delete(id);

        verify(peopleRepository).delete(any());
    }

    @Test
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void testSetLevel() {
        int id = 1;
        int level = 2;
        Optional<Person> personOptional = Optional.of(new Person());
        when(peopleRepository.findById(id)).thenReturn(personOptional);

        peopleService.setLevel(id, level);

        assertEquals(level, personOptional.get().getLevel());
    }

}