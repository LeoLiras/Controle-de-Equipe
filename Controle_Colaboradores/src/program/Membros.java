package program;

import java.util.Date;

public class Membros {
	private String nome, rg, cpf, cargo, endereco, email;
	private Date data_nascimento, data_cadastro;
	private int idade, matricula;
	private static int id = 0;
	
	public Membros(String nome, String rg, String cpf, String cargo, String endereco, Date data_nascimento, String email, int idade) {
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.cargo = cargo; 
		this.endereco = endereco;
		this.data_nascimento = data_nascimento;
		this.data_cadastro = new Date();
		this.email = email;
		this.idade = idade;
		
		this.matricula = Membros.id;
		Membros.id++;		
	}
	
	public int getMatricula() {
		return this.matricula;
	}
	
	public Date getDataCadastro() {
		return this.data_cadastro;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	void deletar() {
		this.nome = "null";
		this.rg="null";
		this.cpf="null";
		this.cargo="null";
		this.endereco="null";
	}
	
	public String toString() {
		return "Matrícula: " + this.getMatricula()
		+ "\nNome: " + this.getNome()
		+ "\nRG: " + this.getRg()
		+ "\nCPF: " + this.getCpf()
		+ "\nCargo: " + this.getCargo()
		+ "\nEmail: " + this.getEmail()
		+ "\nData de Nascimento: " + this.getData_nascimento()
		+ "\nIdade: " + this.getIdade()
		+ "\nEndereço: " + this.getEndereco()
		+ "\nData de Cadastro: " + this.getDataCadastro()
		+ "\n----------------------------------------------------------";
	}
}


