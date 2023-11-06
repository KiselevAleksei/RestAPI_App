package com.kiselev.springproject.RestApp.dto;

import com.kiselev.springproject.RestApp.models.Sensor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MeasurementsDTO {
    @NotNull
    @Min(-50)
    @Max(70)
    private Double value;
    @NotNull
    private Boolean raining;
    @NotNull
    private Sensor sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

     public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
