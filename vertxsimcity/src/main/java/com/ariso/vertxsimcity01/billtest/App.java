package com.ariso.vertxsimcity01.billtest;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

  /*
   * 
   * 房间内有n个人，每个人有n元。

从每个人手里拿出1元钱，凑够n元。

随机选中一些人(至少一个，最多n个），将这n元随机地发给选中的幸运儿。每个人可能分得的钱数，可能是1元，也可能是2元，还可能是3元，最多可能是n元。

https://how-to.vertx.io/single-page-react-vertx-howto/


   */
public class App {
	
	// C / G / R
	public static final String COMMUNITY = "RMBGameRoom";
	public static final String SIMU_GROUP = "RMBGroup";
	public static final String Player_ROLE = "player";
	public static final String Room_ROLE = "room";
	public static final String VIEWER_ROLE = "viewer";

	
	
	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
		vertx.deployVerticle("com.ariso.vertxsimcity01.msgtest.Receiver", res -> {
			if (res.succeeded()) {
				System.out.println("Receiver id is: " + res.result());
			} else {
				System.out.println("Receiver Deployment failed!");
			}
		});

		vertx.deployVerticle("com.ariso.vertxsimcity01.msgtest.Sender", res -> {
			if (res.succeeded()) {
				System.out.println("Sender Deployment id is: " + res.result());
			} else {
				System.out.println("Sender Deployment failed!");
			}
		});
	}
}
