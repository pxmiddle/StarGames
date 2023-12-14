package com.generation.stargames.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100)
	@NotBlank(message = "O Atributo Nome é obrigatório!")
	@Size(min = 5, max = 100, message = "O Atributo Nome deve conter no mínimo 5 e no maximo 100 caractéres")
	private String nome;
	
	@Column(length = 500)
	@NotBlank(message = "O Atributo Descrição é obrigatório!")
	@Size(min = 5, max = 500, message = "O Atributo Descrição deve conter no mínimo 5 e no maximo 500 caractéres")
	private String descricao;
	
	@Column(length = 100)
	@NotBlank(message = "O Atributo Console é obrigatório!")
	@Size(min = 5, max = 100, message = "O Atributo Console deve conter no mínimo 5 e no maximo 100 caractéres")
	private String console;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataLancamento;
	
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
	private BigDecimal preco;
	
	@Column(length = 500)
	@NotBlank(message = "O Atributo Foto é obrigatório!")
	@Size(min = 5, max = 500, message = "O Atributo Foto deve conter no mínimo 5 e no maximo 500 caractéres")
	private String foto;

	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	
	
	
	
}
