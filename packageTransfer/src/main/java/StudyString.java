import java.util.Arrays;//在声明包下面写
import java.util.StringTokenizer;

/**
 * 字符串
 */
public class StudyString{
	public static void main(String[] args)throws Exception{
		
		/////////////////////String不可变字符串////////////////////////
		//构建字符串
		String str1 = "abc";
		String str2 = "Abc";
		String str3 = new String("abc");
		String str4 = new String("abc");	
		String str5 = str3;
		
		//字符串的值比较和引用比较 ==（引用比较）equals(值比较)
		System.out.println("str1 == str2:" + (str1 == str2));//false
		System.out.println("str2 == str3:" + (str2 == str3));//false
		System.out.println("str1 equals str2:" + str1.equals(str2));//false
		System.out.println("str2 equals str3:" + str2.equals(str3));//false
		System.out.println("str3 == str5:" + (str3 == str5));//true
		//equalsIgnoreCase 忽略大小写比较值
		System.out.println("str2 equalsIngoreCase str3:" + str2.equalsIgnoreCase(str3));
		
		//查看对象的地址（hashcode）十进制的地址
		int str3Hashcode = str3.hashCode();
		int str5Hahscode = str5.hashCode();
		System.out.println("str3Hashcode:" + str3Hashcode + "\tstr5Hahscode:" + str5Hahscode);
		
		//把堆中的字符串转到常量池里面
		System.out.println("str1 == str3:" + (str1 == str3.intern()));
		
		String str = "abfljabdslabkfj";
		int strLength = str.length();
		System.out.println("strLength:" + strLength);
		
		//通过索引取得字符
		char ch = str.charAt(2);
		System.out.println("ch:" + ch);
		
//		ch = str.charAt(15);//Exception in thread "main" java.lang.StringIndexOutOfBoundsException
//		System.out.println("ch:" + ch);
		//输出字符串中所有的字符
		for(int i = 0; i < str.length(); i ++){
			ch = str.charAt(i);
			System.out.print(ch + "\t");	
		}
		System.out.println();
		
		//取的字符所在的索引
		int firstIndex = str.indexOf('b');
		System.out.println("firstIndex:" + firstIndex);
		//取得指定索引之后的字符的索引
		int middleIndex = str.indexOf('b', 3);
		System.out.println("middleIndex:" + middleIndex);
		//取得最后的字符对应的索引
		int lastIndex = str.lastIndexOf('b');
		System.out.println("lastIndex:" + lastIndex);
		//查不到返回-1
		int xIndex = str.indexOf('x');
		System.out.println("xIndex:" + xIndex);
		
		int index = -1;
		int location = 0;
		//取得在str中所有的‘b’的索引
		while((index = str.indexOf('b', location)) != -1){
			location = index + 1;//1 是字符的长度
			System.out.print(index + "\t");
		}
		System.out.println();
		
		//查找字符串ab在str中的索引
		int abIndex = str.indexOf("ab", 2);
		System.out.println("abIndex:" + abIndex);
		//练习：取得在str中所有的“ab”的索引
		
		str = "ABadfDF546asd";
		//变成全小写
		String lowerStr = str.toLowerCase();
		System.out.println("lowerStr:" + lowerStr);
		
		//变成全大写
		String upperStr = str.toUpperCase();
		System.out.println("upperStr:" + upperStr);
		
		String resultStr = "";
		//翻转大小写
		for(int i = 0; i < str.length(); i ++){
			char currentCh = str.charAt(i);
			if(currentCh >= 'A' && currentCh <= 'Z'){
				currentCh += 32;
			}else if(currentCh >= 'a' && currentCh <= 'z'){
				currentCh -= 32;
			}
			resultStr += (char)currentCh;
		}
		System.out.println("resultStr:" + resultStr);
		
		resultStr = converStr(str);
		System.out.println("resultStr:" + resultStr);
		
		str = " skdjf sdlfkj ";
		str = str.trim();//剪掉字符串两边的空格
		System.out.println("str:" + str + "|");
		if(str != null && !str.trim().equals("")){//判空
			System.out.println("正常字符串");	
		}
		//练习：翻转大小写倒序输出
		
		//求子串
		str = "ABadfDF546asd";
		String subStr = str.substring(1,3);//截取从开始索引1 到结束索引3之前 的字符串
		System.out.println("subStr:" + subStr);
		
		subStr = str.substring(2);//截取从索引2一直到最后的字符串
		System.out.println("subStr:" + subStr);
		
		//concat 字符串连接 和 + 的作用一样
		str = "abc".concat("def");
		System.out.println("str:" + str);
		
		//判断一个字符串 以。。开始startsWith 
		boolean isStartsWith = str.startsWith("ba");
		System.out.println("isStartsWith:" + isStartsWith);
		
		//判断一个字符串 以。。开始endsWith 
		boolean isEndsWith = str.endsWith("ef");
		System.out.println("isEndsWith:" + isEndsWith);
		
		//替换
		str = "ABadfDF546asd";
		str = str.replace('a', 'b');//替换字符
		System.out.println("str:" + str);
		
		str = str.replaceAll("546","a");//替换字符串（正则表达式替换）
		System.out.println("str:" + str);
		
		//valueOf 把基本类型变成字符串
		str = String.valueOf(5);//"" + 5
		System.out.println("str:" + str);
		
		//字符串<->字符数组
		str = "abc巨和";
		
		//字符串转成字符数组
		char[] chArray = str.toCharArray();
		System.out.println("chArray:" + chArray);
		System.out.println("chArray length:" + chArray.length);
		for(int i = 0; i < chArray.length; i ++){//数组的长度不是方法是属性
			System.out.print(chArray[i] + "\t");
		}
		System.out.println();
		System.out.println(Arrays.toString(chArray));
		
		//从字符数组转成字符串
		String newStr = new String(chArray);
		System.out.println("newStr:" + newStr);
		
		//字符串<->字节数组
		str = "abc巨和";
		byte[] byteArray = str.getBytes();
		System.out.println("byteArray length:" + byteArray.length);
		System.out.println(Arrays.toString(byteArray));
		
		byteArray = str.getBytes("UTF-8");//未报告的异常 java.io.UnsupportedEncodingException；必须对其进行捕捉或声明以便抛出
		System.out.println(Arrays.toString(byteArray));
		newStr = new String(byteArray);
		System.out.println("newStr:" + newStr);
		
		newStr = new String(newStr.getBytes("GBK"), "UTF-8");//转码 把GBK的字符串转成UTF-8编码的字符串
		System.out.println("newStr:" + newStr);
		
		//字符串切割 a,b,c,d
		str = "a,b,c,d";
		String[] strArray = str.split(",");
		for(int i = 0; i < strArray.length; i ++){
			System.out.println(strArray[i]);
		}
		System.out.println(Arrays.toString(strArray));
		
		//参数1：想分割的字符串 参数2：以什么分割 参数3：是不是保留分隔符
		for(java.util.StringTokenizer st = new java.util.StringTokenizer(str, ",", false);st.hasMoreTokens();){
			String subStri = st.nextToken();
			System.out.println("subStri:" + subStri);
		}
		
		//zhangsan,23,1:lisi,22,2:wangwu,24,1
		str = "zhangsan,23,1:lisi,22,2:wangwu,24,1";
		for(StringTokenizer st = new StringTokenizer(str, ":", false);st.hasMoreTokens();){
			
			String subStri = st.nextToken();//zhangsan,23,1
			System.out.println("subStri:" + subStri);	
			for(StringTokenizer sst = new StringTokenizer(subStri, ",", false); sst.hasMoreTokens();){
				String subSubStri = sst.nextToken();
				System.out.println("subSubStri:" + subSubStri);	
			}
		}
		
		//字符串比较
		System.out.println("a".compareTo("b"));//-1 直接相减
		System.out.println("abc".compareTo("abe"));//多位的相同位数的第一个不同字符相减的值
		System.out.println("abcdefg".compareTo("abcde"));//位数比较
		
		///////////////////////////包装类///////////////////////////////////
		//int<->Integer
		int a = 5;
		Integer inte = new Integer(a);//从int到Integer
		System.out.println("inte:" + inte);
		int b = inte.intValue();
		System.out.println("b:" + b);
		
		//int<->String
		a = 5;
		String stri = "" + 5;//int 转成字符串
		b = Integer.parseInt(stri);
		System.out.println("b:" + b);
//		b = Integer.parseInt("afd");//java.lang.NumberFormatException: For input string: "afd"
		b = Integer.parseInt("a", 16);//字符串转换成int
		System.out.println("b:" + b);
		b = Integer.parseInt("101", 3);
		System.out.println("b:" + b);
		
		a = 21;
		stri = Integer.toBinaryString(a);//把10进制的数转成2进制字符串
		System.out.println("stri:" + stri);
		stri = Integer.toHexString(a);//把10进制的数转成16进制字符串
		System.out.println("stri:" + stri);
		
		//ASCII码
		for(int i = 0; i < 128; i ++){
			System.out.println(i + "\t" + (char)i + "\t" + Integer.toHexString(i).toUpperCase());	
		}
		
		//Integer<->String 
		stri = "12";
		inte = new Integer(stri);	//把字符串转成Integer
		System.out.println("inte:" + inte);
		String stri1 = inte.toString();//把Integer转成String
		System.out.println("stri1:" + stri1);
		inte = Integer.valueOf(stri1);//把字符串转成Integer
		System.out.println("inte:" + inte);
		
		//比较
		Integer i1 = new Integer("10");
		Integer i2 = new Integer("10");
		System.out.println(i1.hashCode());//对于Integer而言hashCode是查看值
		System.out.println(i2.hashCode());
		System.out.println(i1.equals(i2));//对于Integer而言equals是值比较
		
		/////////////////////////////StringBuffer可变字符串///////////////////////////////////////////////
		String st = new String("abc");
		System.out.println("st hashCode:" + Integer.toHexString(st.hashCode()).toUpperCase());
		st = st + "abc";
		System.out.println("st hashCode:" + Integer.toHexString(st.hashCode()).toUpperCase());
		
		//创建StringBuffer
		StringBuffer sb = new StringBuffer();
		System.out.println("sb:" + sb);
		System.out.println("sb:" + Integer.toHexString(sb.hashCode()).toUpperCase());
		sb.append("abc");//追加内容
		System.out.println("sb:" + sb);
		System.out.println("sb:" + Integer.toHexString(sb.hashCode()).toUpperCase());
		sb.append(5);
		sb.append(true);
		sb.append(3.5);
		System.out.println("sb:" + sb);
		System.out.println("sb:" + Integer.toHexString(sb.hashCode()).toUpperCase());
		sb.append("juhe")
			.append("It")
			.append("Hello")
			.append("world");
		System.out.println("sb:" + sb);
		System.out.println("sb:" + Integer.toHexString(sb.hashCode()).toUpperCase());
		
		//查看长度
	  int sbLength = sb.length();
	  System.out.println("sbLength:" + sbLength);
	  
	  //插入字符串
	  sb.insert(2, "JUHE");
	  System.out.println("sb:" + sb);
		System.out.println("sb:" + Integer.toHexString(sb.hashCode()).toUpperCase());
	  
	  StringBuffer sb1 = new StringBuffer();
	  int capacity =  sb1.capacity();
	  System.out.println("capacity:" + capacity); 
	  
	  sb1.append("abcdefgabcdefgabcdefg");//容量增加的时候是 = 原来的容量*2 + 2
	  capacity =  sb1.capacity();
	  System.out.println("capacity:" + capacity);
	  
 		//String<->StringBuffer
 		String s = new String("abcd");
 		StringBuffer sb2 = new StringBuffer(s);//把String转成StringBuffer
 		System.out.println("sb2:" + sb2);
 		s = sb2.toString();//把StringBuffer转成String
 		System.out.println("s:" + s);
 		
 		sb2.delete(0,2);//删除从0到2之间的
 		System.out.println("sb2:" + sb2);
 		
 		////////////////////////Math////////////////////////////////
 		System.out.println("PI:" + Math.PI);
 		System.out.println(Math.abs(-4));//绝对值
 		System.out.println(Math.max(3, 8));//最大值
 		System.out.println(Math.min(3, 8));//最小值
 		System.out.println(Math.round(3.2));//四舍五入
 		System.out.println(Math.floor(3.2));//小于参数的最大整数
 		System.out.println(Math.ceil(3.2));//大于参数的最小整数
 		System.out.println(Math.random());// 0<=x<1 随机产生 
 		
 		//取得从1到54之间的随机整数
 		System.out.println((int)(Math.random() * 54) + 1);
	}  
	
	/**
	 * 翻转大小写
	 */
	static String converStr(String str){
		
		if(str == null || str.trim().equals("")){
			System.err.println("参数错误");	
			return null;
		}
		
		String resultStr = "";
		String lowerStr = str.toLowerCase();
		String upperStr = str.toUpperCase();
		for(int i = 0; i < str.length(); i ++){
			char ch = str.charAt(i);
			char lowerCh = lowerStr.charAt(i);
			char upperCh = upperStr.charAt(i);
			if(ch == lowerCh){
				resultStr += upperCh; 
			}else{
				resultStr += lowerCh;
			}
		}
		
		return resultStr;
	}
}

class Person{

}