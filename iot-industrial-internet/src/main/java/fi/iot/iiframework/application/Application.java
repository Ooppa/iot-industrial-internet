/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.application;

import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;
import fi.iot.iiframework.errors.SysError;
import fi.iot.iiframework.domain.InformationSourceConfiguration;
import fi.iot.iiframework.source.InformationSourceManagerImpl;
import fi.iot.iiframework.source.InformationSourceType;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("fi.iot.iiframework")
public class Application {

    public static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws JAXBException, MalformedURLException, IOException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        logger.log(Level.CONFIG, "Following beans found:\t{0}", Arrays.toString(ctx.getBeanDefinitionNames()));

        initTestData(ctx);
    }

    private static void initTestData(ApplicationContext ctx) throws JAXBException, IOException {
        InformationSourceManagerImpl infSourceManager = ctx.getBean(InformationSourceManagerImpl.class);

        InformationSourceConfiguration config = new InformationSourceConfiguration();
        config.setName("Example Config");
        config.setType(InformationSourceType.XML);
        config.setUrl("http://t-teesalmi.users.cs.helsinki.fi/MafiaTools/source.xml");
        config.setActive(true);
        config.setReadFrequency(100 * 1000);
        infSourceManager.createSource(config);

        SysError e = new SysError(ErrorType.UNKNOWN_ERROR, ErrorSeverity.NOTIFICATION, "This is a test error");
        ErrorLogger.log(e);

        SysError e2 = new SysError(ErrorType.CONFLICT_ERROR, ErrorSeverity.HIGH, "This is an another test error");
        ErrorLogger.log(e2);

        SysError e3 = new SysError(ErrorType.TIMEOUT_ERROR, ErrorSeverity.FATAL, "This is an another test error");
        ErrorLogger.log(e3);

        SysError e4 = new SysError(ErrorType.BAD_REQUEST, ErrorSeverity.NOTIFICATION, "This is an another test error");
        ErrorLogger.log(e4);

        /*        
        DeviceConfigurationService deviceConfService = ctx.getBean(DeviceConfigurationService.class);
        DeviceService seviceServiceImpl = ctx.getBean(DeviceService.class);
        
        Device device = seviceServiceImpl.getAll().get(0);
        
        DeviceConfiguration conf = new DeviceConfiguration();
        conf.setDevice(device);
        conf.setActive(false);
        
        deviceConfService.save(conf);
        */

    }
}
