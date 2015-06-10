package fi.iot.iiframework.parsers;

import fi.iot.iiframework.domain.Readout;
import fi.iot.iiframework.domain.Sensor;
import fi.iot.iiframework.errors.ErrorLogger;
import fi.iot.iiframework.errors.ErrorSeverity;
import fi.iot.iiframework.errors.ErrorType;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class XmlParser extends Parser {

    public static List<Sensor> parse(String location) {
        List<Sensor> sensors = null;

        try {
            JAXBContext jaxbc = JAXBContext.newInstance(Sensors.class, Sensor.class, Readout.class);
            Unmarshaller marshaller = jaxbc.createUnmarshaller();
            InputStream input = new URL(location).openStream();
            Sensors read = (Sensors) marshaller.unmarshal(input);
            sensors = read.getSensors();
        } catch (MalformedURLException ex) {
            ErrorLogger.log(ErrorType.BAD_REQUEST, ErrorSeverity.MEDIUM, "Tried to parse XML to Object but URI given could not be formed correctly");
        } catch (JAXBException ex) {
            ErrorLogger.log(ErrorType.PARSE_ERROR, ErrorSeverity.MEDIUM, "Tried to parse XML to Object but failed due to an error: " + ex.toString());
        } catch (IOException ex) {
            ErrorLogger.log(ErrorType.IO_ERROR, ErrorSeverity.MEDIUM, "Tried to parse XML to Object but failed due to an error: " + ex.toString());
        }

        return sensors;
    }

    @XmlRootElement(name = "sensors")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Sensors {

        @XmlElement(name = "sensor")
        private List<Sensor> sensors = null;

        public List<Sensor> getSensors() {
            return sensors;
        }

        public void setSensors(List<Sensor> sensors) {
            this.sensors = sensors;
        }
    }
}
