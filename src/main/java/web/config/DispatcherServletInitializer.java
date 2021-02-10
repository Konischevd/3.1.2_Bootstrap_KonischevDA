package web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    // дать понять "web.xml" (который у нас предствален в виде данного класса) - где находится конфигурация Spring
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    // URL на котором базируется приложение
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}