/**
 * Copyright 2015-2017 https://git.oschina.net/teams/bubble-light
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.bubble.persistence.app.repositories;
import java.util.List;

import net.bubble.persistence.app.entities.Customer;
import net.bubble.persistence.framework.BubbleRepository;
import net.bubble.persistence.framework.page.Page;

import org.apache.ibatis.annotations.Select;


/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月2日
 */
public interface CustomerRepository extends BubbleRepository<Customer, String> {

	@Select("select id,first_name firstName,last_name lastName,email_address emailAddress,created_by createdBy,created_date createdDate,last_modified_by lastModifiedBy,last_modified_date lastModifiedDate from customer customer where id = #{id}")
	public Customer getRegisterCutomer(String id);
	
	public List<Customer> getCustomerByPage(Page<Customer> page);
	
}
