package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.modelo.Categoria;
import br.com.caelum.jdbc.modelo.Produto;

public class ProdutosDAO {
	Connection con;
	public ProdutosDAO(Connection con) {
		this.con = con;
	}
	
	public void salva(Produto produto) throws SQLException {
		String sql = "insert into produto (nome, descricao) values (?, ?)";
		try(PreparedStatement stmt = this.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getDescricao());
			
			boolean resultado = stmt.execute();
			try(ResultSet resultSet = stmt.getGeneratedKeys()){
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					produto.setId(id);
					System.out.println(id);
				}
				
				System.out.println(resultado);
			}
		}
	}
	
	public List<Produto> lista() throws SQLException{
		List<Produto> produtos = new ArrayList<>();
		String sql = "select * from produto";
		try(PreparedStatement stmt = this.con.prepareStatement(sql)){
			stmt.execute();
			trasformaResultadoEmProdutos(produtos, stmt);
		}
		return produtos;
	}

	public List<Produto> busca(Categoria categoria) throws SQLException{
		List<Produto> produtos = new ArrayList<>();
		String sql = "select * from produto where categoria_id = ?";
		try(PreparedStatement stmt = this.con.prepareStatement(sql)){
			stmt.setInt(1, categoria.getId());
			stmt.execute();
			trasformaResultadoEmProdutos(produtos, stmt);
		}
		return produtos;
	}
	
	private void trasformaResultadoEmProdutos(List<Produto> produtos, PreparedStatement stmt) throws SQLException {
		try(ResultSet resultSet = stmt.getResultSet()){
		
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String nome = resultSet.getString("nome");
				String descricao = resultSet.getString("descricao");
				Produto produto = new Produto(nome, descricao);
				produto.setId(id);
				produtos.add(produto);
			}
		}
	}

}
