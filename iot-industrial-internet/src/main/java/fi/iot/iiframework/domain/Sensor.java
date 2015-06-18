/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "sensors")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sensor")
@Data
@ToString(exclude = {"readouts", "source"})
public class Sensor implements Serializable {

    @Id
    @GeneratedValue
    protected Long id;

    @XmlAttribute(name = "name")
    protected String name;

    @JsonIgnore
    @XmlElement(name = "readout")
    @XmlElementWrapper(name = "readouts")
    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Set<Readout> readouts = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source", nullable = false, updatable = false)
    protected InformationSource source;

    @XmlAttribute
    protected String quantity;

    @XmlAttribute
    protected String unit;

    protected boolean active;

    protected Double thresholdMax;

    protected Double thresholdMin;

    public Sensor() {
        active = true;
    }

    public Sensor(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.source == null ? this.source.id : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sensor other = (Sensor) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return (this.source == null ? other.source == null : this.source.id.equals(other.source.id));
    }

}
