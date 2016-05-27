package com.vccorp.bigdata.server;

import com.vccorp.bigdata.LRUCache.LRUCache;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class MatchingCPIServer {

	public static LRUCache lruCache;

	public static UserAgentHandle handler;

	public static UserAgentService.Processor processor;

	public static void main(String[] args) {

		lruCache = new LRUCache(10000);

		try {
			handler = new UserAgentHandle();
			processor = new UserAgentService.Processor(handler);

			Runnable simple = new Runnable() {
				public void run() {
					simple(processor);
				}
			};

			new Thread(simple).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static void simple(UserAgentService.Processor processor) {
		
		try {
			
			TServerTransport serverTransport = new TServerSocket(8888);
			TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

			System.out.println("Starting the server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
