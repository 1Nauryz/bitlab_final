package com.example.demo;

import com.example.demo.controller.HomeController;
import com.example.demo.dto.CarDTO;
import com.example.demo.model.CarModel;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CcApplicationTests {

	@Mock
	private CarRepository carRepository;

	@Mock
	private CarService carService;

	@Mock
	private Model model;

	@InjectMocks
	private HomeController homeController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testIndexPage() {
		String key = "searchKey";
		List<CarModel> cars = new ArrayList<>();
		when(carService.searchCarModel(key)).thenReturn(cars);

		String result = homeController.indexPage(model, key);

		assertEquals("index", result);
		verify(model, times(1)).addAttribute(eq("auto"), eq(cars));
	}

	@Test
	public void testCarDetails_WithExistingCar() {
		Long carId = 1L;
		CarModel carModel = new CarModel();
		carModel.setId(carId);
		when(carRepository.findById(carId)).thenReturn(Optional.of(carModel));

		String result = homeController.carDetails(carId, model);

		assertEquals("details", result);
		verify(model, times(1)).addAttribute(eq("avto"), eq(carModel));
	}

	@Test
	public void testCarDetails_WithNonExistingCar() {
		Long carId = 1L;
		when(carRepository.findById(carId)).thenReturn(Optional.empty());

		String result = homeController.carDetails(carId, model);

		assertEquals("details", result);
		verify(model, times(0)).addAttribute(eq("avto"), any(CarModel.class));
	}

	@Test
	public void testDeleteCar() {
		Long carId = 1L;
		doNothing().when(carRepository).deleteById(carId);

		String result = homeController.deleteCar(carId);

		assertEquals("redirect:/", result);
		verify(carRepository, times(1)).deleteById(carId);
	}
}
