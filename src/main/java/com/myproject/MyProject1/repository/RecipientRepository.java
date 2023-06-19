package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.dto.InsertRecipient;
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

//    @Query("SELECT new com.myproject.MyProject1.dto.RecipientGrid(rcp.name,rcp.email) " +
//            "FROM Recipient rcp " +
//            "WHERE rcp.name LIKE %:search% " +
//                "OR rcp.email LIKE %:search% " )
//    List<RecipientGrid> getAll(@Param("search") String search);

    @Query("SELECT new com.myproject.MyProject1.dto.RecipientGrid(rcp.name,rcp.email) " +
            "FROM Recipient rcp " +
            "WHERE rcp.name LIKE %:search% " +
            "OR rcp.email LIKE %:search% " )
    Page<RecipientGrid> getAllData(Pageable pageable,@Param("search") String search);

    @Query("SELECT rcp FROM Recipient rcp")
    List<Recipient> findByTemplateId(String templateMessageId);

    @Query("select new com.myproject.MyProject1.dto.InsertRecipient(rcp.name,rcp.name,rcp.email) FROM Recipient rcp " +
            "WHERE rcp.name = :currentName")
    InsertRecipient findByName(String currentName);

    @Query("select rcp FROM Recipient rcp Where rcp.name = :currentName")
    Recipient getByName(String currentName);

    @Query("select rcp FROM Recipient rcp Where rcp.name = :name")
    List<Recipient> getListByName(String name);

    @Query("select Count(rcp) FROM Recipient rcp Where rcp.name = :name")
    int countByName(String name);
    @Query("select Count(rcp) FROM Recipient rcp Where rcp.email = :email")
    long countByEmail(String email);

    @Query(value = "SELECT  "+
            "    STUFF((\n" +
            "        SELECT ', ' + tm1.templateName\n" +
            "        FROM Recipient rc1\n" +
            "\tJOIN RecipientTemplate rt1 ON rc1.recipientId=rt1.recipientId\n" +
            "\tJOIN TemplateMessage tm1 ON rt1.templateMessageId=tm1.templateMessageId\n" +
            "        WHERE rc.name = rc1.name\n" +
            "        FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, '') AS templateNames\n" +
            "FROM Recipient rc\n" +
            "JOIN RecipientTemplate rt ON rc.recipientId=rt.recipientId\n" +
            "JOIN TemplateMessage tm ON rt.templateMessageId=tm.templateMessageId\n" +
            "where rc.[name] = :name " +
            "GROUP BY rc.[name];",nativeQuery = true)
    String getTemplateName(String name);
}
