package io.vertx.howtos.protobuf.eventbus;

import io.vertx.core.AbstractVerticle;

public class ReceiverVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.eventBus().<GreetingRequest>consumer("greetings", msg -> {
      var request = msg.body();
      System.out.printf("Received request = %s (%d)%n", request.getName(), System.identityHashCode(request));
      var greeting = String.format("Hello %s", request.getName());
      var reply = GreetingReply.newBuilder().setMessage(greeting).build();
      System.out.printf("Sending reply = %s (%d)%n", reply.getMessage(), System.identityHashCode(reply));
      msg.reply(reply);
    });
    vertx.eventBus().consumer("actions", msg -> {
      var request = msg.body();
      System.out.printf("Received request = %s (%d)%n", request, System.identityHashCode(request));
      var action = String.format("Executing action: %s", request);
      var reply = GreetingReply.newBuilder().setMessage(action).build();
      System.out.printf("Sending reply = %s (%d)%n", reply.getMessage(), System.identityHashCode(reply));
      msg.reply(reply);
    });
  }
}
