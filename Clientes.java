package model;

import java.io.Serializable;


public class Clientes implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private int idade;
	private String cidade;
	private String sexo;
	
	public Clientes() {
		id = -1;
		nome = "vazio";
		sexo = "";
		idade = 0;
		cidade = "";
	}

	public Clientes(int id, String nome, String sexo, int idade, String cidade) {
		setId(id);
		setNome(nome);
		setSexo(sexo);
		setIdade(idade);
		setCidade(cidade);
		
	}		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome.length() >= 3)
			this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
			this.sexo = sexo;
	}

	public int getIdade() {
		return idade;
	}
	
	public void setIdade(int idade) {
			this.idade = idade;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Clientes: " + nome + "   Sexo: " + sexo + "   idade: " + idade + "   cidade: "
				+ cidade;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Clientes) obj).getId());
	}	
}