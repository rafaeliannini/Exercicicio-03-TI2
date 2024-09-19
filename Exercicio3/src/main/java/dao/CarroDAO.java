package dao;

import java.sql.*;
import model.Carro;

public class CarroDAO {
	private Connection conexao;
	
	public CarroDAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "Carros";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "postgres";
		String password = "pao123";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirCarro(Carro carro) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO carro (codigo, marca, modelo, ano, placa, preco) "
					       + "VALUES ("+carro.getCodigo()+ ", '" + carro.getMarca() + "', '"  
					       + carro.getModelo() + "', '" + carro.getAno() + "', '" + carro.getPlaca() +  "', '" + carro.getPreco() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarCarro(Carro carro) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE carro SET marca = '" + carro.getMarca() + "', modelo = '"  
				       + carro.getModelo() + "', ano = '" + carro.getAno() + "', placa = '"  
					   + carro.getPlaca() + "', preco = '" + carro.getPreco()  + "'"
					   + " WHERE codigo = " + carro.getCodigo();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirCarro(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM carro WHERE codigo = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Carro getCarro(int codigo) {
	    Carro carro = null;
	    try {
	        Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet rs = st.executeQuery("SELECT * FROM carro WHERE codigo = " + codigo);
	        if (rs.next()) {
	            carro = new Carro(rs.getInt("codigo"), rs.getString("marca"), rs.getString("modelo"), rs.getInt("ano"), rs.getString("placa"), rs.getFloat("preco"));
	        }
	        st.close();
	    } catch (SQLException e) {
	        System.err.println(e.getMessage());
	    }
	    return carro;
	}

	public Carro[] getCarros() {
		Carro[] carros = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM carro");		
	         if(rs.next()){
	             rs.last();
	             carros = new Carro[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	            	 carros[i] = new Carro(rs.getInt("codigo"), rs.getString("marca"), 
	            	 rs.getString("modelo"), rs.getInt("ano"), rs.getString("placa"), rs.getFloat("preco"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carros;
	}
}