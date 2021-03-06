package com.ariso.timeserver.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	public static void main(String[] args) throws Exception {
		String host;// = args[0];
		int port;// = Integer.parseInt(args[1]);
		if (args.length > 0) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		} else {
			host = "127.0.0.1";
			port = 8888;
		}

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});

			// Start the client.
			ChannelFuture f = b.connect(host, port).sync(); // (5)

			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}

// 1 Bootstrap is similar to ServerBootstrap except that it's for non-server
// channels such as a client-side or connectionless channel.
// 2 If you specify only one EventLoopGroup, it will be used both as a boss
// group and as a worker group. The boss worker is not used for the client side
// though.
// 3 Instead of NioServerSocketChannel, NioSocketChannel is being used to create
// a client-side Channel.
// 4 Note that we do not use childOption() here unlike we did with
// ServerBootstrap because the client-side SocketChannel does not have a parent.
// 5 We should call the connect() method instead of the bind() method.
// As you can see, it is not really different from the the server-side code.
// What about the ChannelHandler implementation? It should receive a 32-bit
// integer from the server, translate it into a human readable format, print the
// translated time, and close the connection:
//