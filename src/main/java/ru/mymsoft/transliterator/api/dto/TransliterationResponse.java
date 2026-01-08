package ru.mymsoft.transliterator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mymsoft.transliterator.service.TransliterationLanguage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с результатом транслитерации")
public class TransliterationResponse {

    @Schema(description = "Исходный текст", example = "Привет мир, похожий на цирк!")
    private String originalText;

    @Schema(description = "Транслитерированный текст", example = "Privet mir, poxozhij na cirk!")
    private String transliteratedText;

    @Schema(description = "Язык исходного текста", example = "RUSSIAN", defaultValue = "RUSSIAN")
    private TransliterationLanguage originalLanguage;

    @Schema(description = "Длина исходного текста в символах", example = "10")
    private Integer textLength;

    @Schema(description = "Время обработки в миллисекундах", example = "15")
    private Long processingTimeMs;
}