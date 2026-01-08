package ru.mymsoft.transliterator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mymsoft.transliterator.api.dto.RetransliterationRequest;
import ru.mymsoft.transliterator.api.dto.TransliterationRequest;

import java.util.HashMap;

@Service
@Slf4j
public class TransliterationService {
    public String transliterate(TransliterationRequest request) {
        String text = request.getText();
        TransliterationLanguage language = request.getLanguage();

        log.debug("Starting transliteration for language: {}, text length: {}",
                language, text.length());

        Transliterator transliterator = new Transliterator(new HashMap<>(), language);
        return transliterator.transliterate(text);
    }

    public String retransliterate(RetransliterationRequest request) {
        String transliteratedText = request.getTransliteratedText();
        TransliterationLanguage language = request.getLanguage();

        log.debug("Starting retransliteration for language: {}, text length: {}",
                language, transliteratedText.length());

        Transliterator transliterator = new Transliterator(new HashMap<>(), language);
        return transliterator.retransliterate(transliteratedText);
    }
}