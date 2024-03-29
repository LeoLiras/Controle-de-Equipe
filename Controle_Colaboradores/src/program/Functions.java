package program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Functions {
	
	static Scanner input = new Scanner(System.in);
	
	public static void menu() throws InterruptedException, ParseException {
		
		System.out.println("\n========== Bem Vindo(a) =========");
		System.out.println("========= Controle de Equipe =========\n");
		System.out.println("Escolha uma opção: \n");
		System.out.println("[1] - Listar colaboradores cadastrados.");
		System.out.println("[2] - Buscar Colaborador");
		System.out.println("[3] - Cadastrar novo colaborador.");
		System.out.println("[4] - Deletar colaborador do sistema.");
		System.out.println("[5] - Atualizar dados do colaborador.");
		System.out.println("[6] - Sair.");
		
		int opt = input.nextInt();
		
		switch(opt){
		case 1:
			listarColaboradores();
			break;
		case 2:
			menuBuscarColaborador();
			break;
		case 3:
			cadastrarColaboradores();
			break;
		case 4:
			deletarColaboradores();
			break;
		case 5:
			atualizarDados();
			break;
		case 6:
			System.exit(0);
		default:
			System.out.println("Opção inválida.");
			Thread.sleep(3000);
			menu();
		}
	}
	
	public static Connection conectarDatabase() {
		String CLASSE_DRIVER = "com.mysql.cj.jdbc.Driver";
		String USUARIO = "root";
		String SENHA = "root123";
		String URL_SERVIDOR = "jdbc:mysql://localhost:3306/colaboradores?useSSL=false";
		
		try {
			Class.forName(CLASSE_DRIVER);
			return DriverManager.getConnection(URL_SERVIDOR, USUARIO, SENHA);
		}catch(Exception e) {
			if (e instanceof ClassNotFoundException) {
				System.out.println("\nVerifique o driver de conexão.\n");
			}else {
				System.out.println("\nVerifique se o servidor está ativo.\n");
				e.printStackTrace();
			}
			System.exit(-1);
			return null;
		}
	}
	
	public static void desconectarDatabase(Connection conexão) {
		if(conexão != null) {
			try {
				conexão.close();
			}catch(SQLException e) {
				System.out.println("\nNão foi possível desconectar do banco de dados.\n");
				e.printStackTrace();
			}
		}
	}
	
	public static void listarColaboradores() {
		String buscar = "SELECT * FROM colaboradores";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement colaboradores = conexão.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultado = colaboradores.executeQuery();
			
			int linhas_tabela;
			
			resultado.last();
			linhas_tabela = resultado.getRow();
			resultado.beforeFirst();
			
			if(linhas_tabela > 0) {
				System.out.println("\n============= Colaboradores ============");
				System.out.println("____________________________________________\n");
				
				while(resultado.next()) {
					System.out.println("ID: " + resultado.getInt(1));
					System.out.println("Nome: " + resultado.getString(2));
					System.out.println("RG: " + resultado.getString(3));
					System.out.println("CPF: " + resultado.getString(4));
					System.out.println("Cargo: " + resultado.getString(5));
					System.out.println("Endereço: " + resultado.getString(6));
					System.out.println("Data de Nascimento: " + resultado.getDate(7));
					System.out.println("Idade: " + resultado.getInt(8));
					System.out.println("Data de Cadastro: " + resultado.getDate(9));
					System.out.println("E-mail: " + resultado.getString(10));
					System.out.println("---------------------------------------------------");
				}
				
				Thread.sleep(2000);
				menu();
			}else {
				System.out.println("\nAinda não há colaboradores cadastrados.\n");
				Thread.sleep(2000);
				menu();
			}
			
			colaboradores.close();
			desconectarDatabase(conexão);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("\nErro cadastrando o produto.\n");
			System.exit(-1);
		}
	}
	
	public static void cadastrarColaboradores() throws InterruptedException, ParseException {
		input.nextLine();
		
		System.out.println("\n ========= Cadastro de Colaborador =========");
		
		System.out.println("Insira o nome completo do Colaborador: ");
		String nome = input.nextLine();
		System.out.println("\nInsira o RG do Colaborador: ");
		String rg = input.nextLine();
		System.out.println("\nInsira o CPF do Colaborador: ");
		String cpf = input.nextLine();
		System.out.println("\nInsira o Cargo do Colaborador: ");
		String cargo = input.nextLine();
		System.out.println("\nInsira o endereço do Colaborador: ");
		String endereco = input.nextLine();
		
		System.out.println("\nInsira a data de nascimento do Colaborador: ");
		String data_n = input.nextLine();
		
		DateFormat data = new SimpleDateFormat("dd/MM/yyyy");
		Date data_nascimento = data.parse(data_n);
		java.sql.Date data_nasc = new java.sql.Date(data_nascimento.getTime());
		
		System.out.println("\nInsira a idade do Colaborador: ");
		int idade = input.nextInt();
		input.nextLine();
		
		Date data_cadastro = new Date();
		java.sql.Date data_cadas = new java.sql.Date(data_cadastro.getTime());
		
		System.out.println("\nInsira o E-mail do Colaborador: ");
		String email = input.nextLine();
	
		//Membros colaborador = new Membros(nome, rg, cpf, cargo, endereco, data_nascimento, email, idade);
		
		String inserir_database = "INSERT INTO colaboradores (nome, rg, cpf, cargo, endereco, data_nascimento, idade, data_cadastro, email) "
				+ "						VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement inserir = conexão.prepareStatement(inserir_database);
			
			
			inserir.setString(1, nome);
			inserir.setString(2, rg);
			inserir.setString(3, cpf);
			inserir.setString(4, cargo);
			inserir.setString(5, endereco);
			inserir.setDate(6, (java.sql.Date) data_nasc);
			inserir.setInt(7, idade);
			inserir.setDate(8,(java.sql.Date) data_cadas);
			inserir.setString(9, email);
			
			inserir.executeUpdate();
			inserir.close();
			
			desconectarDatabase(conexão);
			
			System.out.println("\nColaborador cadastrado com sucesso.\n");
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.print("\nErro cadastrando o colaborador no banco de dados.\n");
			System.exit(-1);
		}
	}
	
	public static void deletarColaboradores() {
		String deletar = "DELETE FROM colaboradores WHERE id=?";
		String buscar = "SELECT * FROM colaboradores WHERE id=?";
		
		System.out.println("Insira o ID do colaborador a ser deletado: ");
		int id = input.nextInt();
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement colaborador = conexão.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			colaborador.setInt(1, id);
			ResultSet resultado = colaborador.executeQuery();
			
			int linha_tabela;
			resultado.last();
			linha_tabela = resultado.getRow();
			resultado.beforeFirst();
			
			if(linha_tabela > 0) {
				PreparedStatement delet = conexão.prepareStatement(deletar, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				delet.setInt(1, id);
				delet.executeUpdate();
				delet.close();
				
				desconectarDatabase(conexão);
				
				System.out.println("\nColaborador deletado com sucesso.\n");
				Thread.sleep(2000);
				menu();
			}else {
				System.err.println("\nNão há colaboradores com o ID informado.\n");
				Thread.sleep(2000);
				menu();
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("\nNão foi possível deletar o colaborador.\n");
			System.exit(-1);
		}
		
	}
	
	public static void atualizarDados() {
		input.nextLine();
		System.out.println("\nInsira o ID do colaborador.\n");
		int id = input.nextInt();
		
		String buscar = "SELECT * FROM colaboradores WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement colaborador = conexão.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			colaborador.setInt(1, id);
			ResultSet resultado = colaborador.executeQuery();
			
			int linha_tabela;
			resultado.last();
			linha_tabela = resultado.getRow();
			resultado.beforeFirst();
			
			if(linha_tabela > 0) {
				System.out.println("\nDados do Colaborador:\n");
				
				while(resultado.next()) {
					System.out.println("ID: " + resultado.getInt(1));
					System.out.println("Nome: " + resultado.getString(2));
					System.out.println("RG: " + resultado.getString(3));
					System.out.println("CPF: " + resultado.getString(4));
					System.out.println("Cargo: " + resultado.getString(5));
					System.out.println("Endereço: " + resultado.getString(6));
					System.out.println("Data de Nascimento: " + resultado.getDate(7));
					System.out.println("Idade: " + resultado.getInt(8));
					System.out.println("Data de Cadastro: " + resultado.getDate(9));
					System.out.println("E-mail: " + resultado.getString(10));
					System.out.println("---------------------------------------------------\n");
				}
				
				System.out.println("\nQual dado deseja atualizar?\n");
				System.out.println("[1] - Nome.");
				System.out.println("[2] - RG.");
				System.out.println("[3] - CPF.");
				System.out.println("[4] - Cargo.");
				System.out.println("[5] - Endereço.");
				System.out.println("[6] - Data de Nascimento.");
				System.out.println("[7] - Idade.");
				System.out.println("[8] - E-mail.");
				int opcao = input.nextInt();
				
				desconectarDatabase(conexão);
				
				switch(opcao){
				case 1:
					atualizarNome(id);
					break;
				case 2:
					atualizarRg(id);
					break;
				case 3:
					atualizarCpf(id);
					break;
				case 4:
					atualizarCargo(id);
					break;
				case 5:
					atualizarEndereco(id);
					break;
				case 6:
					atualizarDataNascimento(id);
					break;
				case 7:
					atualizarIdade(id);
					break;
				case 8:
					atualizarEmail(id);
					break;
				default:
					System.out.println("\nOpção inválida.\n");
					Thread.sleep(3000);
					menu();
				}
				
			}else {
				System.err.println("\nNão há colaboradores cadastrados com esse ID.\n");
				Thread.sleep(2000);
				menu();
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("\nNão foi possível atualizar dados do colaborador.\n");
			System.exit(-1);
		}
	}
	
	public static void atualizarNome(int id) {
		input.nextLine();
		
		System.out.println("\nInforme o novo nome do Colaborador: \n");
		String nome = input.nextLine();
		
		String update = "UPDATE colaboradores SET nome=? WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement upd = conexão.prepareStatement(update);
			
			upd.setString(1, nome);
			upd.setInt(2, id);
			
			upd.executeUpdate();
			upd.close();
			
			System.out.println("\nNome do colaborador atualizado com sucesso.\n");
			
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível realizar a atualização");
			System.exit(-1);
		}
	}
	
	public static void atualizarRg(int id) {
		input.nextLine();
		
		System.out.println("\nInforme o novo RG do Colaborador: \n");
		String rg = input.nextLine();
		
		String update = "UPDATE colaboradores SET rg=? WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement upd = conexão.prepareStatement(update);
			
			upd.setString(1, rg);
			upd.setInt(2, id);
			
			upd.executeUpdate();
			upd.close();
			
			System.out.println("\nRG do colaborador atualizado com sucesso.\n");
			
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível realizar a atualização");
			System.exit(-1);
		}
	}
	
	public static void atualizarCpf(int id) {
		input.nextLine();
		
		System.out.println("\nInforme o novo CPF do Colaborador: \n");
		String cpf = input.nextLine();
		
		String update = "UPDATE colaboradores SET cpf=? WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement upd = conexão.prepareStatement(update);
			
			upd.setString(1, cpf);
			upd.setInt(2, id);
			
			upd.executeUpdate();
			upd.close();
			
			System.out.println("\nCPF do colaborador atualizado com sucesso.\n");
			
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível realizar a atualização");
			System.exit(-1);
		}
	}
	
	public static void atualizarCargo(int id) {
		input.nextLine();
		
		System.out.println("\nInforme o novo cargo do Colaborador: \n");
		String cargo = input.nextLine();
		
		String update = "UPDATE colaboradores SET cargo=? WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement upd = conexão.prepareStatement(update);
			
			upd.setString(1, cargo);
			upd.setInt(2, id);
			
			upd.executeUpdate();
			upd.close();
			
			System.out.println("\nCargo do colaborador atualizado com sucesso.\n");
			
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível realizar a atualização");
			System.exit(-1);
		}
	}
	
	public static void atualizarEndereco(int id) {
		input.nextLine();
		
		System.out.println("\nInforme o novo endereço do Colaborador: \n");
		String endereco = input.nextLine();
		
		String update = "UPDATE colaboradores SET endereco=? WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement upd = conexão.prepareStatement(update);
			
			upd.setString(1, endereco);
			upd.setInt(2, id);
			
			upd.executeUpdate();
			upd.close();
			
			System.out.println("\nEndereço do colaborador atualizado com sucesso.\n");
			
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível realizar a atualização");
			System.exit(-1);
		}
	}
	
	public static void atualizarDataNascimento(int id) throws ParseException {
		input.nextLine();
		
		System.out.println("\nInsira a nova data de nascimento do Colaborador: ");
		String data_n = input.nextLine();
		
		DateFormat data = new SimpleDateFormat("dd/MM/yyyy");
		Date data_nascimento = data.parse(data_n);
		java.sql.Date data_nasc = new java.sql.Date(data_nascimento.getTime());
		
		String update = "UPDATE colaboradores SET data_nascimento=? WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement upd = conexão.prepareStatement(update);
			
			upd.setDate(1, data_nasc);
			upd.setInt(2, id);
			
			upd.executeUpdate();
			upd.close();
			
			System.out.println("\nData de nascimento do colaborador atualizado com sucesso.\n");
			
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível realizar a atualização");
			System.exit(-1);
		}
	}
	
	public static void atualizarIdade(int id) {
		input.nextLine();
		
		System.out.println("\nInforme a nova idade do Colaborador: \n");
		int idade = input.nextInt();
		
		String update = "UPDATE colaboradores SET idade=? WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement upd = conexão.prepareStatement(update);
			
			upd.setInt(1, idade);
			upd.setInt(2, id);
			
			upd.executeUpdate();
			upd.close();
			
			System.out.println("\nIdade do colaborador atualizado com sucesso.\n");
			
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível realizar a atualização");
			System.exit(-1);
		}
	}
	
	public static void atualizarEmail(int id) {
		input.nextLine();
		
		System.out.println("\nInforme o e-mail endereço do Colaborador: \n");
		String email = input.nextLine();
		
		String update = "UPDATE colaboradores SET email=? WHERE id=?";
		
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement upd = conexão.prepareStatement(update);
			
			upd.setString(1, email);
			upd.setInt(2, id);
			
			upd.executeUpdate();
			upd.close();
			
			System.out.println("\nE-mail do colaborador atualizado com sucesso.\n");
			
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possível realizar a atualização");
			System.exit(-1);
		}
	}
	
	public static void menuBuscarColaborador() throws InterruptedException, ParseException {
		System.out.println("\nEscolha uma opção:\n");
		System.out.println("[1] - CPF");
		System.out.println("[2] - RG");
		System.out.println("[3] - Nome");
		int opt = input.nextInt();
		
		switch(opt) {
		case 1:
			buscarCpf();
			break;
		case 2:
			buscarRg();
			break;
		case 3:
			buscarNome();
			break;
		default:
			System.out.println("\nOpção inválida.\n");
			Thread.sleep(3000);
			menu();
		}
	}
	
	public static void buscarCpf() {
		System.out.println("\nInsira o CPF do colaborador(somente números): ");
		int cpf = input.nextInt();
		
		String buscar = "SELECT * FROM colaboradores WHERE cpf=" + cpf;
		 
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement colaboradores = conexão.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultado = colaboradores.executeQuery();
			
			int linhas_tabela;
			
			resultado.last();
			linhas_tabela = resultado.getRow();
			resultado.beforeFirst();
			
			if(linhas_tabela > 0) {
				System.out.println("____________________________________________\n");
				
				while(resultado.next()) {
					System.out.println("ID: " + resultado.getInt(1));
					System.out.println("Nome: " + resultado.getString(2));
					System.out.println("RG: " + resultado.getString(3));
					System.out.println("CPF: " + resultado.getString(4));
					System.out.println("Cargo: " + resultado.getString(5));
					System.out.println("Endereço: " + resultado.getString(6));
					System.out.println("Data de Nascimento: " + resultado.getDate(7));
					System.out.println("Idade: " + resultado.getInt(8));
					System.out.println("Data de Cadastro: " + resultado.getDate(9));
					System.out.println("E-mail: " + resultado.getString(10));
					System.out.println("---------------------------------------------------");
				}
			}else {
				System.out.println("\nNão há colaboradores com o CPF informado.\n");
				menu();
			}
			
			colaboradores.close();
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("\nErro buscando o colaborador.\n");
			System.exit(-1);
		}
		
	}
	
	public static void buscarRg() {
		System.out.println("\nInsira o RG do colaborador(somente números e dígito, se houver): ");
		int rg = input.nextInt();
		
		String buscar = "SELECT * FROM colaboradores WHERE rg=" + rg;
		 
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement colaboradores = conexão.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultado = colaboradores.executeQuery();
			
			int linhas_tabela;
			
			resultado.last();
			linhas_tabela = resultado.getRow();
			resultado.beforeFirst();
			
			if(linhas_tabela > 0) {
				System.out.println("____________________________________________\n");
				
				while(resultado.next()) {
					System.out.println("ID: " + resultado.getInt(1));
					System.out.println("Nome: " + resultado.getString(2));
					System.out.println("RG: " + resultado.getString(3));
					System.out.println("CPF: " + resultado.getString(4));
					System.out.println("Cargo: " + resultado.getString(5));
					System.out.println("Endereço: " + resultado.getString(6));
					System.out.println("Data de Nascimento: " + resultado.getDate(7));
					System.out.println("Idade: " + resultado.getInt(8));
					System.out.println("Data de Cadastro: " + resultado.getDate(9));
					System.out.println("E-mail: " + resultado.getString(10));
					System.out.println("---------------------------------------------------");
				}
			}else {
				System.out.println("\nNão há colaboradores com o RG informado.\n");
				menu();
			}
			
			colaboradores.close();
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("\nErro buscando o colaborador.\n");
			System.exit(-1);
		}
	}
	
	public static void buscarNome() {
		input.nextLine();
		System.out.println("\nInsira o nome completo do colaborador: ");
		String nome = input.nextLine();
		
		String buscar = "SELECT * FROM colaboradores WHERE nome='" + nome + "'";
		 
		try {
			Connection conexão = conectarDatabase();
			PreparedStatement colaboradores = conexão.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultado = colaboradores.executeQuery();
			
			int linhas_tabela;
			
			resultado.last();
			linhas_tabela = resultado.getRow();
			resultado.beforeFirst();
			
			if(linhas_tabela > 0) {
				System.out.println("____________________________________________\n");
				
				while(resultado.next()) {
					System.out.println("ID: " + resultado.getInt(1));
					System.out.println("Nome: " + resultado.getString(2));
					System.out.println("RG: " + resultado.getString(3));
					System.out.println("CPF: " + resultado.getString(4));
					System.out.println("Cargo: " + resultado.getString(5));
					System.out.println("Endereço: " + resultado.getString(6));
					System.out.println("Data de Nascimento: " + resultado.getDate(7));
					System.out.println("Idade: " + resultado.getInt(8));
					System.out.println("Data de Cadastro: " + resultado.getDate(9));
					System.out.println("E-mail: " + resultado.getString(10));
					System.out.println("---------------------------------------------------");
				}
			}else {
				System.out.println("\nNão há colaboradores com o nome informado.\n");
				menu();
			}
			
			colaboradores.close();
			desconectarDatabase(conexão);
			Thread.sleep(2000);
			menu();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("\nErro buscando o colaborador.\n");
			System.exit(-1);
		}
	}
}
