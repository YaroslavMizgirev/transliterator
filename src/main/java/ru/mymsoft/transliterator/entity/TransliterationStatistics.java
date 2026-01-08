package ru.mymsoft.transliterator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.mymsoft.transliterator.service.TransliterationLanguage;

import java.time.LocalDateTime;

@Entity
@Table(name = "transliteration_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransliterationStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sourceLanguage;

    @Column(nullable = false)
    private Integer textLength;

    @Column(nullable = false)
    private Long processingTimeMs;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private String ipAddress;

    @Column
    private String userAgent;

    @PrePersist
    public void calculateProcessingTime() {
        if (processingTimeMs == null) {
            processingTimeMs = 0L;
        }
    }
}