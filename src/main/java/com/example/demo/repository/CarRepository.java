package com.example.demo.repository;

import com.example.demo.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {

    @Query(value = "select cc from CarModel cc where cc.mark like :searchName or cc.model like :searchName")
    List<CarModel> searchCarModel(@Param("searchName")String carName);

}
