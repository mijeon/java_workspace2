package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	//넘겨받은 숫자가 1자리수이면, 앞에 0붙이기
	//누군가가 이 메서드를 호출하면 처리결과를 반환받음
	public static String getNumString(int num) {
		String str=null;
		if(num<10) {  //한자리수
			str="0"+num;  //05
		}else {  //두자리수
			str=Integer.toString(num);  //Wrapper적용
		}
		return str;
	}
	/*----------------------------------------------------
			확장자 추출하여 반환받기
	 ----------------------------------------------------*/
	
	public static String getExtend(String filename) {
		//String filename="a.aa.png";
		int lastIdex=filename.lastIndexOf(".");
		System.out.println(lastIdex);
		return filename.substring(lastIdex+1, filename.length());
	}
	
	
	
	/*
	 * public static void main(String[] args) {
	 * System.out.println(getExtend("aa.aa.aa.a.a.png")); }
	 */
	
//	public static void main(String[] args) { 
//		String result=getNumString(10); 
//		System.out.println(result); 
//	}
	 
	
	
/*----------------------------------------------------
		비밀번호 암호화 하기
		자바의 보안과 관련된 기능을 지원하는 api가 모여있는 패키지가 
		java.security임
 ----------------------------------------------------*/
	public static String getCovertedPass(String pass) {
		//암호화 객체
		StringBuffer hexString=null;
		try {
			MessageDigest digest=MessageDigest.getInstance("SHA-256");
			byte[] hash=digest.digest(pass.getBytes("UTF-8"));  //(암호화대상)
			
			 //String은 불변임 따라서 그 값이 변경될 수 없음
			//따라서 String 객체는 반복문 등에서 반복문 횟수가 클 경우 절대 누적식을 사용해서는 안됨
			//해결책) 변경가능한 문자열 객체를 지원하는 StringBuffer, StringBuilder 등을 활용
			hexString=new StringBuffer();   //StringBuffer는 String이 아님 그러므로 = 사용 불가
			
			for(int i=0;i<hash.length;i++) {
				String hex=Integer.toHexString(0xff&hash[i]);
				//System.out.println(hash[i]);
				System.out.println(hex);
				if(hex.length()==1) {
					hexString.append("0");
				}
				//hexString+=hex;
				hexString.append(hex);
			}		
			System.out.println(hexString.toString());
			System.out.println(hexString.length());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		return hexString.toString();
	}
	/*
	 * public static void main(String[] args) { String
	 * result=getCovertedPass("minzino"); System.out.println(result.length()); }
	 */
}

