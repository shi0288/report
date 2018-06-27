package org.advert.report.util.core;

import org.advert.report.util.core.inter.ResourceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by shiqm on 2017/3/17.
 */

@Configuration
public class WebInterceptor extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResourceInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
