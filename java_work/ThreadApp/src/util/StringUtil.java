package util;

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
	
	/* ctrl+shift+/
	 * public static void main(String[] args) { String result=getNumString(10);
	 * System.out.println(result); }
	 */
}
