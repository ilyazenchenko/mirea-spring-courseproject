package ru.zenchenko.courseproject.services;

import org.springframework.stereotype.Service;
import ru.zenchenko.courseproject.model.Word;
import ru.zenchenko.courseproject.repositories.WordsRepository;

import java.util.List;

@Service
public class WordsService {

    private final WordsRepository wordsRepository;

    public WordsService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    public List<Word> findAllByLevel(int level){
        return wordsRepository.findAllByLevel(level);
    }
}
