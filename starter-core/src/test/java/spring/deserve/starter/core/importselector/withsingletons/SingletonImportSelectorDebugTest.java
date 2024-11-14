package spring.deserve.starter.core.importselector.withsingletons;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import spring.deserve.starter.core.Singleton;
import spring.deserve.starter.core.SingletonImportSelector;

import static org.mockito.Answers.RETURNS_DEEP_STUBS;

@SpringBootTest(classes = {SingletonImportSelectorDebugTest.SingletonImportSelectorConfiguration.class})
public class SingletonImportSelectorDebugTest {
    @InjectMocks                           SingletonImportSelector singletonImportSelector;
    @MockBean(answer = RETURNS_DEEP_STUBS) Environment             environment;
    @MockBean(answer = RETURNS_DEEP_STUBS) AnnotationMetadata      annotationMetadata;

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
    public static class SingletonImportSelectorConfiguration {}

    @Test
    void debugTest(){

    }

}
