package com.dong.blog.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

/**
 * 博主实体
 * 
 * @author java1234_小锋
 *
 */
@Entity
@Table(name = "t_blogger")
@NamedQueries({ @NamedQuery(name = "Blogger.findByUsername", query = "select _blogger from Blogger _blogger where _blogger.username= :username"),
				@NamedQuery(name = "Blogger.updatePassword", query = "update Blogger _blogger set _blogger.password= :password where _blogger.id=1")})
public class Blogger extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false, length = 50)
	private String username; // 用户名
	@Column(nullable = false, length = 100)
	private String password; // 密码
	@Column(name = "nick_name", length = 50)
	private String nickName; // 昵称
	@Column(length = 100)
	private String sign; // 个性签名
	@Column(name = "profile", columnDefinition = "text")
	private String proFile; // 个人简介
	@Column(name = "image_name", length = 100)
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

	public static Blogger findByUsername(String name) {
		return getRepository().createNamedQuery("Blogger.findByUsername")
				.addParameter("username", name).singleResult();
	}
	
	public static int updatePassword(String password) {
		return getRepository().createNamedQuery("Blogger.updatePassword")
				.addParameter("password", password).executeUpdate();
	}

	@Override
	public String toString() {
		return "Blogger [username=" + username + ", password=" + password
				+ ", nickName=" + nickName + ", sign=" + sign + ", proFile="
				+ proFile + ", imageName=" + imageName + "]";
	}

}
