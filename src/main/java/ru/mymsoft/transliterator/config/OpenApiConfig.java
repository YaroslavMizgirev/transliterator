package ru.mymsoft.transliterator.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mymsoft.transliterator.service.TransliterationLanguage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Configuration
public class OpenApiConfig {
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transliterator API")
                        .description("""
                            REST API для транслитерации текста с кириллицы на латиницу

                            ### Поддерживаемые языки:
                            1. **RUSSIAN** - Русский язык
                            2. **BELORUSSIAN** - Белорусский язык
                            3. **UKRAINIAN** - Украинский язык
                            4. **BULGARIAN** - Болгарский язык
                            5. **MACEDONIAN** - Македонский язык
    
                            ### Особенности:
                            - Автоматическое определение языка по умолчанию: RUSSIAN
                            - Поддержка спецсимволов и пунктуации
                        """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("mymsoft")
                                .email("yaroslav@mizgirev.ru")
                                .url("https://mymsoft.ru"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSchemas("TransliterationLanguage", createLanguageScheme()))
                .servers(List.of(
                        new Server()
                                .url(contextPath)
                                .description("Основной сервер"),
                        new Server()
                                .url("http://localhost:8080" + contextPath)
                                .description("Локальный сервер")
                ));
    }

    private Schema<String> createLanguageScheme() {
        Schema<String> schema = new Schema<String>();
        schema.setType("string");
        schema.setEnum(Arrays
                .stream(TransliterationLanguage.values())
                .map((Function<? super TransliterationLanguage, String>) Enum::toString)
                .toList());
        schema.setDescription("""
            Язык для транслитерации.
            
            Выберите один из поддерживаемых языков:
            - RUSSIAN: Русский язык
            - BELORUSSIAN: Белорусский язык
            - UKRAINIAN: Украинский язык
            - BULGARIAN: Болгарский язык
            - MACEDONIAN: Македонский язык
            """);
        schema.setExample("RUSSIAN");
        return schema;
    }
}