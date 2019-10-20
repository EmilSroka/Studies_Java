package uLab1;

import java.io.*;
/**
 *
 * @author Szymon
 */
public class LoginProgram {
    public static void main(String[] argv){
      Login log = new Login ("ala", "makota");
	  try{
		InputStreamReader rd = new InputStreamReader(System.in);
		BufferedReader bfr = new BufferedReader(rd);

        System.out.print("Podaj login: ");
		String login = bfr.readLine();
		System.out.print("Podaj haslo: ");
        String haslo = bfr.readLine();

        if (log.check(login, haslo)) {
          System.out.println("OK");
        } else {
          System.out.println("Niepoprawny login lub haslo");
        }
	  }catch(IOException e){e.printStackTrace();}

    }
}
