//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.12-b141219.1637 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2017.10.29 时间 07:17:32 下午 EDT 
//


package edu.stevens.cs548.clinic.service.dto;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (edu.stevens.cs548.clinic.service.dto.DateAdapter.parseDate(value));
    }

    public String marshal(Date value) {
        return (edu.stevens.cs548.clinic.service.dto.DateAdapter.printDate(value));
    }

}
