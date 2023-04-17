package src;

import java.security.NoSuchAlgorithmException;
import src.abl.ProcessAbl;

public class SubAppRunner {

  private static ProcessAbl processAbl = new ProcessAbl();

  public static void main(String[] args) throws NoSuchAlgorithmException {
    processAbl.processStart();
  }
}