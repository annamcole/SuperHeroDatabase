// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class testSearchByScore {

  @Test
  public void test() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      // set the input stream to our input (with an x to test of the program exists)
      String input = "p" + System.lineSeparator() + "GOOD"
          + System.lineSeparator() + "400" + System.lineSeparator() + "600" + System.lineSeparator()
          + "x" + System.lineSeparator() + "x" + System.lineSeparator();
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      // set the output to the stream captor to read the output of the front end
      System.setOut(new PrintStream(outputStreamCaptor));
      // instantiate when front end is implemented

      FrontEnd frontend = new FrontEnd();
      Backend backend = new Backend(new StringReader(
          "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
              + "Martian Manhunter,good,100,100,96,100,100,85,581\n"
              + "Match,bad,75,95,83,85,90,70,498\n" + "General Zod,bad,94,100,96,100,94,95,579\n"
              + "Silver Surfer,good,63,100,84,1010,100,32,480"));
      
      frontend.run(backend);
      
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      // add all tests to this method
      
      String appOutput = outputStreamCaptor.toString();

      assertTrue(appOutput.contains("Martian Manhunter"));
      assertTrue(appOutput.contains("Silver Surfer"));
      
      outputStreamCaptor = null; 
      inputStreamSimulator = null; 

    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
      // test failed
    } finally {
      System.setOut(null);
      System.setIn(null);
    }
  }

}
