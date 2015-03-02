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
package net.bubble.persistence.framework.mybatis.plugin.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import net.bubble.persistence.framework.mybatis.plugin.DataBaseDialect;
import net.bubble.persistence.framework.mybatis.plugin.dialect.DB2Dialect;
import net.bubble.persistence.framework.mybatis.plugin.dialect.HSQLDBDialect;
import net.bubble.persistence.framework.mybatis.plugin.dialect.MySQLDialect;
import net.bubble.persistence.framework.mybatis.plugin.dialect.OracleDialect;
import net.bubble.persistence.framework.mybatis.plugin.dialect.SQLServerDialect;
import net.bubble.persistence.framework.page.Page;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月2日
 */
@Intercepts({ @org.apache.ibatis.plugin.Signature(type = StatementHandler.class, method = "prepare", args = { java.sql.Connection.class }) })
public class PaginationInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

	private final ObjectFactory objectFactory = new DefaultObjectFactory();

	private final ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, objectFactory, objectWrapperFactory);
		/*
		 * RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds"); if ((rowBounds == null) || (rowBounds ==
		 * RowBounds.DEFAULT)) { return invocation.proceed(); }
		 */
		Object parameterObject = statementHandler.getBoundSql().getParameterObject();
		if (!(parameterObject instanceof Page)) {
			return invocation.proceed();
		}
		Page page = (Page) parameterObject;
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		calculateTotalRecord(page, originalSql, statementHandler, metaStatementHandler, (Connection) invocation.getArgs()[0]);
		
		Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
		DataBaseDialect.Type databaseType = DataBaseDialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
		Assert.notNull(databaseType, "The value of the dialect property in BubbEngine is not defined : "
				+ configuration.getVariables().getProperty("dialect"));
		DataBaseDialect dialect = buildDialect(databaseType);
		String pageSql = dialect.getPageSelectSQL(originalSql, page.getOffset(), page.getPageSize());
		metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		if (logger.isDebugEnabled()) {
			logger.debug("Pagination SQL ：{}", originalSql.replaceAll("\\n|\\t", ""));
		}
	
		return invocation.proceed();
	}

	/**
	 * 计算总记录数
	 * 
	 * @param page
	 * @param originalSql
	 * @param connection
	 * @param parameterMappings
	 * @param statementHandler
	 * @param metaStatementHandler
	 * @param configuration
	 * @param connection
	 */
	private void calculateTotalRecord(Page page, String originalSql, StatementHandler statementHandler, MetaObject metaStatementHandler,
			Connection connection) {
		String countSql = "SELECT COUNT(1) FROM (" + originalSql + ") T";
		if (logger.isDebugEnabled()) {
			logger.debug("Pagination Count SQL ：{}", countSql.replaceAll("\\n|\\t", ""));
		}
		metaStatementHandler.setValue("delegate.boundSql.sql", countSql);

		// 通过connection建立一个countSql对应的PreparedStatement对象。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			statementHandler.getParameterHandler().setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				// 给当前的参数page对象设置总记录数
				page.setTotalRecord(totalRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private DataBaseDialect buildDialect(DataBaseDialect.Type databaseType) {
		DataBaseDialect dialect = null;
		switch (databaseType) {
		case ORACLE:
			dialect = new OracleDialect();
			break;
		case MYSQL:
			dialect = new MySQLDialect();
			break;
		case DB2:
			dialect = new DB2Dialect();
		case SQLSERVER:
			dialect = new SQLServerDialect();
			break;
		case HSQLDB:
			dialect = new HSQLDBDialect();
			break;
		}
		return dialect;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}