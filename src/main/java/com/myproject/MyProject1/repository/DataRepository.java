package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataRepository extends JpaRepository<Data,String> {

    @Query(value = "select top 1(dat.dataId) FROM Data dat order by dat.dataId desc",nativeQuery = true)
    String getLastId();
}
