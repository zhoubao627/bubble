/**
 * Copyright [2015-2017] [https://github.com/bubble-light/]
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
package net.bubble.application.framework.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年2月6日
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"net.bubble.application.**.controller", "net.bubble.application.**.service"})
@ImportResource({"classpath*:config/applicationContext.xml", "classpath*:config/persistence/persistence.xml"})
public class BubbleApplicationConfiguration {
}
