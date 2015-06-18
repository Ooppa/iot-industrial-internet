/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.iot.iiframework.errors;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * Contains information about an error in the system
 */
@Entity
@Table(name = "Errors")
@Data
public class SysError implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private ErrorType type;

    @Column(name = "errordate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date time;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "severity")
    private ErrorSeverity severity;

    @Column(name = "additionalInformation")
    private String additionalInformation;

    /**
     * Creates a new SysError
     *
     * @param type ErrorType of error
     * @param severity ErrorSeverity of the error
     * @param desc Description of error
     */
    public SysError(ErrorType type, ErrorSeverity severity, String desc) {
        this.type = type;
        this.description = desc;
        this.severity = severity;

        this.time = new Date();
    }

    /**
     * Creates a new SysError
     *
     * @param type ErrorType of error
     * @param desc Description of error
     * @param additionalInformation Additional information attached to this
     * error.
     * @param severity ErrorSeverity of the error
     */
    public SysError(ErrorType type, ErrorSeverity severity, String desc, String additionalInformation) {
        this.type = type;
        this.description = desc;
        this.additionalInformation = additionalInformation;
        this.severity = severity;
    }

    public SysError() {
    }

    public String getName() {
        return this.type.getName();
    }

    @Override
    public String toString() {
        return "\tid: " + this.id
                + "\ttype: " + this.type.toString()
                + "\ttime: " + this.time.toString()
                + "\tdesc: " + this.description
                + "\tlocation: " + this.location
                + "\tseverity: " + this.severity.toString();
    }

}
