package io.vertx.howtos.protobuf.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler ;
import io.vertx.ext.web.impl.RouterImpl;

import java.io.IOException;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
public class ServerVerticle extends AbstractVerticle {
  public void start(Future<Void> fut) {
    // you will need to allow outbound and inbound to allow eventbus communication.

  }
}
