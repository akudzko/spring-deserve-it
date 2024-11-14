package spring.deserve.starter.core.importselector.spring.withsingletons;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.deserve.starter.core.Singleton;
import spring.deserve.starter.core.SingletonImportSelector;

import static org.assertj.core.api.Assertions.assertThat;

//TODO подумайте в чём преимущество такого теста через ApplicationContextRunner?
// 1. Допишите тест проверяющий что не нашлись лишние классы
// 2. Допишите тест для фиксации поведения в случае отсутствия property для basepackage от которого нужно искать
//      - например проверить что в этом случае контекст не поднимается с ошибкой
class SingletonImportSelectorSpringTest {
    @Singleton
    public static class S1 {

    }

    @Singleton
    public static class S2 {

    }

    public static class S3 {

    }

    @Configuration
    @Import(SingletonImportSelector.class)
    public static class SingletonImportSelectorTestConfiguration {
    }

    //TODO допишите тест основанный на ApplicationContextRunner
    @Test
    void should_return_singletons_class_names() {
        new ApplicationContextRunner()
                .withUserConfiguration(SingletonImportSelectorTestConfiguration.class)
                .withPropertyValues("importsingleton=spring.deserve.starter.core.importselector.spring.withsingletons")
                .run(context -> assertThat(context)
                        .doesNotHaveBean(S3.class)
                        .hasBean(
                                "spring.deserve.starter.core.importselector.spring.withsingletons.SingletonImportSelectorSpringTest$S1")
                        .hasBean(
                                "spring.deserve.starter.core.importselector.spring.withsingletons.SingletonImportSelectorSpringTest$S2"));
    }

    @Test
    void should_not_contain_errors_on_build_context_if_property_is_not_set() {
        new ApplicationContextRunner()
                .withUserConfiguration(SingletonImportSelectorTestConfiguration.class)
                .run(context -> {
                    assertThat(context)
                            .hasNotFailed()
                            .doesNotHaveBean(S1.class)
                            .doesNotHaveBean(S2.class);
                });
    }
}