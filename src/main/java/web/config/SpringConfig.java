package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("web")
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
                                  // WebMvcConfigurer для переопределения метода configureViewResolvers

    // Контекст автоматически подтягивает Spring
    private final ApplicationContext applicationContext;

    // Конструктор, чтобы подтянуть ApplicationContext
    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    // Здесь я так понимаю, Spring сам создаёт объект Environment,
    // и кладёт в него данные из нашего файла db.properties
    @Autowired
    private Environment env;

    // В объект DataSource мы закидываем настройки ДБ,
    // чтобы потом передать их в SessionFactory или EntityManagerFactory
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    // в объект Properties кладём настройки Hibernate
    Properties getProperties() {
        Properties p = new Properties();
        p.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        p.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        p.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        return p;
    }


// HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE
//        HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE
    /*
    // здесь создаём LocalSessionFactoryBean - т.е. SessionFactory
    //  - у него с момощью методов:
    //    - .setDataSource              (настройки БД сверху)
    //    - и .setHibernateProperties   (настройки Hibernate из файла)
    //    добавляем все необходимые настройки БД и Hibernate
    //
    // Далее - указываем классы бинов
    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        // создаём LocalSessionFactoryBean - т.е. SessionFactory
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        // в LocalSessionFactoryBean кладём настройки БД
        //  - вызываем метод сверху, получаем DataSource с настрйоками БД
        factoryBean.setDataSource(getDataSource());

        // применяем настройки Hibernate к LocalSessionFactoryBean
        factoryBean.setHibernateProperties(getProperties());

        // указываем аннотируемый класс(ы), т.е. класы, которые будут
        // являться сущностями (настройка Hibernate)
        factoryBean.setAnnotatedClasses(User.class);    // сущности hibernate

        return factoryBean;
    }

    // бин для автоматического открытия и закрытия транзакции
    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }
    */
// HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE
//        HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE    HIBERNATE


// JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA
//    JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA
    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean enManFacBean = new LocalContainerEntityManagerFactoryBean();
        enManFacBean.setDataSource(getDataSource());
        enManFacBean.setPackagesToScan("web");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        enManFacBean.setJpaVendorAdapter(vendorAdapter);

        enManFacBean.setJpaProperties(getProperties());

        return enManFacBean;
    }

//    @Bean
//    public EntityManager getEntityManager() {
//        EntityManagerHolder holder = TransactionSynchronizationManager.getResource(getEntityManagerFactory());
//
//        return null;
//    }

    @Bean
    public PlatformTransactionManager TransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
// JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA
//    JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA  JPA


// THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF
//       THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");                         // это добавил kda, для русского текста
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");                         // это добавил kda, для русского текста
        resolver.setContentType("text/html; charset=UTF-8");            // это добавил kda, для русского текста
        registry.viewResolver(resolver);
    }
// THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF
//       THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF    THYMELEAF

}
