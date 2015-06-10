/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sensor")
@Data
@EqualsAndHashCode(exclude = {"readouts"})
@ToString(exclude = {"readouts"})
public class Sensor implements Serializable {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;

    @XmlAttribute(name = "id")
    protected String sensorId;

    @JsonIgnore
    @XmlElement(name = "readout")
    @XmlElementWrapper(name = "readouts")
    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
    protected Set<Readout> readouts;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source", nullable = false, updatable = false)
    @Cascade({CascadeType.SAVE_UPDATE})
    protected InformationSourceConfiguration source;
    
    @JsonIgnore
    @OneToOne(targetEntity = SensorConfiguration.class, fetch = FetchType.EAGER)
    protected SensorConfiguration sensorConfiguration;
    
    protected String name;

    public Sensor() {
    }

    public Sensor(String id) {
        this.sensorId = id;
    }
}
