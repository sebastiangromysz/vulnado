package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

  private Cowsay() { }
public class Cowsay {
  public static String run(String input) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    String cmd = "/usr/games/cowsay '" + input + "'";
    logger.info(cmd);
    Logger logger = Logger.getLogger(Cowsay.class.getName());
    // Ensure the PATH used to find this command includes only what you intend.

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      // Ensure this debug feature is deactivated before delivering the code in production.
    }
    return output.toString();
  }
}
