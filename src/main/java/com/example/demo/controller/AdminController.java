package com.example.demo.controller;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin-page")
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public String indexPage(Model model){
        model.addAttribute("users", userService.getUsers());
        return "admin_page";
    }

    @PostMapping("delete-user")
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public String deleteCar(@RequestParam(name = "id")Long id){
        userService.deleteUser(id);
        return "redirect:/admin-page";
    }

    @GetMapping("edit-page/{usId}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public String viewEditPage(@PathVariable(name = "usId") Long id, Model model){
        model.addAttribute("user", userService.findUser(id));
        return "edit_user_page";
    }

    @PostMapping("/edit-user")
    @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_ADMIN')")
    public String editUser(@RequestParam(name = "user_full_name")String full_name,
                           @RequestParam(name = "user_email")String email,
                           @RequestParam(name = "user_password")String newPassword,
                           @RequestParam(name = "id")Long id){
        UserModel userModel = userService.findUser(id);
        userModel.setEmail(email);
        userService.editPassword(newPassword,id);
        userModel.setFull_name(full_name);
        userService.editUser(userModel);
        return "redirect:/admin-page";
    }

}
