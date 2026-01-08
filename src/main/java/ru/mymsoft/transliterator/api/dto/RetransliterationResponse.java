package ru.mymsoft.transliterator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mymsoft.transliterator.service.TransliterationLanguage;

@Data
@AllArgsConstructor
public class RetransliterationResponse {

    @Schema(description = "Транслитерированный текст", example = "Privet mir, poxozhij na cirk!")
    private String transliteratedText;

    @Schema(description = "Восстановленный текст", example = "Привет мир, похожий на цирк!")
    private String repairText;

    @Schema(description = "Язык восстановленного текста", example = "RUSSIAN")
    private TransliterationLanguage language;

    @Schema(description = "Длина транслитерированного текста в символах", example = "29")
    private Integer transliteratedTextLength;

    @Schema(description = "Время обработки в миллисекундах", example = "15")
    private Long processingTimeMs;
}