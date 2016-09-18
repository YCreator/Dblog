package com.dong.blog.application.dto;

import java.io.Serializable;

/**
 * 博主实体
 * @author java1234_小锋
 *
 */
public class BloggerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7309038640229159183L;
	private Long id; // 编号
	private String username; // 用户名
	private String password; // 密码
	private String nickName; // 昵称
	private String sign; // 个性签名
	private String proFile; // 个人简介
	private String imageName; // 博主头像
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "BloggerDTO [id=" + id + ", username=" + username
				+ ", password=" + password + ", nickName=" + nickName
				+ ", sign=" + sign + ", proFile=" + proFile + ", imageName="
				+ imageName + "]";
	}
	
    
	
}
