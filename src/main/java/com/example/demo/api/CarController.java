package com.example.demo.api;

import com.example.demo.dto.CarDTO;
import com.example.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    @GetMapping()
    public List<CarDTO> getCars(){
        return carService.getCars();
    }
    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable(name = "id")Long id){
        return carService.getCar(id);
    }

    @PostMapping()
    public CarDTO addCar(@RequestBody CarDTO carModel){
        return carService.addCar(carModel);
    }
    @PutMapping
    public CarDTO editCar(@RequestBody CarDTO carModel){
        return carService.editCar(carModel);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable(name = "id")Long id){
        carService.deleteCar(id);
    }

}
