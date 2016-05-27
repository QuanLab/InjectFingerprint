package com.vccorp.bigdata.client;

import com.vccorp.bigdata.server.UserAgentService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


public class MatchingCPIClient {

	public static void main(String[] args) {

		try {
			TTransport transport;
			transport = new TSocket("localhost", 8888);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			UserAgentService.Client client = new UserAgentService.Client(protocol);

			perform(client);

			transport.close();
		} catch (TException x) {
			x.printStackTrace();
		}
	}

	private static void perform(UserAgentService.Client client) throws TException {

		String userAgent="Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D201 Safari/9537.53";
		String product = client.multiply(userAgent);
		System.out.println("Parsed...............");
		String[] splits=product.split("\t");

		for (String str:splits){
			System.out.println(str);
		}
	}

	public static String getUserAgentParsed(String userAgentString) throws TException{

		try {
			TTransport transport;
			transport = new TSocket("localhost", 8888);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			UserAgentService.Client client = new UserAgentService.Client(protocol);

			String userAgentParsed = client.multiply(userAgentString);

			transport.close();

			return  userAgentParsed;

		} catch (TException x) {
			x.printStackTrace();
			return null;
		}
	}
}
