package com.csye6225.demo.entities;
/**
 * Amitha_Murali, 001643826, murali.a@husky.neu.edu
 * Jyoti Sharma, 001643410, sharma.j@husky.neu.edu
 * Surabhi Patil, 001251860, patil.sur@husky.neu.edu
 **/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity // This tells Hibernate to make a table out of this class
public class Task {
    @Id
    private long id;

    @Column(columnDefinition = "VARCHAR(4096)")
    private String description;

    private int userId;

    public long getId() {
        return id;
    }
    public void setId(){
        //  this.id= UUID.randomUUID().getMostSignificantBits();}
        this.id = generateUniqueId(); }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private Long generateUniqueId()
    {
        long val = -1;
        do
        {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0);
        return val;
    }
}