package parse.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*JSON은 프로그래밍 언어가 아니라 단지 문자열에 불과함*/
public class JsonApp {
	public static void main(String[] args) {
		//자바는 JSON을 이해하지 못하기 때문에 문자열로 처리함
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"name\" : \"철수\",");
		sb.append("\"age\" : 29,");
		sb.append("\"hasPet\" : true,");
		sb.append("\"pets\" : [");
		sb.append("{");
		sb.append("\"name\":\"원조\",");
		sb.append("\"type\":\"고양이\"");
		sb.append("},");
		sb.append("{");
		sb.append("\"name\":\"마찌\",");
		sb.append("\"type\":\"고양이\"");
		sb.append("}");
		sb.append("]");
		sb.append("}");	
			
		System.out.println(sb.toString());
		//위의 문자열을 실제 객체처럼 사용하려면, 문자열을 객체화시키는 
		//과정이 필요함 즉 해석이 필요한데, 프로그래밍 분야에서는 이러한 
		//과정을 "파싱한다"라고 함
		JSONParser jsonParser=new JSONParser();  
		
		//문자열에 불과했던 데이터를 실제 객체로 변환함
		try {
			Object obj= jsonParser.parse(sb.toString());
			JSONObject json=(JSONObject)obj;
			
			//json 표기값은 사길 Map이므로 key 값에 변수 적어줌
			System.out.println(json.get("name")); 
			System.out.println(json.get("age")); 
			System.out.println(json.get("hasPet")); 
			
			JSONArray array=(JSONArray)json.get("pets");	
			System.out.println("반려동물의 수는 "+array.size());
			
			for(int i=0;i<array.size();i++) {
				JSONObject pat=(JSONObject)array.get(i);
				System.out.println(pat.get("name"));
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
}
