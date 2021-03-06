package com.project.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringRootInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext container) throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebConfig.class);
		ctx.register(HibernateConfig.class);
		ctx.register(RestConfig.class);
		ctx.register(SchedulerConfig.class);
		ctx.setServletContext(container);

		DispatcherServlet servlet = new DispatcherServlet(ctx);
		servlet.setThrowExceptionIfNoHandlerFound(true);

		ServletRegistration.Dynamic dynamic = container.addServlet("dispatcher", servlet);
		dynamic.setLoadOnStartup(1);
		dynamic.addMapping("/");

		FilterRegistration.Dynamic fr = container.addFilter("encodingFilter", new CharacterEncodingFilter());
		fr.setInitParameter("encoding", "UTF-8");
		fr.setInitParameter("forceEncoding", "true");
		fr.addMappingForUrlPatterns(null, true, "/*");
	}
}
