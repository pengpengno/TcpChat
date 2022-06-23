package com.wang.demo.chat;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;

import java.io.*;
import java.net.URLDecoder;
public class image_paint {

	    /***
	     *
	     * @param srcFilePath ԴͼƬ�ļ�·��
	     * @param circularImgSavePath �����ɵ�ͼƬ�ı���·������Ҫ���б�����ļ����ͺ�׺
	     * @param targetSize �ļ��ı߳�����λ�����أ����յõ�����һ�������ε�ͼ������Ҫ��targetSize<=Դ�ļ�����С�߳�
	     * @param cornerRadius Բ�ǰ뾶����λ�����ء����=targetSize��ô�õ�����Բ��ͼ
	     * @return  �ļ��ı���·��
	     * @throws IOException
	     */
	    public static String makeCircularImg(String srcFilePath, String circularImgSavePath,int targetSize, int cornerRadius) throws IOException {
	        BufferedImage bufferedImage = ImageIO.read(new File(srcFilePath));
	        BufferedImage circularBufferImage = roundImage(bufferedImage,targetSize,cornerRadius);
	        ImageIO.write(circularBufferImage, "png", new File(circularImgSavePath));
	        return circularImgSavePath;
	    }
	    private static BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius) {
	        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = outputImage.createGraphics();
	        g2.setComposite(AlphaComposite.Src);
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(Color.WHITE);
	        g2.fill(new RoundRectangle2D.Float(0, 0, targetSize, targetSize, cornerRadius, cornerRadius));
	        g2.setComposite(AlphaComposite.SrcAtop);
	        g2.drawImage(image, 0, 0, null);
	        g2.dispose();
	        return outputImage;
	    }
	    /**
	     * ����Բ��ͼ��
	     * @param image
	     * @param cornerRadius Բ�ǰ뾶
	     * @return
	     */
	    public static BufferedImage makeRoundedCorner1(BufferedImage image, int cornerRadius) {
	        int w = image.getWidth();
	        int h = image.getHeight();
	        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2 = output.createGraphics();
	        g2.setComposite(AlphaComposite.Src);
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(Color.WHITE);
	        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
	        g2.setComposite(AlphaComposite.SrcAtop);
	        g2.drawImage(image, 0, 0, null);
	        g2.dispose();
	        return output;
	    }
	    /**ͼƬԲ�Ǵ�������͸����
	     * @param srcImageFile ԭͼƬ
	     * @param result  �����ͼƬ
	     * @param type   ͼƬ��ʽ
	     * @param cornerRadius  720ΪԲ��
	     */
	    public static  void makeRoundedCorner(File srcImageFile, File result, String type, int cornerRadius) {
	        try {
	            BufferedImage bi1 = ImageIO.read(srcImageFile);

	            // ������Ҫ�Ƿ�ʹ�� BufferedImage.TYPE_INT_ARGB
	            BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
	                    BufferedImage.TYPE_INT_ARGB);

	            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
	                    .getHeight());

	            Graphics2D g2 = image.createGraphics();
	            image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getWidth(), bi1.getHeight(), Transparency.TRANSLUCENT);
	            g2 = image.createGraphics();
	            g2.setComposite(AlphaComposite.Clear);
	            g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
	            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
	            g2.setClip(shape);
	            // ʹ�� setRenderingHint ���ÿ����
	            g2 = image.createGraphics();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            g2.fillRoundRect(0, 0,bi1.getWidth(), bi1.getHeight(), cornerRadius, cornerRadius);
	            g2.setComposite(AlphaComposite.SrcIn);
	            g2.drawImage(bi1, 0, 0, bi1.getWidth(), bi1.getHeight(), null);
	            g2.dispose();
	            ImageIO.write(image, type, result);
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	    }

	

	public static void main(String[] args) throws IOException {
		String url="E:/eclipse/eclipse-jee-oxygen-R-win32-x86_64/eclipse/chat/src/gril.png";
		String url1="E:\\eclipse\\eclipse-jee-oxygen-R-win32-x86_64\\eclipse\\chat\\src";
		String url3="E:\\eclipse\\eclipse-jee-oxygen-R-win32-x86_64\\eclipse\\chat\\src\\temp.png";
		//url = URLDecoder.decode(url,"utf-8");
		int width=120;		
		try {
            BufferedImage image = ImageIO.read(new File(url));
            BufferedImage rounded = image_paint.roundImage(image, 1000, 1000);
            //image_paint.makeRoundedCorner(new File(url),new File(url3),"png",720);
            
            ImageIO.write(rounded, "png", new File(url3));
        } catch (IOException e) {
            e.printStackTrace();
        }



		}
}
