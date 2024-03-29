package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.InsertTemplateMessage;
import com.myproject.MyProject1.dto.TemplateMessageGrid;
import com.myproject.MyProject1.entity.TemplateMessage;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface TemplateMessageService {
    String save(InsertTemplateMessage dto) throws IOException;

   Page<TemplateMessageGrid> getListTemplate(int page, String search);

    InsertTemplateMessage getTemplateByName(String currentTemplateName);

    void delete(String templateName);

    List<DropdownDTO> getTemplates();

    boolean checkTemplate(String valueTemplateName,String valueCurrentTemplateName);

    List<TemplateMessage> dropdownTemplate(String name);

    List<TemplateMessage> listTemplateByName(String name);
}
