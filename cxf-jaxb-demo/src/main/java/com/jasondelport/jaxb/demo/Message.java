package com.jasondelport.jaxb.demo;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "Message")
@XmlType(name = "Message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable {

	@XmlAttribute(name = "kind")
	String kind;

	//ignored items catch all
	@XmlAnyElement(lax = true)
	private List<Object> anything;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

}
