package com.cn.taskmanagement;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: Implement logic to authenticate user based on JWT wherever required and exception handling
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TaskManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementSystemApplication.class, args);
    }

    // TODO: Modify exception handling based on requirements. Handle exceptions explicitly wherever required
    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
        public void handleException(Exception e, HttpServletResponse response) {
            // Log the exception or perform other actions as needed
            response.setStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
