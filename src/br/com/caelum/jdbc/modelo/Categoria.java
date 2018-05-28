package br.com.caelum.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
	private Integer id;
	private String nome;
	private final List<Produto> produtos = new ArrayList<>();
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	public Categoria(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void adiciona(Produto produto) {
		this.produtos.add(produto);
	}

}
