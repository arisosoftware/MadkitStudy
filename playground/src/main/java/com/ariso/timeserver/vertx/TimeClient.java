package com.ariso.timeserver.vertx;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.net.NetSocket;

public class TimeClient extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		vertx.createNetClient().connect(8888, "localhost", res -> {

			if (res.succeeded()) {
				NetSocket socket = res.result();

				socket.handler(buffer -> {
					long currentTimeMillis = (buffer.getUnsignedInt(0) - 2208988800L) * 1000L;
					;
					System.out.println(new Date(currentTimeMillis));

				});
				socket.close();

			} else {
				System.out.println("Failed to connect " + res.cause());
			}
		});
	}

}
