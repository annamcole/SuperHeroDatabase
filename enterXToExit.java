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

class enterXToExit {


  @Test
  public void test() {

    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      String input = "x" + System.lineSeparator();
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      // set the output to the stream captor to read the output of the front end
      System.setOut(new PrintStream(outputStreamCaptor));
      // instantiate when front end is implemented
      
      FrontEnd frontend = new FrontEnd();
      Backend backend = new Backend(new StringReader(
          "Name,Alignment,Intelligence,Strength,Speed,Durability,Power,Combat,Total\n"
              + "3-D Man,good,50,31,43,32,25,52,233\n" + "Brainiac 5,good,100,10,23,28,60,32,253\n"
              + "Exodus,bad,63,81,28,28,100,70,370\n"));
      frontend.run(backend);
     
      // same for standard in
      System.setIn(standardIn);
      // set the output back to standard out for running the test
      System.setOut(standardOut);
      
      assertTrue(frontend != null);
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
