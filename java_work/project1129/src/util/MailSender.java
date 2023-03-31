package util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

//구글이 제공해주는 구글메일서버를 이용한 자바 메일발송
public class MailSender {
	String myAccountMail="alwjs1221@gmail.com";  //내가 사용중인 Gmail 주소
	String pass="Urwwzajlkcotayjj";
	
	public void sendMail(String to) {
		String subject="함진희의 페이보릿";  //메일제목
		String from="alwjs1221@naver.com";  //보낸 사람 이메일 
		String fromName="함진희 러버";  //보낸 사람 
		//String to="96rlarjsdn@gmail.com";  //받을 사람 이메일 ,(콤마로 다수의 메일 가능)
		
		StringBuffer content=new StringBuffer();
		content.append("<h1>♥함진희 사랑단♥</h1>");
		content.append("<p>우유빛깔 함진희 사랑해요 함진희</p>");
		
		//메일 설정
		Properties props=new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //메일 보낼 때 쓰는 프로토콜  //Gmail사용
		props.put("mail.smtp.port", "587"); //포트번호 설정
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable", "true"); //TLS사용
		
		//구글 메일을 사용하기 위한 인증절차
		//JAVA 메일 api에서 지원하는 Session 객체 생성
		Session mailSession=Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountMail, pass);
			}
		});
		//보내기 위한 세팅
		MimeMessage message=new MimeMessage(mailSession);
		
		//보내는 사람, 받는 사람 정보 세팅
		try {
			message.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName, "UTF-8", "B")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);  //메일 제목 설정
			//HTML 형식으로 보냄
			message.setContent(content.toString(), "text/html;charset=UTF-8");
			message.setSentDate(new Date());  //보낸 날짜
			
			//전송하기
			Transport trans=mailSession.getTransport("smtp");
			trans.connect(myAccountMail, pass);  //fromName, pass
			trans.sendMessage(message, message.getAllRecipients());
			trans.close();
			
			System.out.println("메일 발송 성공");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}	
	}	
	/*
	public static void main(String[] args) {
		MailSender sender=new MailSender();
		sender.sendMail();
	}
	*/
}
