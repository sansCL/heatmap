//package cn.test.model2.draw;
//
//import cn.test.model2.model.ColorLevel;
//
//import cn.test.model2.utils.ListMultipleSortUtil;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//public class DrawImageUtil {
//
//    private static Color[] colors = new Color[]{Color.GREEN, Color.BLUE, Color.ORANGE, Color.RED};
//    private static long[] maxMinLevel = new long[]{4, 1};
////    private static ColorLevel[] colorLevels = new ColorLevel[]{
////            new ColorLevel(Color.GREEN, 1),
////            new ColorLevel(Color.BLUE, 2),
////            new ColorLevel(Color.ORANGE, 3),
////            new ColorLevel(Color.RED, 4)
////    };
//
//    /**
//     * @param path
//     * @param courtSize 包含球场的长宽参数
//     * @return
//     */
//    public static synchronized String createHeatImage(String path, double[] courtSize, List<GraphicsPoint> data) {
//        if (null == path) {
//            path = "E:/javadev/test/testmodel2/src/main/webapp/WEB-INF/res/images/court.jpg";
//        }
//        double width = courtSize[0];
//        double height = courtSize[1];
//
//        ListMultipleSortUtil.sort(data, true, "total");
//        maxMinLevel = new long[]{data.get(data.size() - 1).getTotal(), data.get(0).getTotal()};
//
//        BufferedImage buffImg = null;
//        try {
//            buffImg = ImageIO.read(new File(path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int imgHeight = buffImg.getHeight();
//        int imgWidth = buffImg.getWidth();
//        String topImage = createHeatMap(imgWidth, imgHeight, width / imgWidth, height / imgHeight, path, data);
//
//        BufferedImage waterImg = null;
//        try {
//            buffImg = ImageIO.read(new File(path));
//            waterImg = ImageIO.read(new File(topImage));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 创建Graphics2D对象，用在底图对象上绘图
//        Graphics2D g2d = (Graphics2D) buffImg.getGraphics();
//        int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
//        int waterImgHeight = waterImg.getHeight();// 获取层图的高度
//        // 绘制
//        g2d.drawImage(waterImg, 0, 0, waterImgWidth, waterImgHeight, null);
//        g2d.dispose();// 释放图形上下文使用的系统资源
//        String resultPath = "E:/javadev/test/testmodel2/src/main/webapp/WEB-INF/res/images/court-copy.png";
//        createImage(resultPath, buffImg);
//        return resultPath;
//    }
//
//
//    private static String createHeatMap(int width, int height, double widthCoeff, double heightCoeff, String path, List<GraphicsPoint> data) {
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        // 获取Graphics2D
//        Graphics2D g2d = image.createGraphics();
//
//        // 增加下面代码使得背景透明
//        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
//        g2d.dispose();
//        g2d = image.createGraphics();
//        // 背景透明代码结束
//
//        int stroke = width / 100;
//        // 画图BasicStroke是JDK中提供的一个基本的画笔类,我们对他设置画笔的粗细，就可以在drawPanel上任意画出自己想要的图形了。
//        g2d.setStroke(new BasicStroke(stroke));
//        int count = data.size();
//        for (int i = 1; i < count; i++) {
//
//            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() / 5));
//            Double x = data.get(i).getX() / widthCoeff;
//            Double y  = data.get(i).getY() / heightCoeff;
//            int intX = x.intValue();
//            int intY = y.intValue();
//            g2d.drawOval(intX, intY, stroke, stroke);
//            g2d.setStroke(new BasicStroke(2));
//            g2d.setColor(Color.orange);
//            g2d.drawArc(intX, intY, stroke + 2, stroke + 2, 0, 360);
//        }
//        g2d.dispose();// 释放图形上下文使用的系统资源
//        int last = path.lastIndexOf("/");
//        String resultPath = path.substring(0, last + 1) + "court_transparent.png";
//        createImage(resultPath, image);
//        return resultPath;
//    }
//
//    private static void createImage(String fileLocation, BufferedImage image) {
//        try {
//            image.flush();
//            File of = new File(fileLocation);
//            ImageIO.write(image, "png", of);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static Color getDrawColor(long level) {
////        long[] levels = new long[]
//        return Color.RED;
//    }
//}
