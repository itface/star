package com.itface.star.system.org.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_generator")
public class IdGenerator {

	@Id
	@Column(name="gen_name",length = 100,unique=true)
	private String gen_name;
	@Column(name="gen_value")
	private long gen_value;
	public String getGen_name() {
		return gen_name;
	}
	public void setGen_name(String gen_name) {
		this.gen_name = gen_name;
	}
	public long getGen_value() {
		return gen_value;
	}
	public void setGen_value(long gen_value) {
		this.gen_value = gen_value;
	}
	
}
