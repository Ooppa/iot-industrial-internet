/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.application;

import fi.iot.iiframework.domain.InformationSource;
import fi.iot.iiframework.domain.IntervalType;
import fi.iot.iiframework.source.InformationSourceManager;
import fi.iot.iiframework.source.InformationSourceType;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@ComponentScan("fi.iot.iiframework")
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws JAXBException, MalformedURLException, IOException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        logger.log(Level.CONFIG, "Following beans found:\t{0}", Arrays.toString(ctx.getBeanDefinitionNames()));

        initTestData(ctx);
    }

    private static void initTestData(ApplicationContext ctx) throws JAXBException, IOException {
        InformationSourceManager infSourceManager = ctx.getBean(InformationSourceManager.class);

        InformationSource config = new InformationSource();
        config.setName("Example Config");
        config.setType(InformationSourceType.JSON);
        config.setUrl("https://data.sparkfun.com/output/dZ4EVmE8yGCRGx5XRX1W.json?page=1");
        config.setActive(true);
        config.setStartDate(new Date());
        config.setReadInterval(IntervalType.HOURLY);
        infSourceManager.createSource(config);
    }
}
