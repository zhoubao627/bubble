/**
 * Copyright 2015-2017 https://git.oschina.net/teams/bubble-light
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
package net.bubble.persistence.framework.engine;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import net.bubble.persistence.framework.BubbleEntity;
import net.bubble.persistence.framework.BubbleRepository;
import net.bubble.persistence.framework.mybatis.config.EnableBubbleRepositories;
import net.bubble.persistence.framework.mybatis.plugin.interceptor.PaginationInterceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月1日
 */
@Configuration
@EnableTransactionManagement
@EnableBubbleRepositories(basePackages = "net.bubble.persistence.**.repositories")
public class BubbleMyBatisEngine implements ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private String dialect;

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	@Bean
	SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setTypeAliasesPackage("net.bubble.persistence.**.entities");
		sessionFactory.setTypeAliasesSuperType(BubbleEntity.class);
		sessionFactory.setMapperLocations(getResources("classpath*:config/mybatis/mapper/**/*Mapper.xml"));
		Interceptor[] interceptors = new Interceptor[1];
		interceptors[0] = paginationInterceptor();
		sessionFactory.setPlugins(interceptors);
		Properties properties = new Properties();
		properties.setProperty("dialect", dialect);
		sessionFactory.setConfigurationProperties(properties);
		return sessionFactory.getObject();
	}

	@Bean
	@Scope("prototype")
	SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
	}

	@Bean
	Interceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	@Bean
	MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
		mapperScannerConfigurer.setBasePackage("net.bubble.persistence.**.repositories");
		mapperScannerConfigurer.setMarkerInterface(BubbleRepository.class);
		return mapperScannerConfigurer;
	}

	private Resource[] getResources(String packagePath) throws IOException {
		ResourcePatternResolver resourceResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		Resource[] resources = resourceResolver.getResources(packagePath);
		return resources;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
