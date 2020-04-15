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
			Connection conexão = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/3sadsis2019",
					"postgres",
					"unicesumar");

			criarTabelaCliente(conexão);
			
			excluirTudo(conexão);
			
			inserirCliente(conexão, 1, "Guilherme Longhini", new Date());
			
			Calendar calendario = Calendar.getInstance();
			calendario.set(Calendar.YEAR, 1999);
			calendario.set(Calendar.MONTH, 3);
			calendario.set(Calendar.DAY_OF_MONTH, 30);
			Date nascimentoBruna = calendario.getTime();
			
			inserirCliente(conexão, 2, "Bruna Lamberti", nascimentoBruna);
			
			//UPDATE
			calendario.set(Calendar.YEAR, 2000);
			calendario.set(Calendar.MONTH, 02);
			calendario.set(Calendar.DAY_OF_MONTH, 16);
			nascimentoBruna = calendario.getTime();
			atualizarCliente(conexão, 2, "Bruna Lamberti", nascimentoBruna);
						
			System.out.println("Conectou!");
			conexão.close();
			System.out.println("Fechou!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fim.");
	}
	public static void atualizarCliente(Connection conexão, Integer id, String nome, Date nascimento) throws Exception {
		String sql = "UPDATE cliente "
				+ "set nome = ?, "
				+ "nascimento = ? "
				+ "where id = ?";
		PreparedStatement statement = conexão.prepareStatement(sql);
		
		statement.setInt(3, id);
		statement.setString(1, nome);
		statement.setDate(2, new java.sql.Date(nascimento.getTime()));
		
		statement.execute();
		statement.close();
		
	}
	
	public static void excluirTudo(Connection conexão) throws Exception {
		String sql = "DELETE from cliente";		
		Statement statement = conexão.createStatement();
		statement.execute(sql);
		statement.close();
	}
	
	public static void inserirCliente(Connection conexão, Integer id, String nome, Date nascimento) throws Exception {
		String sql = "INSERT into cliente (id, nome, nascimento) values (?,?,?)";
		PreparedStatement statement = conexão.prepareStatement(sql);
		
		statement.setInt(1, id);
		statement.setString(2, nome);
		statement.setDate(3, new java.sql.Date(nascimento.getTime()));
		
		statement.execute();
		statement.close();
		
	}
	
	public static void criarTabelaCliente(Connection conexão) throws Exception {
		String sql = "CREATE table if not exists cliente ( "
				+ "id integer not null primary key, " 
				+ "nome varchar(255) not null unique, "
				+ "nascimento date not null "
				+ ")";		
		Statement statement = conexão.createStatement();
		statement.execute(sql);
		statement.close();
		
	}

}






