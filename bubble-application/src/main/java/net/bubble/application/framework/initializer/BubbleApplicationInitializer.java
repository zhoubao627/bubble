/**
 * Copyright 2015-2017 https://github.com/bubble-light/
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package net.bubble.application.framework.initializer;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import net.bubble.application.framework.configuration.BubbleApplicationConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年1月31日
 */
public class BubbleApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.setServletContext(servletContext);
		applicationContext.register(new Class[] { BubbleApplicationConfiguration.class });
		initParam(servletContext);
		initListener(servletContext);
		initFilter(servletContext);
		initServlet(servletContext, applicationContext);

	}

	private void initFilter(ServletContext servletContext) {
		EnumSet dispatcherTypeEnums = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
				DispatcherType.ERROR);
		
		FilterRegistration.Dynamic encodingDynamic = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		encodingDynamic.setInitParameter("encoding", "UTF-8");
		encodingDynamic.addMappingForUrlPatterns(dispatcherTypeEnums, false, new String[] { "/" });

		FilterRegistration.Dynamic hiddenMethodDynamic = servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter());
		hiddenMethodDynamic.addMappingForServletNames(dispatcherTypeEnums, true, new String[] { "dispatcher" });
	}

	private void initParam(ServletContext servletContext) {
//		servletContext.setInitParameter("contextConfigLocation",
//				"classpath*:config/applicationContext.xml,classpath*:config/persistence/persistence.xml");
	}

	private void initListener(ServletContext servletContext) {
		// servletContext.addListener(new ContextLoaderListener());
	}

	private void initServlet(ServletContext servletContext, ApplicationContext applicationContext) {
		ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(
				(AnnotationConfigWebApplicationContext) applicationContext));
		dynamic.addMapping(new String[] { "/" });
		dynamic.setLoadOnStartup(1);
	}

}