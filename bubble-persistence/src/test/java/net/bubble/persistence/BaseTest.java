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
package net.bubble.persistence;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月1日
 */
@ContextConfiguration(locations={"classpath:config/applicationContext.xml","classpath:config/persistence/persistence.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
public class BaseTest {
}
