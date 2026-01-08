-- Создание расширения для UUID (если нужно)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Таблица статистики будет создана автоматически через Hibernate
-- Можно добавить индексы для улучшения производительности

CREATE INDEX IF NOT EXISTS idx_transliteration_stats_language
    ON transliteration_statistics (source_language);

CREATE INDEX IF NOT EXISTS idx_transliteration_stats_created_at
    ON transliteration_statistics (created_at DESC);

CREATE INDEX IF NOT EXISTS idx_transliteration_stats_text_length
    ON transliteration_statistics (text_length);