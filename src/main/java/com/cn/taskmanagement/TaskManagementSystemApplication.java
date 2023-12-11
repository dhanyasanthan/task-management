    package com.cn.taskmanagement;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.autoconfigure.domain.EntityScan;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseStatus;

    import javax.servlet.http.HttpServletResponse;

    /**
     * The main class for the Task Management System Spring Boot application.
     */
    @SpringBootApplication
    @EntityScan(basePackages = "com.cn.taskmanagement.model")
    public class TaskManagementSystemApplication {

        /**
         * The main method that starts the Spring Boot application.
         *
         * @param args Command-line arguments.
         */
        public static void main(String[] args) {
            SpringApplication.run(TaskManagementSystemApplication.class, args);
        }

        /**
         * Global exception handler for handling unhandled exceptions across the application.
         */
        @ControllerAdvice
        public static class GlobalExceptionHandler {

            private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

            /**
             * Handles exceptions of type Exception.
             *
             * @param e        The exception that occurred.
             * @param response The HTTP servlet response.
             */
            @ExceptionHandler(Exception.class)
            @ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
            public void handleException(Exception e, HttpServletResponse response) {
                // Log the exception
                logger.error("An error occurred:", e);

                // Set the response status to internal server error
                response.setStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }
