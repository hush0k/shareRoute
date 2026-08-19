package com.hush0k.shareRoute.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Дефолтная пагинация для всех эндпоинтов с Pageable: size=20, без сортировки по умолчанию
 * (единой для всех модулей "правильной" колонки для сортировки не существует —
 * ранее здесь стоял Sort.by("firstName"), который ломал эндпоинты модулей
 * без такого поля, например gamefication).
 */
@Configuration
public class PageableConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(PageRequest.of(0, 20));
        resolver.setMaxPageSize(100);
        resolvers.add(resolver);
    }
}
