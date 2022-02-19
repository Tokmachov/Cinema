package edu.school21.cinema.listeners;

import edu.school21.cinema.config.Config;
import edu.school21.cinema.config.ServletContextAttributes;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CinemaServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(ServletContextAttributes.SPRING_CONTEXT, context);

        final JdbcTemplate jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
        final String schemaDefinitionFile = servletContext.getInitParameter("schemaDefinitionFile");
        List<String> ddlStatements = extractDDLStatements(schemaDefinitionFile);
        createSchema(ddlStatements, jdbcTemplate);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        AnnotationConfigApplicationContext context = (AnnotationConfigApplicationContext) sce.getServletContext().getAttribute(ServletContextAttributes.SPRING_CONTEXT);
        context.close();
    }

    private List<String> extractDDLStatements(String schemaDefinitionFileName) {
        List<String> statements = new ArrayList<>();

        try (InputStream is = CinemaServletContextListener.class.getResourceAsStream("/" + schemaDefinitionFileName))  {
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                statements.add(scanner.next());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to open schema definition file.");
        }
        return statements;
    }

    private void createSchema(List<String> ddlStatements, JdbcTemplate jdbcTemplate) {
        ddlStatements.forEach(jdbcTemplate::execute);
    }
}
