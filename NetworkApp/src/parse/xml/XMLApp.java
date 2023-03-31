package parse.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/*
 자바언어에서 xml을 파싱하는 방법
 1) DOM 파싱 : 태그마다 1:1 대응하는 객체를 메모리에 생성하므로, 요즘같은 스마트폰이 활성화된 개발분야에서는 잘 사용하지 않음
 
 2) SAX 파싱 : 발견된 태그마다 이벤트를 발생시켜주는 파싱방식
 */
public class XMLApp {
	public static void main(String[] args) {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = factory.newSAXParser();
			String path = "C:/java_workspace2/data/NatworkApp/res/food.xml";
			try {
				saxParser.parse(new File(path), new MyHandler());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

	}
}
