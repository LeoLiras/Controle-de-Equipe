package program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Functions {
	
	static Scanner input = new Scanner(System.in);
	
	static void menu() throws InterruptedException {
		
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
	
	static Connection conectarDatabase() {
		String CLASSE_DRIVER = "com.mysql.jdbc.Driver";
		String USUARIO = "estudos";
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
	
	static void listarColaboradores() {
		
	}
	
	static void cadastrarColaboradores() throws InterruptedException {
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
		String data = input.nextLine();
		Date data_nascimento = Utils.stringToDate(data);
		System.out.println("\nInsira o E-mail do Colaborador: ");
		String email = input.nextLine();
		System.out.println("\nInsira a idade do Colaborador: ");
		int idade = input.nextInt();
		
		Membros colaborador = new Membros(nome, rg, cpf, cargo, endereco, data_nascimento, email, idade);
		
		menu();
	}
	
	static void deletarColaboradores() {
		
	}
	
	static void atualizarDados() {
		
	}
}
