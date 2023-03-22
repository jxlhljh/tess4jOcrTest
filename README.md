@[TOC](javaʹ��tess4j����ͼƬ����ʶ��)
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# һ�����
Tess4J ��Java ��JNA�� �� Tesseract OCR API �ķ�װ��
�ܾ�֮ǰ��Ҫ��һ���Զ���½������ҳ�ϵ�δ��������ݽ��а�ť�����������Ҫ��½����֤��У�飬�������һ��Tess4J����ʶ��һЩ�򵥵����ֺ����ֵȣ�ʶ���ʺ���һ�㣬�������˾����»�һ����֤�����ԣ����Լ���Ҳ�ܳɹ����ֽ�֮ǰ�ļ�ʹ�ù��̼�¼�����顣 

tesseract��[https://tesseract-ocr.github.io/](https://tesseract-ocr.github.io/)
������[http://tess4j.sourceforge.net/codesample.html](http://tess4j.sourceforge.net/codesample.html)

���Կ⣺
[https://github.com/tesseract-ocr/tessdata](https://github.com/tesseract-ocr/tessdata)
[https://codechina.csdn.net/mirrors/tesseract-ocr/tessdata](https://codechina.csdn.net/mirrors/tesseract-ocr/tessdata)
# ����ʹ�ù���
## 1.maven��������pom.xml
```c
		<!-- tess4j start -->
		<dependency>
		    <groupId>net.sourceforge.tess4j</groupId>
		    <artifactId>tess4j</artifactId>
		    <version>5.6.0</version>
		</dependency>
		<!-- tess4j end -->
```
## 2.׼����tessdataĿ¼�µ����Կ��ļ�
`��Ҫ��ǰ���غ���ص����Կ��ļ�������������chi_sim.traineddata��eng.traineddata����`
���ص�ַ��[https://codechina.csdn.net/mirrors/tesseract-ocr/tessdata](https://codechina.csdn.net/mirrors/tesseract-ocr/tessdata)
���غú���ڴ��������Ŀ¼��
![���������ͼƬ����](https://img-blog.csdnimg.cn/b14850411d874d4993e4679fab4d6c01.png)
## 3.д���Դ�����в���
׼��������ͼƬ�����ڴ������ԴĿ¼�£���������ȡ��

`ͼƬ1`
![���������ͼƬ����](https://img-blog.csdnimg.cn/cb5aeb38427542108c238aea84cac921.png)
`ͼƬ2`
![���������ͼƬ����](https://img-blog.csdnimg.cn/4bebf344b6f04492959741a2cb7b0101.jpeg)
`����ͼƬ������ԴĿ¼��`
![���������ͼƬ����](https://img-blog.csdnimg.cn/315e847591554efca54b77ac683b7796.png)


�������£�
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
 * Tess4jOcr����ʾ��
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
		
		 //Tesseract�Ĵ��뿪ʼ---------------------->>>>
		ITesseract instance = new Tesseract();
		
		/**
		 * ��װ�Ӻ�tessdataĿ¼��·���ַ���
		 */
		String filePathPre = System.getProperty("user.dir");
    	String dataPath = filePathPre + File.separator + "tessdata";
    	
    	/**
		 * ����Ŀ¼datapath the tessdata path to set
		 * ����ᱨPlease make sure the TESSDATA_PREFIX environment variable is set to your "tessdata" directory.����
		 */
    	instance.setDatapath(dataPath);
    	//instance.setLanguage("eng");//Ĭ�ϣ����Բ�д
    	instance.setLanguage("chi_sim");//��������ʶ��
		
    	String imageName = "verifyCode.png";
        try (InputStream inStream = this.getClass().getResourceAsStream("/" + imageName)) {
            
        	BufferedImage bImage = ImageIO.read(inStream);
        	//doOCRҲ���Դ���ΪFile�������ﴫ��BufferedImage
        	String result = instance.doOCR(bImage);
        	//ʶ��Ľ���������ܻ���س��������
        	result = result.replaceAll("\n", "");
        	log.info("ͼƬ����" + imageName +" ʶ������"+ result);
        	
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } catch (TesseractException e) {
        	log.error(e.getMessage(),e);
		}
        
        imageName = "vCode2.jpg";
        try (InputStream inStream = this.getClass().getResourceAsStream("/" + imageName)) {
            
        	BufferedImage bImage = ImageIO.read(inStream);
        	//doOCRҲ���Դ���ΪFile�������ﴫ��BufferedImage
        	String result = instance.doOCR(bImage);
        	//ʶ��Ľ���������ܻ���س��������
        	result = result.replaceAll("\n", "");
        	log.info("ͼƬ����" + imageName +" ʶ������"+ result);
        	
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } catch (TesseractException e) {
        	log.error(e.getMessage(),e);
		}
		//Tesseract�Ĵ������--------------------->>>>
        
		log.info("ocrTest success. spend time :{} ms.", (System.currentTimeMillis() - startMs));
		
	}

}

````

`���Խ����ͼ�磺`
![���������ͼƬ����](https://img-blog.csdnimg.cn/2f3c5cf50411445ebd5777cf14929b35.png)
Ӣ��ʶ������Ƚ�����������ʶ��������˿ո������Ҫ����ͨ�������һ��ȥ���ո����ˣ�tess4j�ļ�ʹ�ò�����ɡ�
# ����Դ�����ص�ַ
����ṩԴ�����£�

github: [https://github.com/jxlhljh/tess4jOcrTest](https://github.com/jxlhljh/tess4jOcrTest)
gitee: [https://gitee.com/jxlhljh/tess4jOcrTest](https://gitee.com/jxlhljh/tess4jOcrTest)
