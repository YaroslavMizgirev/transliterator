package ru.mymsoft.transliterator.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mymsoft.transliterator.entity.TransliterationStatistics;
import ru.mymsoft.transliterator.repository.TransliterationStatisticsRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {
    private final TransliterationStatisticsRepository statisticsRepository;

    @Async
    @Transactional
    public void saveStatistics(String sourceLanguage, int textLength,
                               long processingTimeMs, HttpServletRequest request) {
        try {
            TransliterationStatistics stats = new TransliterationStatistics();
            stats.setSourceLanguage(sourceLanguage);
            stats.setTextLength(textLength);
            stats.setProcessingTimeMs(processingTimeMs);

            if (request != null) {
                stats.setIpAddress(getClientIp(request));
                stats.setUserAgent(request.getHeader("User-Agent"));
            }

            statisticsRepository.save(stats);
            log.debug("Statistics saved: language={}, length={}, time={}ms",
                    sourceLanguage, textLength, processingTimeMs);
        } catch (Exception e) {
            log.error("Failed to save statistics", e);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    public long getTotalProcessedCharacters() {
        Long total = statisticsRepository.getTotalCharactersProcessed();
        return total != null ? total : 0L;
    }

    public double getAverageProcessingTime() {
        Double avg = statisticsRepository.getAverageProcessingTime();
        return avg != null ? avg : 0.0;
    }

    public long getRequestsCountByLanguage(String language) {
        Long count = statisticsRepository.countByLanguage(language);
        return count != null ? count : 0L;
    }
}