package com.gleb.vinnikov.social_network.auth.api;

import com.gleb.vinnikov.social_network.entities.Role;
import com.gleb.vinnikov.social_network.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private WebTestClient webTestClient;

    private String loginPath;
    private String registerPath;
    private final String USERNAME = "brenscrazy";
    private final String EMAIL = "glebmanufa@gmail.com";
    private final String PASSWORD = "qwerty123";

    @Before
    public void init() {
        loginPath = "http://localhost:" + port + "/login";
        registerPath = "http://localhost:" + port + "/registration";
    }

    @Test
    public void noUserLoginTest() {
        login(new LoginRequest(USERNAME, PASSWORD)).expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void registrationTest() {
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    public void registrationEmailIsTakenTest() {
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
        registration(new RegistrationRequest(USERNAME + "123", EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void registrationUsernameIsTakenTest() {
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
        registration(new RegistrationRequest(USERNAME, EMAIL + "pany", PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void correctLoginTest() {
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
        login(new LoginRequest(USERNAME, PASSWORD)).expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    public void wrongPasswordTest() {
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
        login(new LoginRequest(USERNAME, "wrong_password")).expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private WebTestClient.ResponseSpec registration(RegistrationRequest request) {
        return webTestClient.post().uri(registerPath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), LoginResponse.class)
                .exchange();
    }

    private WebTestClient.ResponseSpec login(LoginRequest request) {
        return webTestClient.post().uri(loginPath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), LoginResponse.class)
                .exchange();
    }

}
