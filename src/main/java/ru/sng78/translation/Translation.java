package ru.sng78.translation;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Translation {
    private String text; //перевод слова
    private String gen; //род существительного
}
