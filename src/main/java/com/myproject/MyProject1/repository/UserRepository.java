package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.dto.RegisterUser;
import com.myproject.MyProject1.dto.UserGrid;
import com.myproject.MyProject1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,String> {

    @Query("SELECT usr FROM User usr where usr.username = :username")
    User findByUsername(String username);

    @Query(value = "select top 1(usr.userId) from [User] usr order by usr.userId DESC",nativeQuery = true)
    String getLastId();

    @Query("select new com.myproject.MyProject1.dto.UserGrid(usr.username,usr.email,usr.role) " +
            "from User usr " +
            "where usr.username LIKE %:search% " +
            "OR usr.email LIKE %:search% " +
            "OR usr.role LIKE %:search% ")
    Page<UserGrid> getAll(Pageable pageable, String search);
    @Query("SELECT new com.myproject.MyProject1.dto.RegisterUser(usr.username,usr.username,usr.password,usr.password,usr.email,usr.role) FROM User usr where usr.username = :username")
    RegisterUser getUserByUsername(String username);
}
