package com.example.demo;

import com.example.demo.model.MarkModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkModelTests {

    @Test
    public void testMarkModelProperties() {
        MarkModel markModel = new MarkModel();
        markModel.setMark("Toyota");
        markModel.setIcon("toyota_icon.png");

        assertEquals("Toyota", markModel.getMark());
        assertEquals("toyota_icon.png", markModel.getIcon());
    }
}
