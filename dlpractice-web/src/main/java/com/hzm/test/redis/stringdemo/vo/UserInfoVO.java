package com.hzm.test.redis.stringdemo.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author houzm
 * @time 2017年4月4日下午12:24:02
 * @description redis-string-practice-userinfovo
 */
@JsonPropertyOrder(alphabetic=false)
public class UserInfoVO implements Serializable{

	private static final long serialVersionUID = -6332381230984581229L;
	
	private String incrKey;//id自增键的键名
	private String name;//user name
	private String age;//user age
	private String sex;//user sex
	/*@JsonSerialize(using= dl.dlutils.utils.jackson.DateSerializer.class)
    @JsonDeserialize(using= dl.dlutils.utils.jackson.DateDeserializer.class)*/
	private Date birthday;//user birthday
	private String address;//user address
	
	public String getIncrKey() {
		return incrKey;
	}
	public void setIncrKey(String incrKey) {
		this.incrKey = incrKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
