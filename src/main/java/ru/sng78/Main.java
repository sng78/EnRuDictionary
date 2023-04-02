package ru.sng78;

import ru.sng78.io.Input;
import ru.sng78.io.Output;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static final String PATH = "pdf//";
    public static final String URI =
            "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=%s&lang=en-ru&text=%s";
    public static final String YOUR_API_KEY =
            "dict.1.1.20230330T064832Z.958ba27c3c769e87.3307a635ab260417d57ada874da5ebb4d347774f";
    public static final Map<String, String> PARTS_OF_SPEECH = Stream.of(new String[][]{
            {"noun", "сущ"}, {"adjective", "прил"}, {"numeral", "числ"},
            {"pronoun", "мест"}, {"verb", "гл"}, {"participle", "прич"},
            {"adverb", "нареч"}, {"preposition", "предл"}, {"conjunction", "союз"},
            {"particle", "част"}, {"interjection", "межд"}, {"determiner", "арт/дет"},
            {"foreign word", "иностр"}, {"predicative", "предик"}, {"invariable", "неизм"},
            {"parenthetic", "вводн"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static void main(String[] args) throws IOException {
        Set<String> yourEnglishWords = Input.ReadFromPdf();
        Output.PrintToTxt(yourEnglishWords);
    }
}
