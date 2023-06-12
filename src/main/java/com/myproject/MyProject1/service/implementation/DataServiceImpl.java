package com.myproject.MyProject1.service.implementation;

import com.myproject.MyProject1.dto.InsertData;
import com.myproject.MyProject1.entity.Data;
import com.myproject.MyProject1.repository.DataRepository;
import com.myproject.MyProject1.service.abstraction.DataService;
import com.myproject.MyProject1.utility.AutoIncrementHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private Data data;

    @Override
    public void save(InsertData dto) {
        data.setDataId("DAT"+AutoIncrementHelper.increment(dataRepository.getLastId()));
        data.setEmail(dto.getEmail());
        data.setInputBy(dto.getInputBy());
        data.setCreatedDate(dto.getCreatedDate());
        dataRepository.save(data);
    }
}
