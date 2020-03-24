package io.cosmos.msg.utils.type;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgAddCredentialValue {

	@JsonProperty("hash")
	@SerializedName("hash")
	private String hash;

	@JsonProperty("path")
	@SerializedName("path")
	private String path;

	@JsonProperty("time")
	@SerializedName("time")
	private String time;

	@JsonProperty("name")
	@SerializedName("name")
	private String name;

	@JsonProperty("owner")
	@SerializedName("owner")
	private String owner;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("hash", hash).append("path", path)
				.append("time", time).append("name", name).append("owner", owner).toString();

	}

}
