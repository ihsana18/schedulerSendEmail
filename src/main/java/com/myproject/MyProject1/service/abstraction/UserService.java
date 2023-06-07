package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.RegisterUser;
import com.myproject.MyProject1.dto.UserGrid;
import com.myproject.MyProject1.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    String getAccountRole(String username);

    RegisterUser register(RegisterUser dto);

    Page<UserGrid> getListUser(int page, String search);

    RegisterUser getUserByUsername(String username);

    RegisterUser update(RegisterUser dto);

    void delete(String username);
}
