package ru.zenchenko.courseproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zenchenko.courseproject.model.Word;

import java.util.List;

@Repository
public interface WordsRepository extends JpaRepository<Word, Integer> {
    List<Word> findAllByLevel(int level);
}