package com.quartzgui.jmx;

import java.io.Serializable;
import java.util.UUID;

public class JmxServerConfig implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String id;
	private String url;
	private String username;
	private String password;
	private Boolean local = Boolean.FALSE;
	
	/**
	 * Construct an empty server config. Auto generates ID.
	 */
	public JmxServerConfig() {
		id = UUID.randomUUID().toString();
	}
	
	public JmxServerConfig(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public Boolean getLocal() {
		return local;
	}

	public void setLocal(Boolean local) {
		this.local = local;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JmxServerConfig other = (JmxServerConfig) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
