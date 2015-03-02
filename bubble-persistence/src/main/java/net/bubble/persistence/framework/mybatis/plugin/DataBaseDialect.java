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
package net.bubble.persistence.framework.mybatis.plugin;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月2日
 */
public abstract class DataBaseDialect {

	/**
	 * Pagination SQL
	 * @param sql Query SQL
	 * @param offset offset
	 * @param limit limit
	 * @return Pagination SQL
	 */
	public abstract String getPageSelectSQL(String sql, int offset, int limit);

	/**
	 * @author shiwen_xiao<xiaosw@msn.cn>
	 * @since 2015年2月4日
	 */
	public static enum Type {
		MYSQL, ORACLE, DB2, SQLSERVER, HSQLDB;
	}
}
