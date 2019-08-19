package aula20190531.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

public class App {
	
	public static void main(String[] args) {

		try {
			Connection conex�o = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/3sadsis2019",
					"postgres",
					"unicesumar");

			criarTabelaCliente(conex�o);
			
			excluirTudo(conex�o);
			
			inserirCliente(conex�o, 1, "Guilherme Longhini", new Date());
			
			Calendar calendario = Calendar.getInstance();
			calendario.set(Calendar.YEAR, 1999);
			calendario.set(Calendar.MONTH, 3);
			calendario.set(Calendar.DAY_OF_MONTH, 30);
			Date nascimentoBruna = calendario.getTime();
			
			inserirCliente(conex�o, 2, "Bruna Lamberti", nascimentoBruna);
			
			//UPDATE
			calendario.set(Calendar.YEAR, 200);
			calendario.set(Calendar.MONTH, 02);
			calendario.set(Calendar.DAY_OF_MONTH, 16);
			nascimentoBruna = calendario.getTime();
			atualizarCliente(conex�o, 2, "Bruna Lamberti", nascimentoBruna);
						
			System.out.println("Conectou!");
			conex�o.close();
			System.out.println("Fechou!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fim.");
	}
	public static void atualizarCliente(Connection conex�o, Integer id, String nome, Date nascimento) throws Exception {
		String sql = "UPDATE cliente "
				+ "set nome = ?, "
				+ "nascimento = ? "
				+ "where id = ?";
		PreparedStatement statement = conex�o.prepareStatement(sql);
		
		statement.setInt(3, id);
		statement.setString(1, nome);
		statement.setDate(2, new java.sql.Date(nascimento.getTime()));
		
		statement.execute();
		statement.close();
		
	}
	
	public static void excluirTudo(Connection conex�o) throws Exception {
		String sql = "DELETE from cliente";		
		Statement statement = conex�o.createStatement();
		statement.execute(sql);
		statement.close();
	}
	
	public static void inserirCliente(Connection conex�o, Integer id, String nome, Date nascimento) throws Exception {
		String sql = "INSERT into cliente (id, nome, nascimento) values (?,?,?)";
		PreparedStatement statement = conex�o.prepareStatement(sql);
		
		statement.setInt(1, id);
		statement.setString(2, nome);
		statement.setDate(3, new java.sql.Date(nascimento.getTime()));
		
		statement.execute();
		statement.close();
		
	}
	
	public static void criarTabelaCliente(Connection conex�o) throws Exception {
		String sql = "CREATE table if not exists cliente ( "
				+ "id integer not null primary key, " 
				+ "nome varchar(255) not null unique, "
				+ "nascimento date not null "
				+ ")";		
		Statement statement = conex�o.createStatement();
		statement.execute(sql);
		statement.close();
		
	}

}






