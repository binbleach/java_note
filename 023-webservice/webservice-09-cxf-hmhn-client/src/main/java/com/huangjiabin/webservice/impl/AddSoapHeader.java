/*
 * Copyright 2019 the original author or authors.
 */
package com.huangjiabin.webservice.impl;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

/**
 * webservice调用请求头封装
 * @author xuliang
 * @mail xuliang@yinhai.com
 * @date 2019/5/9
 * @time 20:05
 * @since 1.0
 */
public class AddSoapHeader extends AbstractSoapInterceptor {

    /**命名空间*/
    private String nameSpace;
    /**请求头构造参数*/
    private Map<String, String> soapParams;

    public AddSoapHeader(String nameSpace, Map<String, String> soapParams) {
        super(Phase.WRITE);
        this.nameSpace = nameSpace;
        this.soapParams = soapParams;
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        QName qname = new QName("RequestSOAPHeader");
        Document doc = DOMUtils.createDocument();
        Element root = doc.createElementNS(this.nameSpace, "in:system");
        soapParams.forEach((k, v) -> {
            if (v != null) {
                Element tagElement = doc.createElement(k);
                tagElement.setTextContent(v);
                root.appendChild(tagElement);
            }
        });
        SoapHeader head = new SoapHeader(qname, root);
        List<Header> headers = soapMessage.getHeaders();
        headers.add(head);
    }
}
