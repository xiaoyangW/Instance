package com.instance.QRCode;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author WXY
 */
public class MatrixToImageWriter {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFF;


    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y,  (matrix.get(x, y) ? BLACK : WHITE));
            }
        }
        return image;
    }

    /**
     * 为二维码添加logo生成文件
     * @param matrix 二维码矩阵
     * @param format 文件类型
     * @param file 输出文件路径
     * @param logUri logo文件路径
     */
    public static void writeToFile(BitMatrix matrix, String format, File file, String logUri) {
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        try {
            image = logoConfig.setMatrixLogo(image, logUri);
            if (!ImageIO.write(image, format, file)) {
                throw new IOException("Could not write an image of format " + format + " to " + file);
            }else{
                System.out.println("图片生成成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
