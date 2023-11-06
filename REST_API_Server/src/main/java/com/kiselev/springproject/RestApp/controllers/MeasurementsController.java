package com.kiselev.springproject.RestApp.controllers;

import com.kiselev.springproject.RestApp.Util.MeasurementValidator;
import com.kiselev.springproject.RestApp.dto.MeasurementsDTO;
import com.kiselev.springproject.RestApp.dto.MeasurementsResponse;
import com.kiselev.springproject.RestApp.models.Measurement;
import com.kiselev.springproject.RestApp.services.MeasurementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.stream.Collectors;

import static com.kiselev.springproject.RestApp.Util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementsDTO measurementsDTO, BindingResult bindingResult){
        Measurement measurementToAdd = convertToMeasurement(measurementsDTO);
        measurementValidator.validate(measurementToAdd, bindingResult);
        if (bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);

        measurementService.addMeasurement(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @GetMapping()
    public MeasurementsResponse getMeasurements(){
        // wrap the List of Measurement into object for send
        return new MeasurementsResponse(measurementService.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }
    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementService.findAll().stream().filter(Measurement::getRaining).count();
    }
    private Measurement convertToMeasurement(MeasurementsDTO measurementsDTO){
        return modelMapper.map(measurementsDTO, Measurement.class);
    }

    private MeasurementsDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementsDTO.class);
    }
}
