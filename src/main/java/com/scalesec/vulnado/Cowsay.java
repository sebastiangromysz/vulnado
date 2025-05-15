package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.util.logging.Logger;
import java.io.InputStreamReader;

public class Cowsay {
  private static final Logger LOGGER = Logger.getLogger(Cowsay.class.getName());
  public static String run(String input) {
  private Cowsay() {}
    ProcessBuilder processBuilder = new ProcessBuilder();
    String cmd = "/usr/games/cowsay '" + input + "'";
    LOGGER.info(cmd);
    String sanitizedInput = input.replaceAll("[^a-zA-Z0-9]", "");

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      LOGGER.severe("An error occurred while running the command: " + e.getMessage());
    }
    return output.toString();
  }
}
