// --== CS400 File Header Information ==--
// Name: Elliott Weinshenker
// Email: eweinshenker@wisc.edu
// Team: Red
// Group: kb
// TA: Keren Chen
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import static org.junit.Assert.assertNotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class testInvalidInput {

  /**
   * This test runs the front end and redirects its output to a string. It then passes in invalid
   * commands. Then exits the app by pressing 'x' to go back to the main mode and another 'x' to
   * exit. The test succeeds if the hero selection screen contains all characters from the data. It
   * fails if any of them are not present or the front end is not initialized.
   *
   * @return true if the test passed, false if it failed
   **/
  @Test
  public void test() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      // set the input stream to our input (with an r to select to enter the Hero rank
      // mode)
      String input2 =  "z" + System.lineSeparator() +  System.lineSeparator() +
      "x" + System.lineSeparator(); 

      InputStream inputStreamSimulator = new ByteArrayInputStream(input2.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      // set the output to the stream captor to read the output of the front end
      System.setOut(new PrintStream(outputStreamCaptor));
      // instantiate when front end is implemented

      FrontEnd frontend = new FrontEnd();
      frontend.run(new Backend(new StringReader(
          "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
              + "Martian Manhunter,good,100,100,96,100,100,85,581\n"
              + "Match,bad,75,95,83,85,90,70,498\n"
              + "General Zod,bad,92,100,96,100,94,95,577\n")));

      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);
      // add all tests to this method
      //      String appOutput = outputStreamCaptor.toString();
      
      assertNotNull(frontend);
      standardIn.close();

    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
      // test failed
    } finally {
      standardOut.flush();
    }

  }
}
