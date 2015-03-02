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
package net.bubble.persistence.framework.mybatis.config;

import net.bubble.persistence.framework.mybatis.factory.BubbleRepositoryFactoryBean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月4日
 */
public class BubbleRepositoryConfigExtension extends RepositoryConfigurationExtensionSupport {

	@Override
	public String getRepositoryFactoryClassName() {

		return BubbleRepositoryFactoryBean.class.getName();
	}

	@Override
	protected String getModulePrefix() {
		return "mybatis";
	}

	@Override
	public void postProcess(BeanDefinitionBuilder builder, XmlRepositoryConfigurationSource config) {
		Element element = config.getElement();
		postProcess(builder, element.getAttribute("transaction-manager-ref"), element.getAttribute("sql-session-template-ref"),
				config.getSource());
	}

	@Override
	public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config) {
		AnnotationAttributes attributes = config.getAttributes();
		postProcess(builder, attributes.getString("transactionManagerRef"), attributes.getString("sqlSessionTemplateRef"),
				config.getSource());
	}

	private void postProcess(BeanDefinitionBuilder builder, String transactionManagerRef, String sqlSessionTemplateRef, Object source) {
		// TODO : transactionManagerRef
		/*if (StringUtils.hasText(transactionManagerRef)) {
			builder.addPropertyReference("transactionManager", transactionManagerRef);
		} else {
			builder.addPropertyReference("transactionManager", "transactionManager");
		}*/
		if (StringUtils.hasText(sqlSessionTemplateRef)) {
			builder.addPropertyReference("sqlSessionTemplate", sqlSessionTemplateRef);
		} else {
			builder.addPropertyReference("sqlSessionTemplate", "sqlSessionTemplate");
		}
	}

}
