package ru.mymsoft.transliterator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mymsoft.transliterator.entity.TransliterationStatistics;

import java.time.LocalDateTime;
import java.util.List;

public interface TransliterationStatisticsRepository extends JpaRepository<TransliterationStatistics, Long> {
    List<TransliterationStatistics> findBySourceLanguageOrderByCreatedAtDesc(String sourceLanguage);

    @Query("SELECT ts FROM TransliterationStatistics ts WHERE ts.createdAt >= :fromDate")
    List<TransliterationStatistics> findStatisticsSince(LocalDateTime fromDate);

    @Query("SELECT AVG(ts.processingTimeMs) FROM TransliterationStatistics ts")
    Double getAverageProcessingTime();

    @Query("SELECT SUM(ts.textLength) FROM TransliterationStatistics ts")
    Long getTotalCharactersProcessed();

    @Query("SELECT COUNT(ts) FROM TransliterationStatistics ts WHERE ts.sourceLanguage = :language")
    Long countByLanguage(String language);
}