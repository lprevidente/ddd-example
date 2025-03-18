package com.lprevidente.ddd_example.pipeline;

import java.util.stream.Stream;

interface CommandHandlers {

  Stream<Command.Handler> supply();
}
