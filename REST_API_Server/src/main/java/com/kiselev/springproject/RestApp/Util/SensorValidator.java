package com.kiselev.springproject.RestApp.Util;

import com.kiselev.springproject.RestApp.models.Sensor;
import com.kiselev.springproject.RestApp.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;
        if(sensorService.findByName(sensor.getName()).isPresent())
            errors.rejectValue("name", "Sensor already exist!");
    }

}