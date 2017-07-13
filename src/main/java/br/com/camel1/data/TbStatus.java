/**
 * 
 */
package br.com.camel1.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Robson Martins 16 de mai de 2017
 *
 */
@Entity
public class TbStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "diario_gerador_sequencia", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "SQSTATUS"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@Id
	@GeneratedValue(generator = "diario_gerador_sequencia")
	private Long id;

	@Column(unique=true)
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}