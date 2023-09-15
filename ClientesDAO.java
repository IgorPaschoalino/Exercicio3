package dao;

import model.Clientes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ClientesDAO extends DAO {	
	public ClientesDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Clientes Clientes) {
		boolean status = false;
		try {
			String sql = "INSERT INTO Clientes (nome, sexo, idade, cidade) "
		               + "VALUES ('" + Clientes.getNome() + "', "
		               + Clientes.getSexo() + ", " 
		               + Clientes.getIdade() + ", "
		               + Clientes.getCidade() + ");";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Clientes get(int id) {
		Clientes Clientes = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Clientes WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 Clientes = new Clientes(rs.getInt("id"), rs.getString("nome"), rs.getString("sexo"), 
	                				   rs.getInt("idade"), 
	        			               rs.getString("cidade"));
	        			              
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return Clientes;
	}
	
	
	public List<Clientes> get() {
		return get("");
	}

	
	public List<Clientes> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Clientes> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Clientes> getOrderBySexo() {
		return get("sexo");		
	}
	
	
	private List<Clientes> get(String orderBy) {
		List<Clientes> MeusClientes = new ArrayList<Clientes>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Clientes" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Clientes p = new Clientes(rs.getInt("id"), rs.getString("nome"), rs.getString("sexo"), 
	        			                rs.getInt("idade"), rs.getString("Cidade"));
	        			           
	            MeusClientes.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return MeusClientes;
	}
	
	
	public boolean update(Clientes Clientes) {
		boolean status = false;
		try {  
			String sql = "UPDATE Clientes SET nome = '" + Clientes.getNome() + "', "
					   + "sexo = " + Clientes.getSexo() + ", " 
					   + "idade = " + Clientes.getIdade() + ","
					   + "cidade = ?, " 
					   + "datavalidade = ? WHERE id = " + Clientes.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(Clientes.getCidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM Clientes WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}