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
