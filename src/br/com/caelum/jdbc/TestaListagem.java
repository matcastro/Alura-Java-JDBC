package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.caelum.jdbc.dao.CategoriasDAO;
import br.com.caelum.jdbc.dao.ProdutosDAO;
import br.com.caelum.jdbc.modelo.Categoria;
import br.com.caelum.jdbc.modelo.Produto;

public class TestaListagem {

	public static void main(String[] args) throws SQLException {
	//TestaRemocao
		Connection connection = new ConnectionPool().getConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("delete from produto where id>3");
		int count = statement.getUpdateCount();
		System.out.println(count + " linhas atualizadas");
		
		statement.close();
		connection.close();
	
	//TestaInsercao	
		try(Connection connection2 = new ConnectionPool().getConnection()){
			connection2.setAutoCommit(false);
			String sql = "insert into produto (nome, descricao) values (?, ?)";
			try(PreparedStatement statement2 = connection2.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				adiciona("TV LCD", "32 polegadas", statement2);
				adiciona("Blueray", "Full HDMI", statement2);
				connection2.commit();
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Rollback efetuado");
				connection2.rollback();
			}
		}
		
	//TestaListagem	
		Connection connection4 = new ConnectionPool().getConnection();
		
		Statement statement4 = connection4.createStatement();
		boolean resultado = statement4.execute("select * from Produto");
		ResultSet resultSet = statement4.getResultSet();
		
		while(resultSet.next()) {
			int id = resultSet.getInt("id");
			String nome = resultSet.getString("nome");
			String descricao = resultSet.getString("descricao");
			System.out.println(id);
			System.out.println(nome);
			System.out.println(descricao);
		}
		
		resultSet.close();
		statement.close();
		connection4.close();
	
	//TestaProdutos
		Produto mesa = new Produto("Mesa Vermelha", "Mesa de 4 pés");
		try(Connection connection3 = new ConnectionPool().getConnection()){
			ProdutosDAO dao = new ProdutosDAO(connection3);
			dao.salva(mesa);
			
			List<Produto> produtos = dao.lista();
			for(Produto produto : produtos) {
				System.out.println("Existe o produto: " + produto);
			}
			
		}
	
	//TestaCategoria
		try(Connection con = new ConnectionPool().getConnection()){
			List<Categoria> categorias = new CategoriasDAO(con).lista();
			for(Categoria categoria : categorias) {
				for(Produto produto : categoria.getProdutos()) {
					System.out.println(categoria.getNome() + " - " + produto.getNome());
				}
			}
		}
	
	}

	private static void adiciona(String nome, String descricao, PreparedStatement statement) throws SQLException {
        if (nome.equals("Blueray")) {
            throw new IllegalArgumentException("Problema ocorrido");
        }

		statement.setString(1, nome);
		statement.setString(2, descricao);
		
		boolean resultado = statement.execute();
		ResultSet resultSet = statement.getGeneratedKeys();
		while(resultSet.next()) {
			int id = resultSet.getInt("id");
			System.out.println(id);
		}
		
		System.out.println(resultado);
		resultSet.close();
	}

}
