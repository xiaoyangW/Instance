# Util
开发中用到的工具类

`com.instance.QRCode目录下是使用google的zxing工具包实现的生成可带logo二维码的工具类`

使用方法示例：
```
//二维码内容
String content="hello"; 
//logo路径
String logUri="F:\\logo.png";
//二维码生成路径
String outFileUri="C:\\Users\\WXY\\Desktop\\qrcode.png";
//二维码尺寸
int[]  size=new int[]{430,430};
//二维码文件类型
String format = "png";
//生成二维码
new QRCodeFactory().creatQrImage(content, format, outFileUri, logUri,size);
```
         

`com.instance.WechatRedBasic 主要使用微信公众号现金红包的工具类，实现红包接口的密钥生成，支付CA证书权限等。`

使用示例：
```$xslt
Map<String, String> map = RedBigUtil.sendRedBasic("发给用户的openid", "金额（分）", "恭喜发财", "注册福利！");
//map为红包发送情况
```
