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
package net.bubble.persistence.framework.mybatis.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.bubble.persistence.framework.BubbleRepository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.Assert;

/**
 * Bubble持久化操作，BubbleRepository接口功能具体实现类</br>
 * 
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年1月30日
 */
public final class BubbleRepositoryImpl<T, ID extends Serializable> implements BubbleRepository<T, ID> {

	private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
	
	private static final String ENTITY_MUST_NOT_BE_NULL = "The given entity must not be null!";

	/**
	 * sqlSessionTemplate
	 */
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * mappedNameSpaceId;
	 */
	private String mapperNamespaceId;

	/**
	 * @param sqlSessionTemplate
	 * @param mapperNamespaceId
	 */
	public BubbleRepositoryImpl(SqlSessionTemplate sqlSessionTemplate, String mapperNamespaceId) {
		Assert.notNull(sqlSessionTemplate, "sqlSessionTemplate must not be null!");
		Assert.notNull(mapperNamespaceId, "mappedNameSpaceId must not be null!");
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.mapperNamespaceId = mapperNamespaceId;
	}

	@Override
	public List<T> findAll() {
		return sqlSessionTemplate.selectList(getMapperId());
	}

	@Override
	public T save(T entity) {
		Assert.notNull(entity, ENTITY_MUST_NOT_BE_NULL);
		int result = sqlSessionTemplate.insert(getMapperId(), entity);
		return result > 0 ? entity : null;
	}

	@Override
	public Iterable<T> save(Iterable<T> entities) {
		if (entities == null || !entities.iterator().hasNext()) {
			return Collections.emptyList();
		}
		List<T> result = new ArrayList<T>();
		for (T entity : entities) {
			result.add(save(entity));
		}
		return result;
	}

	@Override
	public T findOne(ID id) {
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);
		return sqlSessionTemplate.selectOne(getMapperId(), id);
	}

	@Override
	public boolean exists(ID id) {
		return findOne(id) == null ? false : true;
	}

	@Override
	public Iterable<T> findAll(Iterable<ID> ids) {
		if (ids == null || !ids.iterator().hasNext()) {
			return Collections.emptyList();
		}
		List<T> results = new ArrayList<T>();
		for (ID id : ids) {
			results.add(findOne(id));
		}
		return results;
	}

	@Override
	public long count() {
		return findAll().size();
	}

	@Override
	public void delete(ID id) {
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);
		sqlSessionTemplate.delete(getMapperId(), id);
	}

	@Override
	public void delete(T entity) {
		Assert.notNull(entity, ENTITY_MUST_NOT_BE_NULL);
		sqlSessionTemplate.delete(getMapperId(), entity);
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		Assert.notNull(entities, "The given Iterable of entities not be null!");
		for (T entity : entities) {
			delete(entity);
		}
	}

	@Override
	public void deleteAll() {
		for (T element : findAll()) {
			delete(element);
		}
	}

	private final String getMapperId() {
		return mapperNamespaceId + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
	}

}
