package com.ariso.timeserver.vertx;

import io.netty.buffer.ByteBuf;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.streams.Pump;

public class TimeServer extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		
		vertx.createNetServer().connectHandler(sock -> {
		 			
			Buffer buffer  = Buffer.factory.buffer();
			buffer.appendInt( (int) (System.currentTimeMillis() / 1000L + 2208988800L) );
		 sock.write(buffer);
		 sock.close();
		}).listen(8888);
		 
	}

 
 
	public static void main(String[] args) throws InterruptedException {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new TimeServer());
		vertx.deployVerticle(new TimeClient());
		Thread.sleep(5000);
		vertx.deployVerticle(new TimeClient());
	}

}