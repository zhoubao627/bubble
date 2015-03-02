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
package net.bubble.persistence.framework.mybatis.factory;

import java.io.Serializable;

import net.bubble.persistence.framework.BubbleRepository;
import net.bubble.persistence.framework.mybatis.query.BubbleQueryLookupStrategy;
import net.bubble.persistence.framework.mybatis.support.BubbleRepositoryImpl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.util.Assert;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年1月30日
 */
public class BubbleRepositoryFactoryBean<R extends BubbleRepository<T, ID>, T, ID extends Serializable> extends
		RepositoryFactoryBeanSupport<R, T, ID> {

	private SqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory() {
		return new BubbleRepositoryFactory<T, ID>();
	}
	
	

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(sqlSessionTemplate, "SqlSessionTemplate must not be null!");
		super.afterPropertiesSet();
	}

	/**
	 * @author shiwen_xiao<xiaosw@msn.cn>
	 * @since 2015年2月4日
	 * @param <T>
	 * @param <ID>
	 */
	private class BubbleRepositoryFactory<T, ID extends Serializable> extends RepositoryFactorySupport {

		@Override
		protected Object getTargetRepository(RepositoryMetadata metadata) {
			return new BubbleRepositoryImpl<T, ID>(sqlSessionTemplate, metadata.getRepositoryInterface().getCanonicalName());
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return BubbleRepository.class;
		}

		@Override
		protected QueryLookupStrategy getQueryLookupStrategy(Key key, EvaluationContextProvider evaluationContextProvider) {
			return BubbleQueryLookupStrategy.create(sqlSessionTemplate, key, evaluationContextProvider);
		}

		@Override
		public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
