package ru.zenchenko.courseproject.controllers;

//import org.springframework.security.core.parameters.P;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zenchenko.courseproject.model.Person;
import ru.zenchenko.courseproject.model.Word;
import ru.zenchenko.courseproject.security.PersonDetails;
import ru.zenchenko.courseproject.services.PeopleService;
import ru.zenchenko.courseproject.services.WordsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    private final WordsService wordsService;
    private final PeopleService peopleService;
    private List<Word> currentLessonWordsList;
    private List<Integer> usedWordsList;
    private int currentWordIndex;
    private int errorsCnt;
    private int chosenLevel;

    public TasksController(WordsService wordsService, PeopleService peopleService) {
        this.wordsService = wordsService;
        this.peopleService = peopleService;
    }

    //Основная страница

    @GetMapping
    public String tasksPage(Model model, @AuthenticationPrincipal PersonDetails personDetails) {
        chosenLevel = personDetails.getPerson().getLevel();
        return tasksPage(chosenLevel, model, personDetails);
    }

    @GetMapping("/{level}")
    public String tasksPage(@PathVariable int level, Model model,
                            @AuthenticationPrincipal PersonDetails personDetails) {
        chosenLevel = level;
        Person currentUser = initPersonAndLists(personDetails, level);
        if (level > currentUser.getLevel()) {
            model.addAttribute("highLevel", true);
            return "redirect:/tasks/" + currentUser.getLevel();
        }
        model.addAttribute("userLevel", currentUser.getLevel());
        model.addAttribute("chosenLevel", level);
        model.addAttribute("lessonsNumList", IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.toList()));
        return "tasks/tasks";
    }

    private Person initPersonAndLists(PersonDetails personDetails, int level) {
        Person currentUser = personDetails.getPerson();
        currentLessonWordsList = wordsService.findAllByLevel(level);
        usedWordsList = new ArrayList<>();
        return currentUser;
    }


    //Обработка learn

    @GetMapping("/learn")
    public String learnPage(Model model) {
        addNewWordInModel(model);
        return "tasks/learn";
    }

    @PostMapping("/learn")
    public String getNewWord(Model model) {
        if (usedWordsList.size() == 10) {
            return "redirect:/tasks/" + chosenLevel;
        }
        addNewWordInModel(model);
        return "tasks/learn";
    }

    //Обработка карточек

    @GetMapping("/cards/en-ru")
    public String getEnRuCardsPage(Model model) {
        addNewWordInModel(model);
        model.addAttribute("order", "en-ru");
        return "tasks/cards";
    }

    @GetMapping("/cards/ru-en")
    public String getRuEnCardsPage(Model model) {
        addNewWordInModel(model);
        model.addAttribute("order", "ru-en");
        return "tasks/cards";
    }

    @PostMapping("/cards/en-ru")
    public String getNewCardsWordEnRu(Model model) {
        if (usedWordsList.size() == 10) {
            return "redirect:/tasks/" + chosenLevel;
        }
        addNewWordInModel(model);
        model.addAttribute("order", "en-ru");
        return "tasks/cards";
    }

    @PostMapping("/cards/ru-en")
    public String getNewCardsWordRuEn(Model model) {
        if (usedWordsList.size() == 10) {
            return "redirect:/tasks/" + chosenLevel;
        }
        addNewWordInModel(model);
        model.addAttribute("order", "ru-en");
        return "tasks/cards";
    }

    //Предложения

    @GetMapping("/sentence")
    public String getSentencePage(Model model) {
        addNewWordInModel(model);
        addShuffledListInModel(model);
        return "tasks/sentence";
    }

    @PostMapping("/sentence")
    public String checkSentence(@ModelAttribute("sentence") String sentence, Model model) {
        if (!sentence.trim().equals(currentLessonWordsList.get(currentWordIndex).getSentence())) {
            addOldWordInModel(model);
            model.addAttribute("wrongSentence", true);
        } else {
            if (usedWordsList.size() == 10)
                return "redirect:/tasks/" + chosenLevel;
            addNewWordInModel(model);
        }
        addShuffledListInModel(model);
        return "tasks/sentence";
    }

    // Страница теста

    @GetMapping("/test")
    public String getTestPage(Model model) {
        addNewWordInModel(model);
        return "tasks/test";
    }

    @PostMapping("/test")
    public String checkTestAnswer(@ModelAttribute("answer") String answer, Model model,
                                  @AuthenticationPrincipal PersonDetails personDetails) {
        if (!answer.equals(currentLessonWordsList.get(currentWordIndex).getEngWord())) {
            errorsCnt++;
            if (errorsCnt == 3) {
                errorsCnt = 0;
                return "redirect:/tasks/" + chosenLevel;
            }
            addOldWordInModel(model);
            model.addAttribute("testError", true);
        } else {
            if (usedWordsList.size() == 10) {
                Person p = personDetails.getPerson();
                if (chosenLevel == p.getLevel()) {
                    peopleService.updateLevel(p);
                    chosenLevel++;
                }
                return "redirect:/tasks/"+chosenLevel;
            }
            addNewWordInModel(model);
        }
        return "tasks/test";
    }

    //Добавление перемешанного списка в модель
    private void addShuffledListInModel(Model model) {
        String[] words = currentLessonWordsList.get(currentWordIndex).getSentence().split(" ");
        List<String> shuffledWords = Arrays.asList(words);
        Collections.shuffle(shuffledWords);
        model.addAttribute("shuffledWords", shuffledWords);
    }
    //Добавление нового слова в модель

    private void addNewWordInModel(Model model) {
        do {
            currentWordIndex = (int) (Math.random() * 10);
        } while (usedWordsList.contains(currentWordIndex));
        usedWordsList.add(currentWordIndex);
        model.addAttribute("word", currentLessonWordsList.get(currentWordIndex));
    }

    //Добавление текущего слова в модель

    private void addOldWordInModel(Model model) {
        model.addAttribute("word", currentLessonWordsList.get(currentWordIndex));
    }
}
