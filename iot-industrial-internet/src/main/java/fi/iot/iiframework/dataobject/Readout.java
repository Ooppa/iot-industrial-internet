/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.dataobject;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ooppa
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "readout")
public class Readout implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @XmlAttribute
    @NotNull
    protected String time;

    @XmlAttribute
    @NotNull
    protected double value;

    @XmlAttribute
    @NotNull
    protected String unit;

    @XmlAttribute
    @NotNull
    protected String quantity;

    public Readout() {
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getUnit() {
        return unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public double getValue() {
        return value;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the time of this Readout as Java Date.
     *
     * @return
     */
    public Date getTimeAsDate() {
        long timestamp = Long.parseLong(time);
        return new Date(timestamp);
    }

}
