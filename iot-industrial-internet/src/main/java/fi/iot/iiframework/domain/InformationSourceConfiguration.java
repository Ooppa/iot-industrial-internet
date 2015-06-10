/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.iot.iiframework.source.InformationSourceType;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 *
 * Configures a information source.
 */
@Entity
@Table(name = "informationsources")
@Data
@EqualsAndHashCode(exclude = {"id", "readFrequency", "sensors"})
@ToString(exclude = {"sensors"})
public class InformationSourceConfiguration implements Serializable, Validatable {

    /**
     * Information source id.
     */
    @Id
    @GeneratedValue
    protected String id;
    /**
     * Information source name.
     */
    protected String name;
    /**
     * Information source type XML/MBus/JSon/etc.
     */
    protected InformationSourceType type;
    /**
     * URL read from.
     */
    protected String url;
    /**
     * Read in intervals
     * TODO: handle this intelligently.
     */
    protected boolean active = true;
    /**
     * How often read (in seconds).
     */
    protected int readFrequency;
    
    @JsonIgnore
    @OneToMany(mappedBy = "source", fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
    protected Set<Sensor> sensors; 
    
    @Override
    public boolean isValid() {
        try {
            new URL(url);
        } catch (MalformedURLException ex) {
            return false;
        }

        return readFrequency > 0;
    }

}
