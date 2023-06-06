package ru.zenchenko.courseproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.repositories.PeopleRepository;
import ru.zenchenko.courseproject.security.PersonDetails;
import ru.zenchenko.courseproject.services.PersonDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonDetailsServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    private PersonDetailsService personDetailsService;

    @BeforeEach
    public void setUp() {
        personDetailsService = new PersonDetailsService(peopleRepository);
    }

    @Test
    public void testLoadUserByUsername_ExistingPerson_ReturnsPersonDetails() {
        String username = "testuser";
        Person person = new Person();
        person.setLogin(username);

        when(peopleRepository.findByLogin(username)).thenReturn(Optional.of(person));

        PersonDetails result = (PersonDetails) personDetailsService.loadUserByUsername(username);

        assertEquals(person, result.getPerson());
    }

    @Test
    public void testLoadUserByUsername_NonExistingPerson_ThrowsUsernameNotFoundException() {
        String username = "testuser";

        when(peopleRepository.findByLogin(username)).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> personDetailsService.loadUserByUsername(username)
        );
    }
}