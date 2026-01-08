package ru.mymsoft.transliterator.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class Transliterator {
    private final Map<Character, String> transliterationMap;
    private final Map<String, Character> reverseTransliterationMap;
    private final TransliterationLanguage language;

    public Transliterator() {
        this.transliterationMap = RussianTransliterationMap(new HashMap<>());
        this.language = TransliterationLanguage.RUSSIAN;
        this.reverseTransliterationMap = buildReverseMap(this.transliterationMap);
    }

    public Transliterator(Map<Character, String> transliterationMap, @NonNull TransliterationLanguage language) {
        switch (language) {
            case BELORUSSIAN -> this.transliterationMap = BelorussianTransliterationMap(transliterationMap);
            case UKRAINIAN -> this.transliterationMap = UkrainianTransliterationMap(transliterationMap);
            case BULGARIAN -> this.transliterationMap = BulgarianTransliterationMap(transliterationMap);
            case MACEDONIAN -> this.transliterationMap = MacedonianTransliterationMap(transliterationMap);
            default -> this.transliterationMap = RussianTransliterationMap(transliterationMap);
        }
        this.language = language;
        this.reverseTransliterationMap = buildReverseMap(this.transliterationMap);
    }

    /**
     * Создает обратную карту транслитерации (Latin → Cyrillic)
     * Важно: многосимвольные комбинации должны идти первыми!
     */
    private @NonNull Map<String, Character> buildReverseMap(@NonNull Map<Character, String> forwardMap) {
        Map<String, Character> reverseMap = new LinkedHashMap<>();

        // Сначала добавляем многосимвольные комбинации
        forwardMap.entrySet().stream()
                .sorted((e1, e2)
                        -> Integer.compare(e2.getValue().length(), e1.getValue().length()))
                .forEach(entry -> {
                    if (!reverseMap.containsKey(entry.getValue())) {
                        reverseMap.put(entry.getValue(), entry.getKey());
                    }
                });

        // Также добавляем варианты в нижнем регистре для поиска
        Map<String, Character> lowerCaseMap = new LinkedHashMap<>();
        reverseMap.forEach((key, value) -> {
            lowerCaseMap.put(key.toLowerCase(), Character.toLowerCase(value));
            lowerCaseMap.put(key.toUpperCase(), Character.toUpperCase(value));
        });

        reverseMap.putAll(lowerCaseMap);

        log.debug("Built reverse map with {} entries for language: {}",
                reverseMap.size(), language);

        return reverseMap;
    }

    public Map<Character, String> RussianTransliterationMap(@NonNull Map<Character, String> transliterationMap) {

        transliterationMap.put('а', "a");
        transliterationMap.put('б', "b");
        transliterationMap.put('в', "v");
        transliterationMap.put('г', "g");
        transliterationMap.put('д', "d");
        transliterationMap.put('е', "e");
        transliterationMap.put('ё', "yo");
        transliterationMap.put('ж', "zh");
        transliterationMap.put('з', "z");
        transliterationMap.put('и', "i");
        transliterationMap.put('й', "j");
        transliterationMap.put('к', "k");
        transliterationMap.put('л', "l");
        transliterationMap.put('м', "m");
        transliterationMap.put('н', "n");
        transliterationMap.put('о', "o");
        transliterationMap.put('п', "p");
        transliterationMap.put('р', "r");
        transliterationMap.put('с', "s");
        transliterationMap.put('т', "t");
        transliterationMap.put('у', "u");
        transliterationMap.put('ф', "f");
        transliterationMap.put('х', "x");
        transliterationMap.put('ц', "cz");
        transliterationMap.put('ч', "ch");
        transliterationMap.put('ш', "sh");
        transliterationMap.put('щ', "shh");
        transliterationMap.put('ъ', "``");
        transliterationMap.put('ы', "y`");
        transliterationMap.put('ь', "`");
        transliterationMap.put('э', "e`");
        transliterationMap.put('ю', "yu");
        transliterationMap.put('я', "ya");

        transliterationMap.put('А', "A");
        transliterationMap.put('Б', "B");
        transliterationMap.put('В', "V");
        transliterationMap.put('Г', "G");
        transliterationMap.put('Д', "D");
        transliterationMap.put('Е', "E");
        transliterationMap.put('Ё', "Yo");
        transliterationMap.put('Ж', "Zh");
        transliterationMap.put('З', "Z");
        transliterationMap.put('И', "I");
        transliterationMap.put('Й', "J");
        transliterationMap.put('К', "K");
        transliterationMap.put('Л', "L");
        transliterationMap.put('М', "M");
        transliterationMap.put('Н', "N");
        transliterationMap.put('О', "O");
        transliterationMap.put('П', "P");
        transliterationMap.put('Р', "R");
        transliterationMap.put('С', "S");
        transliterationMap.put('Т', "T");
        transliterationMap.put('У', "U");
        transliterationMap.put('Ф', "F");
        transliterationMap.put('Х', "X");
        transliterationMap.put('Ц', "Cz");
        transliterationMap.put('Ч', "Ch");
        transliterationMap.put('Ш', "Sh");
        transliterationMap.put('Щ', "Shh");
        transliterationMap.put('Ъ', "``");
        transliterationMap.put('Ы', "Y`");
        transliterationMap.put('Ь', "`");
        transliterationMap.put('Э', "E`");
        transliterationMap.put('Ю', "Yu");
        transliterationMap.put('Я', "Ya");

        return transliterationMap;
    }

    public Map<Character, String> BelorussianTransliterationMap(@NonNull Map<Character, String> transliterationMap) {

        transliterationMap.put('а', "a");
        transliterationMap.put('б', "b");
        transliterationMap.put('в', "v");
        transliterationMap.put('г', "g");
        transliterationMap.put('д', "d");
        transliterationMap.put('е', "e");
        transliterationMap.put('ё', "yo");
        transliterationMap.put('ж', "zh");
        transliterationMap.put('з', "z");
        transliterationMap.put('і', "i");
        transliterationMap.put('й', "j");
        transliterationMap.put('к', "k");
        transliterationMap.put('л', "l");
        transliterationMap.put('м', "m");
        transliterationMap.put('н', "n");
        transliterationMap.put('о', "o");
        transliterationMap.put('п', "p");
        transliterationMap.put('р', "r");
        transliterationMap.put('с', "s");
        transliterationMap.put('т', "t");
        transliterationMap.put('у', "u");
        transliterationMap.put('ў', "u`");
        transliterationMap.put('ф', "f");
        transliterationMap.put('х', "x");
        transliterationMap.put('ц', "cz");
        transliterationMap.put('ч', "ch");
        transliterationMap.put('ш', "sh");
        transliterationMap.put('ы', "y`");
        transliterationMap.put('ь', "`");
        transliterationMap.put('э', "e`");
        transliterationMap.put('ю', "yu");
        transliterationMap.put('я', "ya");

        transliterationMap.put('А', "A");
        transliterationMap.put('Б', "B");
        transliterationMap.put('В', "V");
        transliterationMap.put('Г', "G");
        transliterationMap.put('Д', "D");
        transliterationMap.put('Е', "E");
        transliterationMap.put('Ё', "Yo");
        transliterationMap.put('Ж', "Zh");
        transliterationMap.put('З', "Z");
        transliterationMap.put('І', "I");
        transliterationMap.put('Й', "J");
        transliterationMap.put('К', "K");
        transliterationMap.put('Л', "L");
        transliterationMap.put('М', "M");
        transliterationMap.put('Н', "N");
        transliterationMap.put('О', "O");
        transliterationMap.put('П', "P");
        transliterationMap.put('Р', "R");
        transliterationMap.put('С', "S");
        transliterationMap.put('Т', "T");
        transliterationMap.put('У', "U");
        transliterationMap.put('Ў', "U`");
        transliterationMap.put('Ф', "F");
        transliterationMap.put('Х', "X");
        transliterationMap.put('Ц', "Cz");
        transliterationMap.put('Ч', "Ch");
        transliterationMap.put('Ш', "Sh");
        transliterationMap.put('Ы', "Y`");
        transliterationMap.put('Ь', "`");
        transliterationMap.put('Э', "E`");
        transliterationMap.put('Ю', "Yu");
        transliterationMap.put('Я', "Ya");

        return transliterationMap;
    }

    public Map<Character, String> UkrainianTransliterationMap(@NonNull Map<Character, String> transliterationMap) {

        transliterationMap.put('а', "a");
        transliterationMap.put('б', "b");
        transliterationMap.put('в', "v");
        transliterationMap.put('г', "gh");
        transliterationMap.put('ґ', "g");
        transliterationMap.put('д', "d");
        transliterationMap.put('е', "e");
        transliterationMap.put('є', "ye");
        transliterationMap.put('ж', "zh");
        transliterationMap.put('з', "z");
        transliterationMap.put('и', "y`");
        transliterationMap.put('і', "i");
        transliterationMap.put('ї', "yi");
        transliterationMap.put('й', "j");
        transliterationMap.put('к', "k");
        transliterationMap.put('л', "l");
        transliterationMap.put('м', "m");
        transliterationMap.put('н', "n");
        transliterationMap.put('о', "o");
        transliterationMap.put('п', "p");
        transliterationMap.put('р', "r");
        transliterationMap.put('с', "s");
        transliterationMap.put('т', "t");
        transliterationMap.put('у', "u");
        transliterationMap.put('ф', "f");
        transliterationMap.put('х', "x");
        transliterationMap.put('ц', "cz");
        transliterationMap.put('ч', "ch");
        transliterationMap.put('ш', "sh");
        transliterationMap.put('щ', "shh");
        transliterationMap.put('ь', "`");
        transliterationMap.put('ю', "yu");
        transliterationMap.put('я', "ya");

        transliterationMap.put('А', "A");
        transliterationMap.put('Б', "B");
        transliterationMap.put('В', "V");
        transliterationMap.put('Г', "Gh");
        transliterationMap.put('Ґ', "G");
        transliterationMap.put('Д', "D");
        transliterationMap.put('Е', "E");
        transliterationMap.put('Є', "Ye");
        transliterationMap.put('Ж', "Zh");
        transliterationMap.put('З', "Z");
        transliterationMap.put('И', "Y`");
        transliterationMap.put('І', "I");
        transliterationMap.put('Ї', "Yi");
        transliterationMap.put('Й', "J");
        transliterationMap.put('К', "K");
        transliterationMap.put('Л', "L");
        transliterationMap.put('М', "M");
        transliterationMap.put('Н', "N");
        transliterationMap.put('О', "O");
        transliterationMap.put('П', "P");
        transliterationMap.put('Р', "R");
        transliterationMap.put('С', "S");
        transliterationMap.put('Т', "T");
        transliterationMap.put('У', "U");
        transliterationMap.put('Ф', "F");
        transliterationMap.put('Х', "X");
        transliterationMap.put('Ц', "Cz");
        transliterationMap.put('Ч', "Ch");
        transliterationMap.put('Ш', "Sh");
        transliterationMap.put('Щ', "Shh");
        transliterationMap.put('Ь', "`");
        transliterationMap.put('Ю', "Yu");
        transliterationMap.put('Я', "Ya");

        return transliterationMap;
    }

    public Map<Character, String> BulgarianTransliterationMap(@NonNull Map<Character, String> transliterationMap) {

        transliterationMap.put('а', "a");
        transliterationMap.put('б', "b");
        transliterationMap.put('в', "v");
        transliterationMap.put('г', "g");
        transliterationMap.put('д', "d");
        transliterationMap.put('е', "e");
        transliterationMap.put('ж', "zh");
        transliterationMap.put('з', "z");
        transliterationMap.put('и', "i");
        transliterationMap.put('й', "j");
        transliterationMap.put('к', "k");
        transliterationMap.put('л', "l");
        transliterationMap.put('м', "m");
        transliterationMap.put('н', "n");
        transliterationMap.put('о', "o");
        transliterationMap.put('п', "p");
        transliterationMap.put('р', "r");
        transliterationMap.put('с', "s");
        transliterationMap.put('т', "t");
        transliterationMap.put('у', "u");
        transliterationMap.put('ф', "f");
        transliterationMap.put('х', "x");
        transliterationMap.put('ц', "cz");
        transliterationMap.put('ч', "ch");
        transliterationMap.put('ш', "sh");
        transliterationMap.put('щ', "sth");
        transliterationMap.put('ъ', "a`");
        transliterationMap.put('ь', "`");
        transliterationMap.put('ю', "yu");
        transliterationMap.put('я', "ya");

        transliterationMap.put('А', "A");
        transliterationMap.put('Б', "B");
        transliterationMap.put('В', "V");
        transliterationMap.put('Г', "G");
        transliterationMap.put('Д', "D");
        transliterationMap.put('Е', "E");
        transliterationMap.put('Ж', "Zh");
        transliterationMap.put('З', "Z");
        transliterationMap.put('И', "I");
        transliterationMap.put('Й', "J");
        transliterationMap.put('К', "K");
        transliterationMap.put('Л', "L");
        transliterationMap.put('М', "M");
        transliterationMap.put('Н', "N");
        transliterationMap.put('О', "O");
        transliterationMap.put('П', "P");
        transliterationMap.put('Р', "R");
        transliterationMap.put('С', "S");
        transliterationMap.put('Т', "T");
        transliterationMap.put('У', "U");
        transliterationMap.put('Ф', "F");
        transliterationMap.put('Х', "X");
        transliterationMap.put('Ц', "Cz");
        transliterationMap.put('Ч', "Ch");
        transliterationMap.put('Ш', "Sh");
        transliterationMap.put('Щ', "Sth");
        transliterationMap.put('Ъ', "A`");
        transliterationMap.put('Ь', "`");
        transliterationMap.put('Ю', "Yu");
        transliterationMap.put('Я', "Ya");

        return transliterationMap;
    }

    public Map<Character, String> MacedonianTransliterationMap(@NonNull Map<Character, String> transliterationMap) {

        transliterationMap.put('а', "a");
        transliterationMap.put('б', "b");
        transliterationMap.put('в', "v");
        transliterationMap.put('г', "g");
        transliterationMap.put('д', "d");
        transliterationMap.put('ѓ', "g`");
        transliterationMap.put('е', "e");
        transliterationMap.put('ж', "zh");
        transliterationMap.put('з', "z");
        transliterationMap.put('ѕ', "z`");
        transliterationMap.put('и', "i");
        transliterationMap.put('ј', "j");
        transliterationMap.put('к', "k");
        transliterationMap.put('л', "l");
        transliterationMap.put('љ', "l`");
        transliterationMap.put('м', "m");
        transliterationMap.put('н', "n");
        transliterationMap.put('њ', "n`");
        transliterationMap.put('о', "o");
        transliterationMap.put('п', "p");
        transliterationMap.put('р', "r");
        transliterationMap.put('с', "s");
        transliterationMap.put('т', "t");
        transliterationMap.put('ќ', "k`");
        transliterationMap.put('у', "u");
        transliterationMap.put('ф', "f");
        transliterationMap.put('х', "x");
        transliterationMap.put('ц', "cz");
        transliterationMap.put('ч', "ch");
        transliterationMap.put('џ', "dh");
        transliterationMap.put('ш', "sh");

        transliterationMap.put('А', "A");
        transliterationMap.put('Б', "B");
        transliterationMap.put('В', "V");
        transliterationMap.put('Г', "G");
        transliterationMap.put('Д', "D");
        transliterationMap.put('Ѓ', "G`");
        transliterationMap.put('Е', "E");
        transliterationMap.put('Ё', "Yo");
        transliterationMap.put('Ж', "Zh");
        transliterationMap.put('З', "Z");
        transliterationMap.put('Ѕ', "Z`");
        transliterationMap.put('И', "I");
        transliterationMap.put('Ј', "J");
        transliterationMap.put('К', "K");
        transliterationMap.put('Л', "L");
        transliterationMap.put('Љ', "L`");
        transliterationMap.put('М', "M");
        transliterationMap.put('Н', "N");
        transliterationMap.put('Њ', "N`");
        transliterationMap.put('О', "O");
        transliterationMap.put('П', "P");
        transliterationMap.put('Р', "R");
        transliterationMap.put('С', "S");
        transliterationMap.put('Т', "T");
        transliterationMap.put('Ќ', "K`");
        transliterationMap.put('У', "U");
        transliterationMap.put('Ф', "F");
        transliterationMap.put('Х', "X");
        transliterationMap.put('Ц', "Cz");
        transliterationMap.put('Ч', "Ch");
        transliterationMap.put('Џ', "Dh");
        transliterationMap.put('Ш', "Sh");

        return transliterationMap;
    }

    /**
     * Транслитерирует исходный текст
     * @param text исходный текст
     * @return транслитерированный текст
     */
    public String transliterate(String text) {
        if (text == null || text.isEmpty()) return "";

        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (transliterationMap.containsKey(c)) {
                change_CZ_to_C(result, c);
                result.append(transliterationMap.get(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * Проверяет и при выполнении условия заменяет транслитерированные символы 'cz' на 'c'.
     * @param text Транслитерированный текст.
     * @param currentChar Символ следующий за 'Ц'/'ц'.
     */
    private void change_CZ_to_C(@NonNull StringBuilder text, char currentChar) {
        if (text.length() < 2) return;

        String lastTwo = text.substring(text.length() - 2);
        if (!lastTwo.equalsIgnoreCase("cz")) return;

        String translit = transliterationMap.get(currentChar);
        if (translit == null) return;

        boolean isVowelAfterCz = switch (translit.toLowerCase()) {
            case "i", "i`", "e", "yo", "y`", "yu", "ya",
                 "yi", "ye", "yh", "j" -> true;
            default -> false;
        };

        if (isVowelAfterCz) {
            char replacement = Character.isUpperCase(lastTwo.charAt(0)) ? 'C' : 'c';
            text.replace(text.length() - 2, text.length(), String.valueOf(replacement));
        }
    }

    /**
     * Определяет, нужно ли расширять 'c' до 'cz'
     * Правило: 'c' перед гласной могло быть 'cz' в оригинале
     */
    private boolean shouldExpand_C_to_CZ(int currentIndex, @NonNull String transliteratedText) {
        if (currentIndex >= transliteratedText.length() - 1) return false;

        char nextChar = transliteratedText.charAt(currentIndex + 1);
        String nextCharStr = String.valueOf(nextChar);

        String[] vowels = {"i", "i`", "e", "yo", "y`", "yu", "ya", "yi", "ye", "yh", "j"};
        for (String vowel : vowels) {
            if (nextCharStr.equalsIgnoreCase(vowel.substring(0, 1))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Обратная транслитерация: из латиницы в кириллицу
     * @param transliteratedText транслитерированный текст на латинице
     * @return текст на оригинальном языке (кириллице)
     */
    public String retransliterate(String transliteratedText) {
        if (transliteratedText == null || transliteratedText.isEmpty()) return "";

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < transliteratedText.length()) {
            boolean found = false;

            // Максимальная длина латинской последовательности для одного символа кириллицы
            int maxLength = Math.min(3, transliteratedText.length() - i);

            for (int len = maxLength; len >= 1; len--) {
                String candidate = transliteratedText.substring(i, i + len);

                if (reverseTransliterationMap.containsKey(candidate)) {
                    char originalChar = reverseTransliterationMap.get(candidate);

                    if (candidate.equals("`") || candidate.equals("``")) {
                        originalChar = determineSoftSign(result.toString(), candidate);
                    }
                    result.append(originalChar);
                    i += len;
                    found = true;
                    break;
                } else if (len == 1 && (candidate.equalsIgnoreCase("c")) &&
                        shouldExpand_C_to_CZ(i, transliteratedText)) {
                    char originalChar = reverseTransliterationMap.get("cz");
                    result.append(originalChar);
                    i++;
                    found = true;
                    break;
                }
            }

            // Если не нашли соответствия, оставляем символ как есть
            if (!found) {
                result.append(transliteratedText.charAt(i));
                i++;
            }
        }

//        log.debug("Retransliterated '{}' to '{}'", transliteratedText, result);
        return result.toString();
    }

    /**
     * Определяет, какую букву мягкого знака использовать
     * Правило: если это НЕ начало слова → строчная 'ь', иначе заглавная 'Ь'
     */
    private char determineSoftSign(String currentResult, @NonNull String candidate) {
        char baseChar = candidate.equals("``") ? 'ъ' : 'ь';

        if (isStartOfWord(currentResult)) {
            return Character.toUpperCase(baseChar); // 'Ъ' или 'Ь'
        } else {
            return Character.toLowerCase(baseChar); // 'ъ' или 'ь'
        }
    }

    /**
     * Проверяет, находится ли текущая позиция в начале слова
     * Начало слова = либо строка пустая, либо предыдущий символ не буква
     */
    private boolean isStartOfWord(@NonNull String currentResult) {
        if (currentResult.isEmpty()) return true; // Это первый символ в результате

        // Начало слова, если предыдущий символ:
        // 1. Пробел
        // 2. Знак препинания
        // 3. Специальный символ
        char lastChar = currentResult.charAt(currentResult.length() - 1);
        return !Character.isDigit(lastChar) && !Character.isLetter(lastChar) && !isCyrillicLetter(lastChar);
    }

    /**
     * Проверяет, является ли символ кириллической буквой
     */
    private boolean isCyrillicLetter(char candidate) {
        return Character.UnicodeBlock.of(candidate) == Character.UnicodeBlock.CYRILLIC;
    }
}