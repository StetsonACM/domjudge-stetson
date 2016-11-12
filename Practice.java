import java.util.Scanner;

public class Practice {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		for(int i = 0; i < n; i++) {
			String line = in.nextLine();
			for(int j = 0; j < line.length(); j++) {
				if(line.charAt(j) >= 65 && line.charAt(j) <= 90) {
					System.out.print((char)(line.charAt(j) + 32));
				}
				else if(line.charAt(j) >= 97 && line.charAt(j) <= 122) {
					System.out.print((char)(line.charAt(j) - 32));
				}
				else {
					System.out.print(line.charAt(j));
				}
			}
			System.out.println();
		}
	}
}
