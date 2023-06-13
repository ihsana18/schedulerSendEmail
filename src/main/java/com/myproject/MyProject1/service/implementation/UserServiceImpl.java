package com.myproject.MyProject1.service.implementation;

import com.myproject.MyProject1.configuration.ApplicationUserDetails;
import com.myproject.MyProject1.dto.RegisterUser;
import com.myproject.MyProject1.dto.UserGrid;
import com.myproject.MyProject1.entity.User;
import com.myproject.MyProject1.repository.UserRepository;
import com.myproject.MyProject1.service.abstraction.UserService;
import com.myproject.MyProject1.utility.AutoIncrementHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private User user;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        return new ApplicationUserDetails(user);
    }

    @Override
    public String getAccountRole(String username) {
        User user = userRepository.findByUsername(username);
        return user.getRole();
    }

    @Override
    public RegisterUser register(RegisterUser dto) {
        User userExisting = userRepository.findByUsername(dto.getCurrentUsername());
        if(userExisting != null){
            userExisting.setEmail(dto.getEmail());
            userExisting.setRole(dto.getRole());
            userExisting.setPassword(passwordEncoder.encode(dto.getPassword()) );
            userExisting.setUsername(dto.getUsername());
            userRepository.save(userExisting);

        }else {
            user.setUserId("USR"+ AutoIncrementHelper.increment(userRepository.getLastId()));
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRole(dto.getRole());
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            userRepository.save(user);

        }
        return dto;
    }

    @Override
    public Page<UserGrid> getListUser(int page, String search) {
        Pageable pageable = PageRequest.of(page-1,5);
        Page<UserGrid> userGrids = userRepository.getAll(pageable,search);
        return userGrids;
    }

    @Override
    public RegisterUser getUserByUsername(String username) {
        RegisterUser user = userRepository.getUserByUsername(username);
        return user;
    }

    @Override
    public RegisterUser update(RegisterUser dto) {
        User userExisting = userRepository.findByUsername(dto.getCurrentUsername());
        userExisting.setEmail(dto.getEmail());
        userExisting.setRole(dto.getRole());
        userExisting.setPassword(dto.getPassword());
        userExisting.setUsername(dto.getUsername());
        userRepository.save(userExisting);
        return dto;
    }

    @Override
    public void delete(String username) {
        User deleteUser = userRepository.findByUsername(username);
        userRepository.deleteById(deleteUser.getUserId());
    }

    @Override
    public boolean checkUsername(String value, String valueCurrent) {
        User userExist = userRepository.findByUsername(valueCurrent);
        int countUser=userRepository.countByName(value);
        if(value.toLowerCase().equals(valueCurrent.toLowerCase())){
            return false;
        }else if(userExist!=null && countUser==1 && value != valueCurrent){
            return true;
        }else if(userExist==null && countUser==1){
            return true;
        }
        return false;
    }
}
