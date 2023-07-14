package com.example.demo.mapper;

import com.example.demo.dto.CarDTO;
import com.example.demo.model.CarModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO toDto(CarModel carModel);

    CarModel toModel(CarDTO carDTO);

    List<CarDTO> toDtoList(List<CarModel> carModelList);

    List<CarModel> toModelList(List<CarDTO> carDTOList);
}
