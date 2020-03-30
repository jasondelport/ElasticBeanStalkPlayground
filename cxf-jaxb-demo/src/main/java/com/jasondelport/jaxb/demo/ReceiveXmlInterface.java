package com.jasondelport.jaxb.demo;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

// serviceName -> Service name of the Web Service. Maps to the <wsdl:service> element in the WSDL file.
// endpointInterface -> Fully qualified name of an existing service endpoint interface file.
// If you specify this attribute, it is assumed that you have already created the endpoint
// interface file and it is in your CLASSPATH.

// maps to xmlns:service="http://demo.jaxb.jasondelport.com/ in the soap message"

@WebService //@WebService(endpointInterface = "com.jasondelport.jaxb.demo.ReceiveXmlInterface")
public interface ReceiveXmlInterface {

	// need to match the configuration info in the soap message
	// operationName -> Name of the operation. Maps to the <wsdl:operation> element in the WSDL file.
	// action -> The action for this operation. For SOAP bindings, the value of this attribute determines
	// the value of the SOAPAction header in the SOAP messages.

	// maps to <service:deliveryupdate> in the soap message

	@WebMethod // @WebMethod(operationName = "deliveryupdate", action = "http://demo.jaxb.jasondelport.com/deliveryupdate")
	String deliveryupdate(@WebParam(name = "Message") final Message message);

	// maps to <service:deliverysend> in the soap message

	@WebMethod // @WebMethod(operationName = "deliverysend", action = "http://demo.jaxb.jasondelport.com/deliverysend")
	String deliverysend(@WebParam(name = "Message") final Message message);
}
