package ru.mymsoft.transliterator.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mymsoft.transliterator.api.dto.*;
import ru.mymsoft.transliterator.entity.TransliterationStatistics;
import ru.mymsoft.transliterator.repository.TransliterationStatisticsRepository;
import ru.mymsoft.transliterator.service.StatisticsService;
import ru.mymsoft.transliterator.service.TransliterationService;
import ru.mymsoft.transliterator.service.Transliterator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Transliteration API", description = "API для транслитерации текста")
public class TransliterationController {
    private final TransliterationService transliterationService;
    private final StatisticsService statisticsService;
    private final TransliterationStatisticsRepository statisticsRepository;

    @PostMapping("/transliterate")
    @Operation(
            summary = "Транслитерировать текст",
            description = "Переводит текст с кириллицы на латиницу по выбранному языку"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная транслитерация"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<TransliterationResponse> transliterate(
            @Valid @RequestBody TransliterationRequest request,
            HttpServletRequest httpRequest) {

        long startTime = System.currentTimeMillis();

        try {
            String transliteratedText = transliterationService.transliterate(request);
            long processingTime = System.currentTimeMillis() - startTime;

            TransliterationResponse response = new TransliterationResponse(
                    request.getText(),
                    transliteratedText,
                    request.getLanguage(),
                    request.getText().length(),
                    processingTime
            );

            // Асинхронное сохранение статистики
            statisticsService.saveStatistics(
                    request.getLanguage().toString(),
                    request.getText().length(),
                    processingTime,
                    httpRequest
            );

            log.info("Transliteration completed: language={}, length={}, time={}ms",
                    request.getLanguage(), request.getText().length(), processingTime);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Transliteration failed", e);
            throw e;
        }
    }

    @PostMapping("/retransliterate")
    @Operation(
            summary = "Обратная транслитерация текста",
            description = "Переводит текст с латиницы на кириллицу по выбранному языку"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная ретранслитерация"),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<RetransliterationResponse> retransliterate(
            @Valid @RequestBody RetransliterationRequest request,
            HttpServletRequest httpRequest) {

        long startTime = System.currentTimeMillis();

        try {
            String transliteratedText = transliterationService.retransliterate(request);
            long processingTime = System.currentTimeMillis() - startTime;

            RetransliterationResponse response = new RetransliterationResponse(
                    request.getTransliteratedText(),
                    transliteratedText,
                    request.getLanguage(),
                    request.getTransliteratedText().length(),
                    processingTime
            );

            // Асинхронное сохранение статистики
            statisticsService.saveStatistics(
                    request.getLanguage().toString(),
                    request.getTransliteratedText().length(),
                    processingTime,
                    httpRequest
            );

            log.info("Retransliteration completed: language={}, length={}, time={}ms",
                    request.getLanguage(), request.getTransliteratedText().length(), processingTime);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Transliteration failed", e);
            throw e;
        }
    }

    @GetMapping("/statistics")
    @Operation(
            summary = "Получить статистику транслитераций",
            description = "Возвращает историю транслитераций с возможностью фильтрации"
    )
    public ResponseEntity<List<StatisticsResponse>> getStatistics(
            @Parameter(description = "Язык для фильтрации (опционально)")
            @RequestParam(required = false) String language,

            @Parameter(description = "Количество последних записей (по умолчанию 100)")
            @RequestParam(defaultValue = "100") int limit) {

        List<TransliterationStatistics> stats;

        if (language != null && !language.isEmpty()) {
            stats = statisticsRepository.findBySourceLanguageOrderByCreatedAtDesc(language);
        } else {
            stats = statisticsRepository.findAll()
                    .stream()
                    .sorted((s1, s2) -> s2.getCreatedAt().compareTo(s1.getCreatedAt()))
                    .limit(limit)
                    .collect(Collectors.toList());
        }

        List<StatisticsResponse> response = stats.stream()
                .map(s -> new StatisticsResponse(
                        s.getId(),
                        s.getSourceLanguage(),
                        s.getTextLength(),
                        s.getProcessingTimeMs(),
                        s.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics/summary")
    @Operation(
            summary = "Сводная статистика",
            description = "Возвращает общую сводку по всем транслитерациям"
    )
    public ResponseEntity<Object> getSummaryStatistics() {
        long totalCharacters = statisticsService.getTotalProcessedCharacters();
        double avgTime = statisticsService.getAverageProcessingTime();

        return ResponseEntity.ok(new Object() {
            public final long totalCharactersProcessed = totalCharacters;
            public final double averageProcessingTimeMs = avgTime;
            public final LocalDateTime currentTime = LocalDateTime.now();

            public final Object requestsByLanguage = new Object() {
                public final long russian = statisticsService.getRequestsCountByLanguage("RUSSIAN");
                public final long ukrainian = statisticsService.getRequestsCountByLanguage("UKRAINIAN");
                public final long belorussian = statisticsService.getRequestsCountByLanguage("BELORUSSIAN");
                public final long bulgarian = statisticsService.getRequestsCountByLanguage("BULGARIAN");
                public final long macedonian = statisticsService.getRequestsCountByLanguage("MACEDONIAN");
            };
        });
    }

    @GetMapping("/health")
    @Operation(
            summary = "Проверка работоспособности сервиса",
            description = "Проверяет, что сервис работает корректно"
    )
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok(new Object() {
            public final String status = "UP";
            public final LocalDateTime timestamp = LocalDateTime.now();
            public final String service = "Transliterator API";
        });
    }
}