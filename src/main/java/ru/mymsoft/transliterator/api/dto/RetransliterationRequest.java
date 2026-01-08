package ru.mymsoft.transliterator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.mymsoft.transliterator.service.TransliterationLanguage;

@Data
public class RetransliterationRequest {

    @NotNull(message = "Текст не может быть пустым")
    @NotBlank(message = "Текст не может быть пустым")
    @Schema(
            description = "Исходный текст для ретранслитерации",
            example = "Privet mir, poxozhij na cirk!"
    )
    private String transliteratedText;

    @NotNull(message = "Язык исходного текста не может быть пустым")
    @Schema(
            description = "Язык исходного текста",
            example = "RUSSIAN",
            defaultValue = "RUSSIAN"
    )
    private TransliterationLanguage language;
}