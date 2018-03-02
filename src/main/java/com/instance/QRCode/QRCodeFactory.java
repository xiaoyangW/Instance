package com.instance.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author wxy
 */
public class QRCodeFactory {

    /**
     * 二维码logo处理
     * @param matrixImage 二维码
     * @param logUri logo图路径
     * @return BufferedImage
     * @throws IOException
     */
    public BufferedImage setMatrixLogo(BufferedImage matrixImage, String logUri){
        // 读取二维码图片，并构建绘图对象
        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();
        //读取Logo图片
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(new File(logUri));
            //开始绘制图片
            g2.drawImage(logo,matrixWidth/5*2,matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5, null);
            //绘制边框
            BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            // 设置笔画对象
            g2.setStroke(stroke);
            //指定弧度的圆角矩形
            RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
            g2.setColor(Color.white);
            // 绘制圆弧矩形
            g2.draw(round);
            //设置logo 有一道灰色边框
            BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            // 设置笔画对象
            g2.setStroke(stroke2);
            RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2, matrixHeigh/5*2+2, matrixWidth/5-4, matrixHeigh/5-4,20,20);
            g2.setColor(new Color(128,128,128));
            //绘制圆弧矩形
            g2.draw(round2);
            g2.dispose();
            matrixImage.flush() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrixImage ;

    }

    /**
     * 生成二维码
     * @param content 二维码内容
     * @param format 图片类型
     * @param outFileUri 二维码生成路径
     * @param logUri logo路径
     * @param size 二维码尺寸
     */
    public  void creatQrImage(String content,String format,String outFileUri,String logUri, int ...size) {
        //默认二维码长宽
        int width = 430;
        int height = 430;
        //如果存储大小的不为空，那么对我们图片的大小进行设定
        if (size!=null){
            if(size.length==2){
                width=size[0];
                height=size[1];
            }else if(size.length==1){
                width=height=size[0];
            }
        }
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        //指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置二维码边的空度，非负数(周边空白)
        hints.put(EncodeHintType.MARGIN, 1);
        //要编码的内容，生成二维码时的一些配置,此项可选
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
            //生成二维码图片文件
            File outputFile = new File(outFileUri);
            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile,logUri);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
