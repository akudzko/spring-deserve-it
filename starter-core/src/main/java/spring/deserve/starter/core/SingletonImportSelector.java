package spring.deserve.starter.core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class SingletonImportSelector implements ImportSelector {
    private final Environment environment;

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // Получаем пакеты из application.yaml
        String packages = environment.getProperty("importsingleton");
        if (packages == null || packages.isEmpty()) {
            return new String[0];
        }

        // Разделяем пакеты по запятым и создаем список бинов для импорта
        String[]     packageArray = packages.split(",");
        List<String> beanClasses  = new ArrayList<>();

        // Настраиваем сканер для поиска компонентов с аннотацией @Singleton
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Singleton.class));

        for (String pkg : packageArray) {
            pkg = pkg.trim();
            // Сканируем пакет на наличие классов с аннотацией @Singleton
            Set<BeanDefinition> candidates = scanner.findCandidateComponents(pkg);
            for (BeanDefinition candidate : candidates) {
                beanClasses.add(candidate.getBeanClassName());
            }
        }

        // Преобразуем список классов в массив и возвращаем для регистрации в контексте
        return beanClasses.toArray(new String[0]);
    }

}
