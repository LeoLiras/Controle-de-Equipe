package program;

public class testes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Membros m1 = new Membros("Leo", "123", "123", "Analista", "Rua 2", Utils.stringToDate("07/02/2002"), "leo.com", 15);
		Membros m2 = new Membros("Leo", "123", "123", "Analista", "Rua 2", Utils.stringToDate("07/02/2002"), "leo.com", 15);
		
		
		System.out.println(m2.toString());
	}

}
