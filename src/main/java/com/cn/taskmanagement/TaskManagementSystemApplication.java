    package com.cn.taskmanagement;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import javax.servlet.http.HttpServletResponse;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.autoconfigure.domain.EntityScan;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseStatus;

    @SpringBootApplication
    @EntityScan(basePackages = "com.cn.taskmanagement.model")
    public class TaskManagementSystemApplication {

        public static void main(String[] args) {
            SpringApplication.run(TaskManagementSystemApplication.class, args);
        }

        @ControllerAdvice
        public static class GlobalExceptionHandler {

            private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

            @ExceptionHandler(Exception.class)
            @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
            public void handleException(Exception e, HttpServletResponse response) {
                // Log the exception
                logger.error("An error occurred:", e);

                // Set the response status
                response.setStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }
