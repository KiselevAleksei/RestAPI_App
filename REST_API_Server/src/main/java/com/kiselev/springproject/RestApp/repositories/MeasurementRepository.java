package com.kiselev.springproject.RestApp.repositories;

import com.kiselev.springproject.RestApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

}
