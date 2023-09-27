package ru.zenchenko.courseproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.zenchenko.courseproject.model.Word;
import ru.zenchenko.courseproject.repositories.WordsRepository;
import ru.zenchenko.courseproject.services.WordsService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WordsServiceTest {

    @Mock
    private WordsRepository wordsRepository;

    private WordsService wordsService;

    @BeforeEach
    public void setUp() {
        wordsService = new WordsService(wordsRepository);
    }

    @Test
    public void testFindAllByLevel_ReturnsListOfWords() {
        int level = 1;
        List<Word> expectedWords = Arrays.asList(
                new Word(level,"стол","table"),
                new Word(level,"небо","sky"),
                new Word(level,"лампа","lamp")
        );

        when(wordsRepository.findAllByLevel(level)).thenReturn(expectedWords);

        List<Word> result = wordsService.findAllByLevel(level);

        assertEquals(expectedWords, result);
    }
}