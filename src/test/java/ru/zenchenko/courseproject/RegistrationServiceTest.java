package ru.zenchenko.courseproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.repositories.PeopleRepository;
import ru.zenchenko.courseproject.services.RegistrationService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void testRegister_EncodesPasswordAndSavesPerson() {
        Person person = new Person();
        person.setPassword("password");

        when(passwordEncoder.encode(person.getPassword())).thenReturn("encodedPassword");

        registrationService.register(person);

        verify(passwordEncoder).encode("password");
        verify(peopleRepository).save(person);
        assertEquals(1, person.getLevel());
        assertEquals("ROLE_USER", person.getRole());
        assertEquals("encodedPassword", person.getPassword());
    }
}