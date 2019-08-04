package cn.com.taiji.springdatajpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * 类名称：DeptUser   机构用户中间表 
 * 类描述：   
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:40:37 
 * @version
 */
@Entity
@Table(name="dept_user")
@NamedQuery(name="DeptUser.findAll", query="SELECT d FROM DeptUser d")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class DeptUser implements Serializable {
	
	private static final long serialVersionUID = 5680225687620614164L;
	@EmbeddedId
	private DeptUserPK id;

	public DeptUser() {
	}

	public DeptUserPK getId() {
		return this.id;
	}

	public void setId(DeptUserPK id) {
		this.id = id;
	}

}