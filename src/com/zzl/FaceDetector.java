package com.zzl;

/**
 * Class <code>Object</code> is the root of the class hierarchy.
 * Every class has <code>Object</code> as a superclass. All objects,
 * including arrays, implement the methods of this class.
 *
 * @author Administrator
 * @version 1.0
 * @see
 * @since JDK1.7
 * <p>
 * History
 * Created by Administrator on 2017/8/17 0017.
 */
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FaceDetector {

    public static void main(String[] args) throws Exception{
        //指定旋转
//        File file1 = new File("D:\\study\\face\\src\\com\\zzl\\12.jpg");
//        BufferedImage image1 = ImageIO.read(file1);
//        BufferedImage image2 = rotateImage(image1, 110);
//        ImageIO.write(image2, "jpg", new File("D:\\jar\\test.jpg"));

        //人脸识别
        detectFace();

    }

    public static void detectFace(){
        //加载本地的OpenCV库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Running FaceDetector");
        //创建实例CascadeClassifier,将已加装的分类器的文件名传进来
        CascadeClassifier faceDetector = new CascadeClassifier("D:\\study\\faceDetetorDemo\\src\\com\\zzl\\haarcascade_frontalface_alt.xml");
        //将图片转转化成Java API能够接受使用Highui类的格式，铺垫在OpenCV C++的n维密集数组类上边
        Mat image = Highgui.imread("D:\\study\\faceDetetorDemo\\src\\com\\zzl\\12.jpg");

        MatOfRect faceDetections = new MatOfRect();
        //调用分类器上的detectMultiScale方法传递给它图象和MatOfRect对象。MatOfRect将有面部检测
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        //遍历所有的脸部检测并用矩形标记图像
        for (Rect rect : faceDetections.toArray()) {
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            // 把检测到的人脸重新定义大小后保存成文件
            Mat sub = image.submat(rect);
            Mat mat = new Mat();
            Size size = new Size(100, 100);
            Imgproc.resize(sub, mat, size);
            Highgui.imwrite("D:\\study\\faceDetetorDemo\\src\\com\\zzl\\face.jpg", mat);
        }

        //输出图片
        String filename = "D:\\study\\faceDetetorDemo\\src\\com\\zzl\\output.jpg";
        System.out.println(String.format("Writing %s", filename));
        Highgui.imwrite(filename, image);

    }


    /**
     * 按指定角度旋转图片
     * @param bufferedimage 目标图像
     * @param degree    旋转角度
     * @return
     */
    public static BufferedImage rotateImage(BufferedImage bufferedimage, int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}