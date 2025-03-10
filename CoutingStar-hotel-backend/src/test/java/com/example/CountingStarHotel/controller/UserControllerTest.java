package com.example.CountingStarHotel.controller;

import com.example.CountingStarHotel.DTO.request.user.UploadUserRequest;
import com.example.CountingStarHotel.DTO.response.user.UserResponse;
import com.example.CountingStarHotel.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UploadUserRequest request;

    private UserResponse userResponse;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void initData() {
        request = UploadUserRequest.builder()
                .firstName("Demo")
                .lastName("Demo")
                .email("demo@gmail.com")
                .password("123456789")
                .phoneNumber("123456789")
                .build();

        userResponse = UserResponse.builder()
                .id(Long.valueOf("1"))
                .firstName("Demo")
                .lastName("Demo")
                .email("demo@gmail.com")
                .phoneNumber("123456789")
                .build();
    }

    @Test
    void registerUser_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);
        Mockito.when(userService.registerUser(ArgumentMatchers.any())).thenReturn(userResponse);
        //WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register-user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void registerUser_invalidUserPasswordRequest_fail() throws Exception {
        // GIVEN
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword123");
        Mockito.when(userService.registerUser(ArgumentMatchers.any())).thenReturn(userResponse);

        String content = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register-user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName").value("Demo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastName").value("Demo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("demo@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value("123456789"));
    }

}
