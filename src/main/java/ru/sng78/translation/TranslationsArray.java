package ru.sng78.translation;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TranslationsArray {
    private String pos; //часть речи
    private String ts; //транскрипция
    private List<Translation> tr; //список переводов слова
}
