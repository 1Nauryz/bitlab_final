package com.example.demo.controller;

import com.example.demo.model.CarModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HomeController {


    @Value("${cars.images.path}")
    private String carsImagesPath;

    @Autowired
    private UserService userService;

    @Autowired
    private CarRepository carRepository;

    private final CarService carService;

    private final FileStorageService fileStorageService;

    public HomeController(CarService carService, FileStorageService fileStorageService) {
        this.carService = carService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/")
    public String indexPage(Model model, @RequestParam(name = "key", required = false, defaultValue = "") String key){
        model.addAttribute("auto", carService.searchCarModel(key));
        return "index";
    }

    @GetMapping("/sign-in-page")
    public String signInPage(){
        return "sign_in_page";
    }

    @GetMapping("/sign-up-page")
    public String signUpPage(){
        return "sign_up_page";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String profilePage(){
        return "profile";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add-page")
    public String addPage(Model model) {
        return "add_page";
    }

    @PostMapping("/add-car")
    @PreAuthorize("isAuthenticated()")
    public String addCar(@RequestParam(name = "city") String city,
                         @RequestParam(name = "image") MultipartFile image,
                         @RequestParam(name = "type")String type,
                         @RequestParam(name = "mark")String mark,
                         @RequestParam(name = "model")String model,
                         @RequestParam(name = "mileage")int mileage,
                         @RequestParam(name = "volume") double volume,
                         @RequestParam(name = "transmission") String transmission,
                         @RequestParam(name = "phone") String phone,
                         @RequestParam(name = "color") String color) {

        try {

            CarModel carModel = new CarModel();

            carModel.setCity(city);
            carModel.setMark(mark);
            carModel.setModel(model);
            carModel.setType(type);
            carModel.setMileage(mileage);
            carModel.setVolume(volume);
            carModel.setTransmission(transmission);
            carModel.setColor(color);
            carModel.setPhone(phone);

            if (!image.isEmpty()) {
                String fileName = fileStorageService.saveFile(image, carsImagesPath);
                carModel.setImage(fileName);
            }

            CarModel newCar = carService.addIm(carModel);

            if (newCar != null) {
                return "redirect:?/";
            } else {
                return "redirect:/add-car?error";
            }

        } catch (IOException e) {
            System.out.println(e);
            return "redirect:/add-car?error";
        }
    }

    @GetMapping("/show-page/{carId}")
    @PreAuthorize("isAuthenticated()")
    public String showPage(@PathVariable(name = "carId") Long id, Model model){
        if (carRepository != null) {
            CarModel car = carRepository.findById(id).orElse(null);
            model.addAttribute("avto", car);
            return "show_page";
        }
        return "SORRY CAR NOT FOUND";
    }


    @GetMapping("/details/{carId}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public String carDetails(@PathVariable(name = "carId") Long id, Model model){
        if (carRepository != null) {
            CarModel car = carRepository.findById(id).orElse(null);
            model.addAttribute("avto", car);
            return "details";
        }
        return "SORRY CAR NOT FOUND";
    }

    @PostMapping("/edit-car")
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public String saveCar(@RequestParam(name = "city") String city,
                          @RequestParam(name = "image") MultipartFile image,
                          @RequestParam(name = "type")String type,
                          @RequestParam(name = "id")Long id,
                          @RequestParam(name = "mark")String mark,
                          @RequestParam(name = "model")String model,
                          @RequestParam(name = "mileage")int mileage,
                          @RequestParam(name = "volume") Float volume,
                          @RequestParam(name = "transmission") String transmission,
                          @RequestParam(name = "phone") String phone,
                          @RequestParam(name = "color") String color){
        try {

            CarModel carModel = carRepository.findById(id).orElse(null);

            carModel.setCity(city);
            carModel.setMark(mark);
            carModel.setModel(model);
            carModel.setType(type);
            carModel.setMileage(mileage);
            carModel.setVolume(volume);
            carModel.setTransmission(transmission);
            carModel.setColor(color);
            carModel.setPhone(phone);

            if (!image.isEmpty()) {
                String fileName = fileStorageService.saveFile(image, carsImagesPath);
                carModel.setImage(fileName);
            }

            CarModel editCar = carService.editCar(carModel);

            if (editCar != null) {
                return "redirect:?/";
            } else {
                return "redirect:/add-car?error";
            }

        } catch (IOException e) {
            System.out.println(e);
            return "redirect:/add-car?error";
        }
    }

    @PostMapping("delete-car")
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public String deleteCar(@RequestParam(name = "id")Long id){
        if (carRepository != null) {
            carRepository.deleteById(id);
            return "redirect:/";
        }
        return null;
    }

    @PostMapping("/to-sign-up")
    public String toSignUp(@RequestParam(name = "user_email")String email,
                           @RequestParam(name = "user_password")String password,
                           @RequestParam(name = "user_full_name")String full_name,
                           @RequestParam(name = "user_repeat_password")String repeat_password)
    {
        if (password.equals(repeat_password)){
            UserModel userModel = new UserModel();
            userModel.setEmail(email);
            userModel.setPassword(password);
            userModel.setFull_name(full_name);
            UserModel newUser = userService.addUser(userModel);
            if (newUser != null){
                return "redirect:/sign-up-page?success";
            }else
                return "redirect:/sign-up-page?emailError";
        }else {
            return "redirect:/sign-up-page?passwordError";
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("update-password-page")
    public String updatePasswordPage(){
        return "update_password_page";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("update-profile-page")
    public String updateProfilePage(){
        return "update_profile_page";
    }

    @PostMapping("/to-update-password")
    @PreAuthorize("isAuthenticated()")
    public String toUpdatePassword(
                           @RequestParam(name = "user_old_password")String oldPassword,
                           @RequestParam(name = "user_new_password")String newPassword,
                           @RequestParam(name = "user_repeat_new_password")String repeatNewPassword)
    {
        if (newPassword.equals(repeatNewPassword)){
            UserModel userModel = userService.updatePassword(newPassword,oldPassword);
            if (userModel!= null){
                return "redirect:/update-password-page?success";
            }
            else{
                return "redirect:/update-password-page?oldPasError";
            }
        }else {
            return "redirect:/update-password-page?passwordError";
        }
    }
    @PostMapping("/to-update-profile")
    @PreAuthorize("isAuthenticated()")
    public String toUpdateProfile(
            @RequestParam(name = "user_full_name")String full_name,
            @RequestParam(name = "user_password")String password)
    {
        if (userService.checkPassword(password)){
            UserModel userModel = userService.updateProfile(full_name);
            if (userModel!= null){
                return "redirect:/update-profile-page?success";
            }
            else{
                return "redirect:/update-profile-page?fullNameError";
            }
        }else {
            return "redirect:/update-profile-page?fullNameError";
        }
    }

}
