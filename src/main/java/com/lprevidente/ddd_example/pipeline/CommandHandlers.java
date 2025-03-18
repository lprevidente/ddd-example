package com.lprevidente.ddd_example.pipeline;

import java.util.stream.Stream;

public interface CommandHandlers {

  Stream<Command.Handler> supply();
}
