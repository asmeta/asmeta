package asmeta.server;

import org.asmeta.runtime_container.SimulationContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
	public static SimulationContainer sim;
	
	@Bean
    WebMvcConfigurer configurer () {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers (ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/**").
                          addResourceLocations("file:///" + System.getProperty("user.dir") + "/asmeta-frontend/dist/");
            }
        };
    }

	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http:localhost:8081");
			}
		};
	}
	
	public static void main(String[] args) {
		sim = new SimulationContainer();
		sim.init(10);
		SpringApplication.run(Application.class, args);
	}

}
