/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.Unmarshaller;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "readouts",
        uniqueConstraints
        = @UniqueConstraint(columnNames = {"readout_time", "readout_value", "sensor"}))
@ToString(exclude = {"sensor"})
public class Readout implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "readout_time")
    protected long time;

    @NotNull
    @Column(name = "readout_value")
    protected double value;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor")
    protected Sensor sensor;

    protected ReadoutFlag flag;

    public Readout() {
        this.flag = ReadoutFlag.EMPTY;
    }

    public Readout(long time, double value, Sensor sensor) {
        this.time = time;
        this.value = value;
        this.sensor = sensor;
        this.flag = ReadoutFlag.EMPTY;
    }

    /**
     * Returns the time of this Readout as Java Date.
     *
     * @return Date
     */
    @JsonIgnore
    public Date getTimeAsDate() {
        return new Date(time);
    }

    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.sensor = (Sensor) parent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.time ^ (this.time >>> 32));
        hash = 79 * hash + Objects.hashCode(this.sensor != null ? this.sensor : 0);
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
        final Readout other = (Readout) obj;
        if (this.id != null && other.id != null) {
            if (this.id.equals(other.id)) {
                return true;
            }
        }

        if (this.time != other.time) {
            return false;
        }

        if (this.sensor == null && other.sensor == null) {
            return true;
        }

        if (this.sensor == null || other.sensor == null) {
            return false;
        }

        return this.sensor.equals(other.sensor);
    }

}
