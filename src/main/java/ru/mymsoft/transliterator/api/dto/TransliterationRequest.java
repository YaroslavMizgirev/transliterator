package ru.mymsoft.transliterator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.mymsoft.transliterator.service.TransliterationLanguage;

@Data
@Schema(description = "Запрос на транслитерацию текста")
public class TransliterationRequest {

    @NotNull(message = "Текст не может быть пустым")
    @NotBlank(message = "Текст не может быть пустым")
    @Schema(
            description = "Исходный текст для транслитерации",
            example = "Привет мир, похожий на цирк!"
    )
    private String text;

    @NotNull(message = "Язык исходного текста не может быть пустым")
    @Schema(
            description = "Язык исходного текста",
            example = "RUSSIAN",
            defaultValue = "RUSSIAN"
    )
    private TransliterationLanguage language;
}