package com.itface.star.system.util.guava;

import java.util.List;

import com.google.common.io.CharStreams;

public class CharStreamsAndByteStreams {

	public void test1(){
		/* 我需要从一个Reader里一行一行读出所有文本. 直接用Reader的话, 你需要弄一个BufferedReader, 然后循环调用readLine(), 直到全部读完. 类似这样:
		BufferedReader buffered = new BufferedReader(reader);
		List<String> lines = new ArrayList<String>();
		for (;;) {
		  String line = buffered.readLine();
		  if (line == null) {
		    break;
		  }
		  lines.add(line);
		}*/
		/*
		 * 用CharStreams的话, 一句话就搞定了: 
		 */
		//List<String> lines = CharStreams.readLines(reader);
		/*
		如果你还是需要类似于流一样的操作-比如, 输入的行数太多, 不能一下子都读进来, 那么, 还有一个LineReader可以用. 用起来类似于: 
			Java代码  收藏代码
			LineReader lineReader = new LineReader(reader);  
			for (String line = lineReader.readLine(); line != null; line = lineReader.readLine()) {  
			  System.out.println(line);  
			}  



			其它的one-liner, 包括: 
			从一个Readable读取所有东西写到一个Appendable里去: 
			Java代码  收藏代码
			CharStreams.copy(reader, writer);  

			从Readable读取所有内容到一个字符串: 
			Java代码  收藏代码
			String content = CharStreams.toString(reader);  
			*/
		/*
		ByteStreams很类似, 不过它是工作在字节流上, 而不关心字符编码问题. 
		对应于CharStreams.toString(), 是
		Java代码  收藏代码
		byte[] content = ByteStreams.toByteArray(inputStream)  

		用来把整个InputStream的内容全部一次性读到一个byte[]里面. 

		对应于CharStreams.copy(), 是
		Java代码  收藏代码
		ByteStreams.copy(inputStream, outputStream);  

		用来把所有内容从一个InputStream拷贝到另一个OutputStream. 
		*/
	}
	public void test2(){
		
	}
}
