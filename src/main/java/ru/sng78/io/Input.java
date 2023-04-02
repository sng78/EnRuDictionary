package ru.sng78.io;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.atteo.evo.inflector.English;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.sng78.Main.*;

public class Input {
    public static Set<String> ReadFromPdf() throws IOException {
        File pdfDir = new File(PATH);
        List<File> files; //создаем список файлов директории pdfs
        try (Stream<Path> paths = Files.walk(Paths.get(String.valueOf(pdfDir)))) {
            files = paths.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
        }

        Set<String> wordsList = new TreeSet<>();
        if (files.size() != 1) {
            System.out.println("В директории должен быть 1 файл!!!");
        } else {
            File file = new File(files.get(0).toURI());
            var doc = new PdfDocument(new PdfReader(file)); //создаем объект пдф-документа
            for (int i = 1; i < doc.getNumberOfPages() + 1; i++) {
                var pdfPage = doc.getPage(i);  //получаем объект одной страницы документа
                var text = PdfTextExtractor.getTextFromPage(pdfPage); //получаем текст со страницы
                var words = text.split("\\P{IsLatin}+"); //разбиваем полученный текст на слова

                for (String word : words) { //перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    wordsList.add(word);
                }
            }
        }

        List<String> wordsPlural = new ArrayList<>(); //создаем список слов в множ. числе
        for (String word : wordsList) {
            String wordPlural = English.plural(word);
            if (wordsList.contains(word) && wordsList.contains(wordPlural)) {
                wordsPlural.add(wordPlural);
            }
        }

        for (String wordPlural : wordsPlural) { //удаляем слова в множ. числе
            wordsList.remove(wordPlural);
        }
        return wordsList;
    }
}
