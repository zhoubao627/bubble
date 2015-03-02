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
package net.bubble.persistence.framework;

import java.io.Serializable;

/**
 * @author shiwen_xiao<xiaosw@msn.cn>
 * @since 2015年1月31日
 */
public class BubbleEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3520763606031566400L;

	private String id;

	private String createdDate;

	private String lastModifiedDate;

	private String createdBy;

	private String lastModifiedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public boolean isNew() {
		return false;
	}

	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
			return false;
		}
		BubbleEntity that = (BubbleEntity) obj;
		return this.id.equals(that.getId());
	}
}
