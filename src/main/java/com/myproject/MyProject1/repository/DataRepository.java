package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data,String> {
}
