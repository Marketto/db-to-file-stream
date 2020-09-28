package it.marketto.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "test2")
@Table(name = "test2")
@Getter @Setter @NoArgsConstructor
public class TestEntity {
	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "data")
	private String data;
}
