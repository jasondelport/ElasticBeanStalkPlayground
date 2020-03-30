package com.jasondelport.jaxb.demo;

public class ReceiveXml implements ReceiveXmlInterface {

	@Override
	public String deliveryupdate(Message message) {
		return message.getKind();
	}

	@Override
	public String deliverysend(Message message) {
		return "KIND -> " + message.getKind();
	}

}
