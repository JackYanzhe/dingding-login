package dingding.aes;

import com.alibaba.fastjson.JSONObject;

import dingding.DingTalkConstant;

public class MainTest {

	
	public static void main(String[] args) throws Exception {
		
		//setCallBackUrl();
		
		 /**url中的签名**/
	       String msgSignature = "111108bb8e6dbce3c9671d6fdb69d15066227608 ";
	       
	       //String msgSignature = "5a65ceeef9aab2d149439f82dc191dd6c5cbe2c0";
	       
	       /**url中的时间戳**/
	       String timeStamp = "1783610513";
//	       String timeStamp = "1445827045067";
	       
	       /**url中的随机字符串**/
	       String nonce = "380320111";
	       
	      // String nonce = "nEXhMP4r";
	       
	       String encrypt="1ojQf0NSvw2WPvW7LijxS8UvISr8pdDP+rXpPbcLGOmIBNbWetRg7IP0vdhVgkVwSoZBJeQwY2zhROsJq/HJ+q6tp1qhl9L1+ccC9ZjKs1wV5bmA9NoAWQiZ+7MpzQVq+j74rJQljdVyBdI/dGOvsnBSCxCVW0ISWX0vn9lYTuuHSoaxwCGylH9xRhYHL9bRDskBc7bO0FseHQQasdfghjkl";
			
	       	DingTalkEncryptor dingTalkEncryptor = null;
			String plainText = null;
			try {
				dingTalkEncryptor = new DingTalkEncryptor(DingTalkConstant.TOKEN,DingTalkConstant.ENCODING_AES_KEY,"suite4xxxxxxxxxxxxxxx");
				plainText = dingTalkEncryptor.getDecryptMsg(msgSignature, timeStamp, nonce, encrypt);
			} catch (DingTalkEncryptException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
	       /**对从encrypt解密出来的明文进行处理**/
			JSONObject plainTextJson = JSONObject.parseObject(plainText);	
			
		
			
			String eventType = plainTextJson.getString("EventType");
		    System.out.println(eventType);
		
		
	}


	
	
	

	
   
	
	
}
