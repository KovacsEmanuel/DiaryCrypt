package ro.uvt.aplicatiejurnalcriptat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageConfiguration implements WebMvcConfigurer{
	  @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Override
	    public void addViewControllers(ViewControllerRegistry controllerRegistry) {
	        controllerRegistry.addViewController("/home").setViewName("home");
	        controllerRegistry.addViewController("/").setViewName("home");
	        controllerRegistry.addViewController("/dashboard").setViewName("dashboard");
	        controllerRegistry.addViewController("/login").setViewName("login");
	        controllerRegistry.addViewController("/jurnal").setViewName("jurnal");
	    }
}
