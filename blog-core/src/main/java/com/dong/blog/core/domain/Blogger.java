package com.dong.blog.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.dayatang.domain.AbstractEntity;

/**
 * 博主实体
 * @author java1234_小锋
 *
 */
@Entity
@Table(name="t_blogger")
public class Blogger extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(nullable=false)
	private String username; // 用户名
	@Column(nullable=false)
	private String password; // 密码
	@Column
	private String nickName; // 昵称
	@Column
	private String sign; // 个性签名
	@Column
	private String proFile; // 个人简介
	@Column
	private String imageName; // 博主头像
	
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getProFile() {
		return proFile;
	}
	public void setProFile(String proFile) {
		this.proFile = proFile;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
		
}
