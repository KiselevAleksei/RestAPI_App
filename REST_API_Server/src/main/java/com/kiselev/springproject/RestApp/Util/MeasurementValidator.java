package com.kiselev.springproject.RestApp.Util;

import com.kiselev.springproject.RestApp.models.Measurement;
import com.kiselev.springproject.RestApp.models.Sensor;
import com.kiselev.springproject.RestApp.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Measurement measurement = (Measurement) o;

        if(measurement.getSensor() == null) {
            return;
        }
        if(sensorService.findByName(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "Sensor is not registered!");

    }
}
