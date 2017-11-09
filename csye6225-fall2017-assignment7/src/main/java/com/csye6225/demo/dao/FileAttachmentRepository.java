/**
 * Amitha_Murali, 001643826, murali.a@husky.neu.edu
 * Jyoti Sharma, 001643410, sharma.j@husky.neu.edu
 * Surabhi Patil, 001251860, patil.sur@husky.neu.edu
 **/

package com.csye6225.demo.dao;

import com.csye6225.demo.entities.FileAttachment;
import com.csye6225.demo.entities.Task;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface FileAttachmentRepository extends CrudRepository<FileAttachment, String> {
}
