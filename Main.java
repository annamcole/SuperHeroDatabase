// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.IOException;
import java.util.zip.DataFormatException;

public class Main {
  public static void main(String[] args) {
//    while (args.length != 1) {
//      System.out.println("Usage: find pattern");
//    }
    String csvFile = "characters_stats.csv";//args[0];

    try {
      // TODO: Uncomment when backend integration complete
      Backend backend = new Backend(csvFile);
      FrontEnd frontend = new FrontEnd();
      frontend.run(backend);
    } catch (IOException ioe) {
      // TODO Auto-generated catch block
      System.out.println("There was an issue loading the character file.");
      ioe.printStackTrace();
    } catch (DataFormatException dfe) {
      System.out.println("There was an issue loading the character file.");
      dfe.printStackTrace();
    } // pass csv filepath to backend in constructor

  }
}


