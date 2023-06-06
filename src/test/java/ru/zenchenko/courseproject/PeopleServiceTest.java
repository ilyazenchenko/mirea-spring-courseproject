package ru.zenchenko.courseproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.repositories.PeopleRepository;
import ru.zenchenko.courseproject.services.PeopleService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}