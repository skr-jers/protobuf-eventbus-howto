package io.vertx.howtos.protobuf.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

public class MainVerticle extends AbstractVerticle {


  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    BridgeOptions opts = new SockJSBridgeOptions()
      .addOutboundPermitted(new PermittedOptions().setAddress("feed"));
    SockJSBridgeOptions bridgeOpts = new SockJSBridgeOptions()
      .addOutboundPermitted(new PermittedOptions().setAddress("feed"))
      .addInboundPermitted(new PermittedOptions().setAddress("feed"))
      .addOutboundPermitted(new PermittedOptions().setAddress("folios"))
      .addInboundPermitted(new PermittedOptions().setAddress("folios"));
    SockJSHandler ebHandler = SockJSHandler.create(vertx);
    Router bridgeRouter = ebHandler.bridge(bridgeOpts);

    bridgeRouter.route().handler(io.vertx.ext.web.handler.CorsHandler.create("*")
      .allowedMethod(io.vertx.core.http.HttpMethod.GET)
      .allowedMethod(io.vertx.core.http.HttpMethod.POST)
      .allowedMethod(io.vertx.core.http.HttpMethod.PUT)
      .allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
      .allowCredentials(true)
      .allowedHeader("Access-Control-Allow-Method")
      .allowedHeader("Access-Control-Allow-Origin")
      .allowedHeader("Access-Control-Allow-Credentials")
      .allowedHeader("Content-Type"));
    bridgeRouter.get("/").handler(req -> req.response().end("Hello Eventbus"));
    bridgeRouter.route("/eventbus/*").handler(ebHandler);
    bridgeRouter.route().handler(StaticHandler.create());

    // create the server with 8080 port to bind
    vertx.createHttpServer().requestHandler(bridgeRouter).listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
    // create the eventbus
    EventBus eb = vertx.eventBus();
    // publish messages in 1 sec. interval
    vertx.setPeriodic(20000l, t -> {
      String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(Date.from(Instant.now()));
      eb.send("feed", new JsonObject().put("now", timestamp).put("tittle", "Notificación desde servicio de notificaciones"));
    });
  }
}
