package com.ariso.vertxsimcity00.msgtest;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
		vertx.deployVerticle("com.ariso.vertxsimcity00.msgtest.Receiver", res -> {
			if (res.succeeded()) {
				System.out.println("Receiver id is: " + res.result());
			} else {
				System.out.println("Receiver Deployment failed!");
			}
		});

		vertx.deployVerticle("com.ariso.vertxsimcity00.msgtest.Sender", res -> {
			if (res.succeeded()) {
				System.out.println("Sender Deployment id is: " + res.result());
			} else {
				System.out.println("Sender Deployment failed!");
			}
		});
	}
}
