package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.dto.RecipientGrid;
import com.myproject.MyProject1.entity.Recipient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RecipientRepository extends JpaRepository<Recipient,String> {
    @Query(value = "SELECT TOP 1(rcp.recipientId) FROM Recipient rcp ORDER BY rcp.recipientId DESC",nativeQuery = true)
    String getLastId();

    @Query("SELECT new com.myproject.MyProject1.dto.RecipientGrid(rcp.name,rcp.email,tmp.templateName) " +
            "FROM Recipient rcp " +
            "JOIN rcp.templateMessage tmp " +
            "WHERE rcp.name LIKE %:name%")
    List<RecipientGrid> getAll(@Param("name") String name);

    @Query("SELECT new com.myproject.MyProject1.dto.RecipientGrid(rcp.name,rcp.email,tmp.templateName) " +
            "FROM Recipient rcp " +
            "JOIN rcp.templateMessage tmp " +
            "WHERE rcp.name LIKE %:name%")
    Page<RecipientGrid> getAllData(Pageable pageable,@Param("name") String name);

    @Query("SELECT rcp FROM Recipient rcp WHERE rcp.templateMessageId = :templateMessageId")
    List<Recipient> findByTemplateId(String templateMessageId);
}
