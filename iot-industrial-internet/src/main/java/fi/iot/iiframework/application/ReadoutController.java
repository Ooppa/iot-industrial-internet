/*
 * IoT - Industrial Internet Framework
 * Apache License Version 2.0, January 2004
 * Released as a part of Helsinki University
 * Software Engineering Lab in summer 2015
 */
package fi.iot.iiframework.application;

import fi.iot.iiframework.dataobject.DataSourceObject;
import fi.iot.iiframework.dataobject.Device;
import fi.iot.iiframework.dataobject.Sensor;
import fi.iot.iiframework.services.dataobject.DataSourceObjectService;
import fi.iot.iiframework.views.ViewParams;
import fi.iot.iiframework.views.ViewUtils;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("readouts")
public class ReadoutController {
    
    @Autowired
    private DataSourceObjectService service;

    @RequestMapping("*")
    public String index(Model model) {
        return "redirect:/sources/example/view";
    }

    @RequestMapping("/{deviceid}/{sensorid}/view")
    public String view(Model model, @PathVariable String deviceid, @PathVariable String sensorid) {
        ViewParams params = new ViewParams("List of all Readouts", "---");

        params.setNavtype("loggedin");
        params.setContenttype("list_readouts");

        ViewUtils.addViewParamsToModel(model, params);

        List<DataSourceObject> datasources = service.getAll();

        Set<Device> devices = datasources.get(0).getDevices();
        
        // Purkkapallo
        for (Device device : devices) {
            Set<Sensor> sensors = device.getSensors();
            
            for (Sensor sensor : sensors) {
                if(sensor.getId().equals(sensorid)){
                    model.addAttribute("sensor", sensor);
                }
            }
            
        }
        
        

        return "default";
    }
}
