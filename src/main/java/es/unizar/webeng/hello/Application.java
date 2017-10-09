package es.unizar.webeng.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
* This class is a subclass of <code> @CodeSpringBootServletInitializer </code> This is done in order
* to produce a deployable war file. The next step is configure the application overriding
* the configure method.
*
* Annotations used in the code:<br>
* <code> @SpringBootApplication </code> Annotation from Spring introduced from SpringBoot v1.2.0 that represents three 
* annotations of Spring:  <br>
* <code> @Configuration </code> This annotation tags the class as the source for bean definitions. <br>
* <code> @EnableAutoConfiguration </code>  This enables the application to add the beans using the
* classpath definitions <br>
* <code> @ComponentScan </code> This annotation tells the spring to look for other components, 
* configurations and services in the specified path
* <br>
*     
* <code> @Override </code> It indicates that a method is overriding a method of its superclass.
* By using this tag, the compiler has to check that a method is actually being overrided 
* and this can avoid us a lot of problems, specially misspelling errors.
* Actually, it makes the code easier to understand because it is more obvious when methods are 
* overwritten <br>
*   
**/

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    /**
     * This main method uses {@code SpringApplication.run(Application.class, args)} to launch the application.
     * @param args Contains the supplied command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
