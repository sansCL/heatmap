package cn.test.model2.heatmap;

import cn.test.model2.model.GPSLocation;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeatMapUtil {

    public static String getFilePath(String dir, String name) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ServletContext servletContext = request.getSession().getServletContext();
        //		String filepath = FileUpload.class.getResource(dir + realName).getFile();
        File file = new File(servletContext.getRealPath(dir + name));
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    public static synchronized String createHeatMap(List<GPSLocation> gpsLocationList, List<GPSLocation> courtList) {
        GPSTransform gpsTransform = new GPSTransform(gpsLocationList, courtList);
        List<Point> points = gpsTransform.getData();
        String originalImage = "E:/javadev/test/testmodel2/src/main/webapp/WEB-INF/res/images/court.jpg";
//        originalImage = getFilePath("res/images", "court");
        double[] courtSize = gpsTransform.getWidthAndHeight();
        List<Point> pointList = new ArrayList<>();
        for (int i=0;i<10;i++) {
            pointList.add(new Point(640 + i*2, 400+i*2));
        }
        final String outputFile = "E:/javadev/test/testmodel2/src/main/webapp/WEB-INF/res/images/court-heat.png";

        final HeatMap myMap = new HeatMap(pointList, outputFile, originalImage);
        myMap.createHeatMap(0.3f);
        return outputFile;
    }

}
