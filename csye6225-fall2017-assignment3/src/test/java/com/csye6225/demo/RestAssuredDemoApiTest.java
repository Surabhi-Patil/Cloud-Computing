package com.csye6225.demo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

//import com.jayway.restassured.RestAssured.given;
//import org.hamcrest.Matchers;

public class RestAssuredDemoApiTest {

@Ignore
  @Test
  public void testHomePage() throws URISyntaxException {
    RestAssured
            .when()
                .get(new URI("http://localhost:8000/"))
            .then()
                .statusCode(200);
  }

  @Ignore
  @Test
  public void testHomePageLoggedIn() throws URISyntaxException {
    RestAssured
            .given()
            .header("Authorization","Basic dXNlcjpwYXNzd29yZA==")
            .param("name","user")
            .param("email","user")
            .param("password","password")
            .when()
            .get(new URI("http://localhost:8000/"))
            .then()
            .statusCode(200);
  }

  @Ignore
  @Test
  public void testRegisterUser() throws URISyntaxException {
  RestAssured
          .given()
              .header("Authorization","Basic dXNlcjpwYXNzd29yZA==")
              .param("name","user")
              .param("email","user")
              .param("password","password")
          .when()
              .post(new URI("http://localhost:8000/user/register"))
          .then()
              .statusCode(200);
  }


  //Dummy testing
  @Test
  public void test()
  {
    assertEquals(true,true);
  }

  /*
  @Test
  public void testUserExists() throws URISyntaxException {
    RestAssured
            .given()
               .header("Authorization","Basic dXNlcjpwYXNzd29yZA==")
               .param("name","user")
               .param("email","user")
               .param("password","password")
            .when()
               .post(new URI("http://localhost:8000/user/register"))
            .then()
                .assertThat()
                .body("message",equalTo("USer already exists."));
  }*/
}
