package com.example.demo.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ajax")
public class AjaxController {
    @GetMapping
    public List<String> textList(@RequestParam(name = "test",required = false, defaultValue = "Nauryz")String test){
        List<String> list = new ArrayList<>();
        list.add("Hello " + test);
        list.add("Salam " + test);
        list.add("Abi " + test);
        list.add("Ket " + test);
        return list;
    }
}
