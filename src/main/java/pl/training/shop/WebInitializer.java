package pl.training.shop;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;

public class WebInitializer implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String ROOT_URL = "/";

    @Override
    public void onStartup(ServletContext servletContext) {
        var container = new AnnotationConfigWebApplicationContext();
        container.register(ShopConfiguration.class);
        var dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(container));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(ROOT_URL);
    }

}
