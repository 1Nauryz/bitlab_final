package com.example.demo.service;


import com.example.demo.dto.CarDTO;
import com.example.demo.mapper.CarMapper;
import com.example.demo.model.CarModel;
import com.example.demo.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    private final CarMapper carMapper;

    public List<CarDTO> getCars() {
        return carMapper.toDtoList(carRepository.findAll());
    }
    public CarDTO getCar(Long id){
        return carMapper.toDto(carRepository.findById(id).orElse(null));
    }
    public List<CarModel> searchCarModel(String key){
        List<CarModel> carModelList = carRepository.searchCarModel("%"+ key +"%");
        return carModelList;
    }

    public CarModel findCar(Long id){
        CarModel car = carRepository.findById(id).orElse(null);
        return car;
    }

    public CarModel addIm(CarModel carModel){
        return carRepository.save(carModel);
    }

    public CarDTO addCar(CarDTO car){
        return carMapper.toDto(carRepository.save(carMapper.toModel(car)));
    }
    public CarDTO editCar(CarDTO car){
        return carMapper.toDto(carRepository.save(carMapper.toModel(car)));
    }

    public CarModel editCar(CarModel carModel){
        return carRepository.save(carModel);
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }

}
