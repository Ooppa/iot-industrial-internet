/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.iot.iiframework.errors;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Contains information about an error in the system
 */
@Entity
@Table(name = "Errors")

public class SysError implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

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
     * @param location Location of where the error occurred
     * @param severity ErrorSeverity of the error
     */
    public SysError(ErrorType type, ErrorSeverity severity, String desc, String location) {
        this.type = type;
        this.description = desc;
        this.location = location;
        this.severity = severity;
    }

    public SysError() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }
    
    public String getName(){
        return this.type.getName();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ErrorSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ErrorSeverity severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "\tid: "+ this.id +
                "\ttype: " + this.type.toString() +
                "\ttime: " + this.time.toString() +
                "\tdesc: " + this.description +
                "\tlocation: " + this.location +
                "\tseverity: " + this.severity.toString();
    }

}
