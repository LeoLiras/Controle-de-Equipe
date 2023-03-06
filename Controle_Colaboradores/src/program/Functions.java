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
		System.out.println("[1] - Listar colaborador cadastrado.");
		System.out.println("[2] - Cadastrar novo colaborador.");
		System.out.println("[3] - Deletar colaborador do sistema.");
		System.out.println("[4] - Atualizar dados do colaborador.");
		System.out.println("[5] - Sair.");
		
		int opt = input.nextInt();
		
		switch(opt){
		case 1:
			listarColaboradores();
			break;
		case 2:
			cadastrarColaboradores();
			break;
		case 3:
			deletarColaboradores();
			break;
		case 4:
			atualizarDados();
			break;
		case 5:
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
			}else {
				System.out.println("\nAinda não há colaboradores cadastrados.\n");
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
		}catch(Exception e) {
			e.printStackTrace();
			System.err.print("\nErro cadastrando o colaborador no banco de dados.\n");
			
			menu();
		}
	}
	
	public static void deletarColaboradores() {
		
	}
	
	public static void atualizarDados() {
		
	}
}
