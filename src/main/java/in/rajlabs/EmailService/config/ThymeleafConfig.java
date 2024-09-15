package in.rajlabs.EmailService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {

    /**
     * Configures the template resolver for Thymeleaf.
     *
     * @return A ClassLoaderTemplateResolver instance configured for HTML templates.
     */
    @Bean
    public ITemplateResolver htmlTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");  // Template location
        templateResolver.setSuffix(".html");        // Template suffix
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    /**
     * Configures the Thymeleaf template engine with the template resolver.
     *
     * @return A TemplateEngine instance configured with the provided resolver.
     */
    @Bean
    public TemplateEngine templateEngine(ITemplateResolver htmlTemplateResolver) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver);
        return templateEngine;
    }
}
