package com.hush0k.shareRoute.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasenames(
                "classpath:messages/user-messages",
                "classpath:messages/followers-messages",
                "classpath:messages/auth-messages",
                "classpath:messages/gamefication-messages",
                "classpath:messages/geography-messages",
                "classpath:messages/post-messages"
                // сюда будешь добавлять по мере появления модулей:
                // "classpath:messages/route-messages"
        );
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(java.util.Locale.ENGLISH); // fallback, если Accept-Language не kk/en
        source.setCacheSeconds(3600); // в проде можно кэшировать, в деве можно поставить 0 для горячей перезагрузки
        return source;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}