package com.instance;

import com.google.zxing.WriterException;
import com.instance.QRCode.QRCodeFactory;

import java.io.IOException;

/**
 *@author WXY
 */
public class App {
    public static void main(String[] args)
    {
        App a= new App();
        a.testQRcode();
    }

    private void testQRcode(){
        String content="http://weixin.qq.com/q/02T5OddzamcF110000007B";
        String logUri="F:\\logo.png";
        String outFileUri="C:\\Users\\WXY\\Desktop\\qrcode.png";
        int[]  size=new int[]{430,430};
        String format = "png";
        new QRCodeFactory().creatQrImage(content, format, outFileUri, logUri,size);
    }
}
