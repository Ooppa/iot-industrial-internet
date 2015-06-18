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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * Configures a information source.
 */
@Data
@Entity
@Table(name = "informationsources")
@EqualsAndHashCode(of = {"url", "type"})
@ToString(exclude = {"sensors"})
public class InformationSource implements Serializable, Validatable {

    /**
     * Information source id.
     */
    @Id
    @GeneratedValue
    protected Long id;
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
     * Read in intervals.
     */
    protected boolean active = true;
    /**
     * A specified time to start the reading (date/time).
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date startDate;

    /**
     * The interval for reading after a specified date/time (for example
     * weekly).
     */
    protected IntervalType readInterval;
    /**
     * How often read (in seconds).
     */
    protected long otherInterval;
    /**
     * A specified time to end the reading (date/time).
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date endDate;

    @Column(name = "last_readout")
    protected long lastReadout;

    @JsonIgnore

    @OneToMany(mappedBy = "source", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Set<Sensor> sensors = new HashSet<>();

    @Override
    public boolean isValid() {
        try {
            new URL(url);
        } catch (MalformedURLException ex) {
            return false;
        }

        if (readInterval == IntervalType.OTHER) {
            return startDate != null && readInterval != null && otherInterval > 0;
        }

        return startDate != null && readInterval != null;
    }

}
