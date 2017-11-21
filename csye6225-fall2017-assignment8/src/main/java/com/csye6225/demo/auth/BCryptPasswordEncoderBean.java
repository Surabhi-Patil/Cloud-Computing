/**
 * Amitha_Murali, 001643826, murali.a@husky.neu.edu
 * Jyoti Sharma, 001643410, sharma.j@husky.neu.edu
 * Surabhi Patil, 001251860, patil.sur@husky.neu.edu
 **/

package com.csye6225.demo.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptPasswordEncoderBean {

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
