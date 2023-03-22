@[TOC](java使用tess4j进行图片文字识别)
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 一、简介
Tess4J 是Java （JNA） 对 Tesseract OCR API 的封装。
很久之前需要做一个自动登陆并对网页上的未处理的数据进行按钮点击，其中需要登陆的验证码校验，因此用了一下Tess4J，能识别一些简单的文字和数字等，识别率好像一般，但出错了就重新换一个验证码再试，多试几次也能成功。现将之前的简单使用过程记录，备查。 

tesseract：[https://tesseract-ocr.github.io/](https://tesseract-ocr.github.io/)
官网：[http://tess4j.sourceforge.net/codesample.html](http://tess4j.sourceforge.net/codesample.html)

语言库：
[https://github.com/tesseract-ocr/tessdata](https://github.com/tesseract-ocr/tessdata)
[https://codechina.csdn.net/mirrors/tesseract-ocr/tessdata](https://codechina.csdn.net/mirrors/tesseract-ocr/tessdata)
# 二、使用过程
## 1.maven依赖引入pom.xml
```c
		<!-- tess4j start -->
		<dependency>
		    <groupId>net.sourceforge.tess4j</groupId>
		    <artifactId>tess4j</artifactId>
		    <version>5.6.0</version>
		</dependency>
		<!-- tess4j end -->
```
## 2.准备好tessdata目录下的语言库文件
`需要提前下载好相关的语言库文件，这里我下了chi_sim.traineddata和eng.traineddata两个`
下载地址：[https://codechina.csdn.net/mirrors/tesseract-ocr/tessdata](https://codechina.csdn.net/mirrors/tesseract-ocr/tessdata)
下载好后放在代码里面的目录下
![在这里插入图片描述](https://img-blog.csdnimg.cn/b14850411d874d4993e4679fab4d6c01.png)
## 3.写测试代码进行测试
准备好两张图片放置在代码的资源目录下，方便程序读取，

`图片1`
![在这里插入图片描述](https://img-blog.csdnimg.cn/cb5aeb38427542108c238aea84cac921.png)
`图片2`
![在这里插入图片描述](https://img-blog.csdnimg.cn/4bebf344b6f04492959741a2cb7b0101.jpeg)
`两张图片放在资源目录下`
![在这里插入图片描述](https://img-blog.csdnimg.cn/315e847591554efca54b77ac683b7796.png)


代码如下：
```c
package cn.ljhua;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Tess4jOcr测试示例
 * @author liujh
 */
@Slf4j
public class Tess4jOcrTest {
	
	public static void main(String[] args) {
		
		Tess4jOcrTest test = new Tess4jOcrTest();
		test.ocrTest();
		
	}
	
	public void ocrTest() {
		
		log.info("ocrTest start....");
		long startMs = System.currentTimeMillis();
		
		 //Tesseract的代码开始---------------------->>>>
		ITesseract instance = new Tesseract();
		
		/**
		 * 组装接好tessdata目录的路径字符串
		 */
		String filePathPre = System.getProperty("user.dir");
    	String dataPath = filePathPre + File.separator + "tessdata";
    	
    	/**
		 * 设置目录datapath the tessdata path to set
		 * 否则会报Please make sure the TESSDATA_PREFIX environment variable is set to your "tessdata" directory.错误
		 */
    	instance.setDatapath(dataPath);
    	//instance.setLanguage("eng");//默认，可以不写
    	instance.setLanguage("chi_sim");//设置中文识别
		
    	String imageName = "verifyCode.png";
        try (InputStream inStream = this.getClass().getResourceAsStream("/" + imageName)) {
            
        	BufferedImage bImage = ImageIO.read(inStream);
        	//doOCR也可以传参为File，我这里传的BufferedImage
        	String result = instance.doOCR(bImage);
        	//识别的结果回来可能会带回车，处理掉
        	result = result.replaceAll("\n", "");
        	log.info("图片名：" + imageName +" 识别结果："+ result);
        	
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } catch (TesseractException e) {
        	log.error(e.getMessage(),e);
		}
        
        imageName = "vCode2.jpg";
        try (InputStream inStream = this.getClass().getResourceAsStream("/" + imageName)) {
            
        	BufferedImage bImage = ImageIO.read(inStream);
        	//doOCR也可以传参为File，我这里传的BufferedImage
        	String result = instance.doOCR(bImage);
        	//识别的结果回来可能会带回车，处理掉
        	result = result.replaceAll("\n", "");
        	log.info("图片名：" + imageName +" 识别结果："+ result);
        	
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } catch (TesseractException e) {
        	log.error(e.getMessage(),e);
		}
		//Tesseract的代码结束--------------------->>>>
        
		log.info("ocrTest success. spend time :{} ms.", (System.currentTimeMillis() - startMs));
		
	}

}

````

`测试结果截图如：`
![在这里插入图片描述](https://img-blog.csdnimg.cn/2f3c5cf50411445ebd5777cf14929b35.png)
英文识别出来比较正常，中文识别出来带了空格，如果需要可以通过代码进一步去掉空格，至此，tess4j的简单使用测试完成。
# 三、源码下载地址
最后提供源码如下：

github: [https://github.com/jxlhljh/tess4jOcrTest](https://github.com/jxlhljh/tess4jOcrTest)
gitee: [https://gitee.com/jxlhljh/tess4jOcrTest](https://gitee.com/jxlhljh/tess4jOcrTest)
