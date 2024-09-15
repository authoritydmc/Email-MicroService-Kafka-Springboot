package in.rajlabs.EmailService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {



    @Bean
    public ITemplateResolver htmlTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("templates/");  // Template location
        emailTemplateResolver.setSuffix(".html");        // Template suffix
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding("UTF-8");
        return emailTemplateResolver;
    }
    @Bean
    public TemplateEngine templateEngine() {
        var templateEngine = new TemplateEngine();
        return templateEngine;
    }
}
