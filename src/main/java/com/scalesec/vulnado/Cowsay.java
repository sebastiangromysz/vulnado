package com.scalesec.vulnado;

import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;

  private Cowsay() { }
public class Cowsay {
  public static String run(String input) {
    processBuilder.environment().put("PATH", "/usr/games:/usr/local/bin:/usr/bin:/bin");
    Logger logger = Logger.getLogger(Cowsay.class.getName());
    ProcessBuilder processBuilder = new ProcessBuilder();
    String sanitizedInput = input.replaceAll("[\\\\"'`$]", "");
    String cmd = "/usr/games/cowsay '" + sanitizedInput + "'";
    logger.info(cmd);
    processBuilder.command("bash", "-c", cmd);

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      logger.severe("An error occurred: " + e.getMessage());
    }
    return output.toString();
  }
}
