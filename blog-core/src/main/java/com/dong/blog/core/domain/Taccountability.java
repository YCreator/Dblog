package com.dong.blog.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "t_accountabilities")
@DiscriminatorColumn(name = "CATEGORY", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Taccountability")
@NamedQueries({ @NamedQuery(name = "Taccountability.findAccountabilitiesByMenu", query = "select o from Taccountability o where o.commissioner = :menu or o.responsible = :menu") })
public class Taccountability<C extends Menu, R extends Menu> extends
		AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(targetEntity = Menu.class, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "commissioner_id")
	private C commissioner;
	@ManyToOne(targetEntity = Menu.class, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "responsible_id")
	private R responsible;
	
	public Taccountability() {
		
	}
	
	public Taccountability(C commissioner, R responsible) {
		this.commissioner = commissioner;
		this.responsible = responsible;
	}

	public C getCommissioner() {
		return commissioner;
	}

	public void setCommissioner(C commissioner) {
		this.commissioner = commissioner;
	}

	public R getResponsible() {
		return responsible;
	}

	public void setResponsible(R responsible) {
		this.responsible = responsible;
	}

	@SuppressWarnings("rawtypes")
	public static <T extends Taccountability> T getByCommissionerAndResponsible(
			Class<T> accountabilityClass, Menu commissioner, Menu responsible) {
		return getRepository().createCriteriaQuery(accountabilityClass)
				.eq("commissioner", commissioner)
				.eq("responsible", responsible).singleResult();
	}

	@SuppressWarnings("rawtypes")
	public static List<Taccountability> findAccountabilitiesByBparty(Menu menu) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("menu", menu);
		return getRepository()
				.createNamedQuery("Taccountability.findAccountabilitiesByMenu")
				.addParameter("menu", menu).list();
	}

}
