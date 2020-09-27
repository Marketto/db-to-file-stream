package it.marketto.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "test")
@Table(name = "test")
@Getter @Setter @NoArgsConstructor
public class TestEntity {
	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "data")
	private String data;
}
