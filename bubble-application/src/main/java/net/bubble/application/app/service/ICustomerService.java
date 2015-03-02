/**
 * Copyright [2015-2017] [https://github.com/bubble-light/]
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
package net.bubble.application.app.service;

import net.bubble.application.framework.exception.ServiceException;
import net.bubble.persistence.app.entities.Customer;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月6日
 */
public interface ICustomerService {

	/**
	 * 查询客户信息
	 * @param customerId
	 * @return Customer
	 * @return Customer
	 */
	Customer getCustomerInfo(String customerId) throws ServiceException;

}
