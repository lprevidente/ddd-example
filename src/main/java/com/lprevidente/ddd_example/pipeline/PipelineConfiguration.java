package com.lprevidente.ddd_example.pipeline;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PipelineConfiguration {

  @Bean
  Pipeline pipeline(ObjectProvider<Command.Handler> commandHandlers) {
    return new PipelineImpl().with(commandHandlers::stream);
  }
}
