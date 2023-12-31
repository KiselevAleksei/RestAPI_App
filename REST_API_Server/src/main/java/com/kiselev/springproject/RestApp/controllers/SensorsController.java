package com.kiselev.springproject.RestApp.controllers;

import com.kiselev.springproject.RestApp.Util.MeasurementErrorResponce;
import com.kiselev.springproject.RestApp.Util.MeasurementException;
import com.kiselev.springproject.RestApp.Util.SensorValidator;
import com.kiselev.springproject.RestApp.dto.SensorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.kiselev.springproject.RestApp.models.Sensor;
import com.kiselev.springproject.RestApp.services.SensorService;

import javax.validation.Valid;

import static com.kiselev.springproject.RestApp.Util.ErrorsUtil.returnErrorsToClient;

@RestController // @Controller + @ResponseBody
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        Sensor sensorToAdd = convertToSensor(sensorDTO);
        sensorValidator.validate(sensorToAdd, bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        sensorService.register(sensorToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponce> handleException(MeasurementException e){
        MeasurementErrorResponce response = new MeasurementErrorResponce(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
