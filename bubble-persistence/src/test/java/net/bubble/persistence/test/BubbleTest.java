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
package net.bubble.persistence.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.bubble.persistence.BaseTest;
import net.bubble.persistence.app.entities.Customer;
import net.bubble.persistence.app.repositories.CustomerRepository;
import net.bubble.persistence.framework.page.Page;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月1日
 */
public class BubbleTest extends BaseTest {

	final Logger logger = LoggerFactory.getLogger(BubbleTest.class);
	
	@Autowired
	CustomerRepository customerRepository;

	@Test
	public void testStartUp(){
		logger.info("container is started!");
	}
	
	@Test
	public void testFindAll(){
		System.out.println(customerRepository.findAll());
	}
	
	@Test
	public void testSaveCustomer() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Customer customer = new Customer();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		customer.setId(uuid);
		customer.setFirstName("bob");
		customer.setLastName("david");
		customer.setEmailAddress("bob.david@gmail.com");
		customer.setCreatedBy("vincent");
		customer.setCreatedDate(sdf.format(new Date()));
		customer.setLastModifiedBy("vincent");
		customer.setLastModifiedDate(sdf.format(new Date()));
		customerRepository.save(customer);
		logger.info("save successful!");
	}
	
	@Test
	public void testExtension(){
		System.out.println(customerRepository.getRegisterCutomer("688e7bc45c36411dbe6b0ccbd463c2dc"));
	}
	
	@Test
	public void testPage(){
		Map params = new HashMap();
		params.put("id", "688e7bc45c36411dbe6b0ccbd463c2dc");
		Page<Customer> page = new Page<Customer>(1,10,params);
		List<Customer> resultList = customerRepository.getCustomerByPage(page);
		page.setResults(resultList);
		System.out.println(page);
	}
}
