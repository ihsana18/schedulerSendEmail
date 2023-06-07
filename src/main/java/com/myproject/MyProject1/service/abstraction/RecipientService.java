package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.InsertRecipient;

public interface RecipientService {
    Object save(InsertRecipient dto);

    Object findAll(int page, String name);
}
