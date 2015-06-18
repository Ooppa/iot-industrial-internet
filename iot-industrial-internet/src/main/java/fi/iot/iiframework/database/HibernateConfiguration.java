/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.xml.ws.Action;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_C3P0_MIN_SIZE = "hibernate.c3p0.min_size";
    private static final String PROPERTY_NAME_C3P0_MAX_SIZE = "hibernate.c3p0.max_size";
    private static final String PROPERTY_NAME_C3P0_TIMEOUT = "hibernate.c3p0.timeout";
    private static final String PROPERTY_NAME_C3P0_MAX_STATEMENTS = "hibernate.c3p0.max_statements";
    private static final String PROPERTY_NAME_C3P0_IDLE_TEST_PERIOD = "hibernate.c3p0.idle_test_period";

    @Resource
    private Environment env;

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUser(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        dataSource.setMinPoolSize(Integer.parseInt(env.getRequiredProperty(PROPERTY_NAME_C3P0_MIN_SIZE)));
        dataSource.setMaxPoolSize(Integer.parseInt(env.getRequiredProperty(PROPERTY_NAME_C3P0_MAX_SIZE)));
        dataSource.setCheckoutTimeout(Integer.parseInt(env.getRequiredProperty(PROPERTY_NAME_C3P0_TIMEOUT)));
        dataSource.setMaxStatements(Integer.parseInt(env.getRequiredProperty(PROPERTY_NAME_C3P0_MAX_STATEMENTS)));
        dataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getRequiredProperty(PROPERTY_NAME_C3P0_IDLE_TEST_PERIOD)));

        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
        sessionFactoryBean.setHibernateProperties(hibProperties());

        return sessionFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
        properties.put("show_sql", "true");
        properties.put("cache.provider_class", "org.hibernate.cache.internal.NoCacheProvider");
        return properties;
    }

    @Autowired
    @Bean
    @Primary
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory
        );

        return transactionManager;
    }

}
