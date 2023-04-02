package ru.sng78.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.sng78.translation.TranslationsArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static ru.sng78.Main.*;

public class Output {
    public static void PrintToTxt(Set<String> yourEnglishWords) throws IOException {
        PrintWriter writer = new PrintWriter("Dictionary.txt", StandardCharsets.UTF_8);

        Output.PrintTitle(writer);
        Output.PrintTranslations(yourEnglishWords, writer);
        Output.PrintCopyright(writer);

        writer.close();
    }

    protected static void PrintTranslations(Set<String> yourEnglishWords, PrintWriter writer) throws IOException {
        int countOfWords = 0;
        for (String yourEnglishWord : yourEnglishWords) {
            String jsonResponse = Api.GetJson(yourEnglishWord);

            if (!jsonResponse.equals("{\"head\":{},\"def\":[]}")) {
                countOfWords += 1;
                System.out.println("Перевожу и записываю в файл слово " + yourEnglishWord);
                String json = (String) jsonResponse.subSequence(17, jsonResponse.lastIndexOf('}'));
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                List<TranslationsArray> translationsArrays;
                try {
                    translationsArrays = objectMapper.readValue(json, new TypeReference<>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                writer.println(yourEnglishWord);
                for (var translation : translationsArrays) {
                    if (PARTS_OF_SPEECH.get(translation.getPos()) != null) {
                        writer.print(PARTS_OF_SPEECH.get(translation.getPos()) + ": ");
                        for (var tr : translation.getTr()) {
                            if (tr.getGen() != null) {
                                writer.print(tr.getText() + " (" + tr.getGen() + ". род), ");
                            } else {
                                writer.print(tr.getText() + ", ");
                            }
                        }
                        writer.println();
                    }

                }
                writer.println();
            }
        }
        writer.println("Всего слов в словаре: " + countOfWords + "\n");
    }

    protected static void PrintTitle(PrintWriter writer) {
        writer.println("Ваш личный словарь по книге" + "\n");
    }

    protected static void PrintCopyright(PrintWriter writer) {
        writer.println("Реализовано с помощью сервиса API Яндекс.Словарь" + "\n" +
                "https://yandex.ru/dev/dictionary" + "\n" +
                "(C) Сергей Горбачев aka sng78, 2023");
    }
}
