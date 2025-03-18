package com.lprevidente.ddd_example.pipeline;

public interface Pipeline {
  /**
   * Sends a command to the pipeline and returns a result
   * 
   * @param command The command to send
   * @param <R> The result type
   * @param <C> The command type
   * @return The result of the command execution
   */
  <R, C extends Command<R>> R send(C command);
}
