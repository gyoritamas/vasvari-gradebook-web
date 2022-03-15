package org.vasvari.gradebookweb;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.vasvari.gradebookweb.business.config.BusinessConfig;
import org.vasvari.gradebookweb.web.config.WebConfig;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

//    public static final String CHARACTER_ENCODING = "UTF-8";

    public WebApplicationInitializer() {
        super();
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { BusinessConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
//        encodingFilter.setEncoding(CHARACTER_ENCODING);
//        encodingFilter.setForceEncoding(true);
//        return new Filter[] { encodingFilter };
//    }

}