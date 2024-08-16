package br.com.api.domain.DTO;

import java.util.Date;

public class LivroDTO {

	private Long id;
	private String nome;
	private Date datalancamento;
	private String autor;
	private String editora;
	private Integer numpags;
	private CapaDTO capa;

	public CapaDTO getCapa() {
		return capa;
	}

	public void setCapa(CapaDTO capa) {
		this.capa = capa;
	}

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

	public Date getDatalancamento() {
		return datalancamento;
	}

	public void setDatalancamento(Date datalancamento) {
		this.datalancamento = datalancamento;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public Integer getNumpags() {
		return numpags;
	}

	public void setNumpags(Integer numpags) {
		this.numpags = numpags;
	}
}