package br.com.api.domain;

import java.util.Date;

public class Livro {

	private Long id;
	private String nome;
	private Date datalancamento;
	private String autor;
	private String editora;
	private Integer numpags;

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
		if (nome == null || nome.isEmpty()) {
			nome = null;
		}
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
		if (autor == null || autor.isEmpty()) {
			autor = null;
		}
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		if (editora == null || editora.isEmpty()) {
			editora = null;
		}
		this.editora = editora;
	}

	public Integer getNumpags() {
		return numpags;
	}

	public void setNumpags(Integer numpags) {
		this.numpags = numpags;
	}
}
