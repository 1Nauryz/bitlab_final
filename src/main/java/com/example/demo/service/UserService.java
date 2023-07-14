package com.example.demo.service;

import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(username);
        if (user!=null){
            return user;
        }else {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


    public UserModel findUser(Long id){
        UserModel user = userRepository.findById(id).orElse(null);
        return user;
    }

    public UserModel editUser(UserModel userModel){
        return userRepository.save(userModel);
    }



    public UserModel addUser(UserModel userModel){
        UserModel checkUser = userRepository.findByEmail(userModel.getEmail());
        if (checkUser == null){
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
            userRepository.save(userModel);
        }
        return null;
    }

    public UserModel updatePassword(String newPassword, String oldPassword){
        UserModel currentUser = getCurrentSessionUser();
        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())){
            currentUser.setPassword(passwordEncoder.encode(newPassword));
           return userRepository.save(currentUser);
        }
        return null;
    }

    public UserModel editPassword(String newPassword, Long id){
        UserModel selectedUser = userRepository.findById(id).orElse(null);
        assert selectedUser != null;
        selectedUser.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(selectedUser);
    }
    public boolean checkPassword(String password){
        UserModel currentUser = getCurrentSessionUser();
        if (passwordEncoder.matches(password, currentUser.getPassword())){
            return true;
        }
        return false;
    }

    public UserModel updateProfile(String full_name){
        UserModel currentUser = getCurrentSessionUser();
        if (!full_name.equals(currentUser.getFull_name())){
            currentUser.setFull_name(full_name);
            return userRepository.save(currentUser);
        }
        return null;
    }

    public UserModel getCurrentSessionUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            UserModel userModel = (UserModel) authentication.getPrincipal();
            if (userModel != null){
                return userModel;
            }
        }
        return null;
    }

}
