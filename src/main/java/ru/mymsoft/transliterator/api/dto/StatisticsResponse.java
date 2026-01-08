package ru.mymsoft.transliterator.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mymsoft.transliterator.service.TransliterationLanguage;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Статистика транслитерации")
public class StatisticsResponse {

    @Schema(description = "ID записи", example = "1")
    private Long id;

    @Schema(description = "Язык исходного текста", example = "RUSSIAN")
    private String sourceLanguage;

    @Schema(description = "Длина текста в символах", example = "100")
    private Integer textLength;

    @Schema(description = "Время обработки в миллисекундах", example = "25")
    private Long processingTimeMs;

    @Schema(description = "Дата и время создания записи", example = "2024-01-15T14:30:00")
    private LocalDateTime createdAt;
}