package com.lprevidente.ddd_example.pipeline;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor
class PipelineImpl implements Pipeline {

  private Map<Class, Command.Handler> commandHandlers = Map.of();

  public PipelineImpl with(CommandHandlers commandHandlers) {
    Assert.notNull(commandHandlers, "Command handlers must not be null");
    this.commandHandlers =
        commandHandlers
            .supply()
            .collect(Collectors.toMap(Command.Handler::getClassType, Function.identity()));
    return this;
  }

  @SuppressWarnings("unchecked")
  public <R, C extends Command<R>> R send(C command) {
    Assert.notNull(command, "Command must not be null");

    if (!this.commandHandlers.containsKey(command.getClass()))
      throw new CommandHandlerNotFoundException(command);

    final var matchingHandler = commandHandlers.get(command.getClass());
    return (R) matchingHandler.handle(command);
  }
}
