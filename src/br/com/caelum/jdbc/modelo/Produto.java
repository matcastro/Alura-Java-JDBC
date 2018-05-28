package br.com.caelum.jdbc.modelo;

public class Produto {
	
	Integer id;
	String nome;
	String descricao;
	
	public Produto(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		
		return "[nome: " + this.nome + " descricao: " + this.descricao;
	}

}
