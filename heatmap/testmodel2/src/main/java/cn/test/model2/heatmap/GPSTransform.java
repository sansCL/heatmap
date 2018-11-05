package cn.test.model2.heatmap;

import cn.test.model2.model.GPSLocation;
import cn.test.model2.utils.ListMultipleSortUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

public class GPSTransform {

    private double[] widthAndHeight;
    private List<Point> data;

    /**
     * 生成画图所需的点
     *
     * @param dataList     数据采集点
     * @param courtGPSData 画图区域点位
     */
    public GPSTransform(@NotNull List<GPSLocation> dataList, @NotNull @Size(min = 4, max = 4, message = "需要4个坐标点") List<GPSLocation>
            courtGPSData) {
        List<GPSLocation> gpsLocations = new ArrayList<>();
        if (null != dataList && !dataList.isEmpty()) {
            dataList.forEach(l -> {
                if (l.getLongitude() > 0 && l.getLatitude() > 0) {
                    gpsLocations.add(l);
                }
            });
            data = getGraphicsPoint(gpsLocations, courtGPSData);
        }
    }

    /**
     * 计算
     * 长度4，0-左上， 1-右上， 2-左下， 3-右下
     *
     * @param dataList
     * @param courtGPSData
     * @return List<Point>
     */
    private List<Point> getGraphicsPoint(List<GPSLocation> dataList, List<GPSLocation> courtGPSData) {
        // 将球场旋转成
        //  0******1
        //  *      *
        //  2******3
        //重新计算坐标
        double angle = getAngle(courtGPSData.get(0), courtGPSData.get(1));
        GPSLocation firstLocation = courtGPSData.get(0);
        for (int i = 0; i < courtGPSData.size(); i++) {
            if (i > 0) {
                courtGPSData.set(i, getRotateLocation(angle, firstLocation, courtGPSData.get(i)));
            }
        }
        dataList.forEach(l -> {
            l = getRotateLocation(angle, firstLocation, l);
        });

        //计算最大最小值
        int size = courtGPSData.size();
        GPSLocation maxGpsLocation = new GPSLocation();
        GPSLocation minGpsLocation = new GPSLocation();
        ListMultipleSortUtil.sort(courtGPSData, true, "longitude");
        maxGpsLocation.setLongitude(courtGPSData.get(size - 1).getLongitude());
        minGpsLocation.setLongitude(courtGPSData.get(0).getLongitude());
        ListMultipleSortUtil.sort(courtGPSData, true, "latitude");
        maxGpsLocation.setLatitude(courtGPSData.get(size - 1).getLatitude());
        minGpsLocation.setLatitude(courtGPSData.get(0).getLatitude());
        //经度长边，纬度短边
        widthAndHeight = new double[]{maxGpsLocation.getLongitude() - minGpsLocation.getLongitude(), maxGpsLocation.getLatitude() -
                minGpsLocation.getLatitude()};

        return generateGraphicsPoint(dataList, maxGpsLocation, minGpsLocation);
    }


    /**
     * 取得在范围内的有效数据
     * <p>
     * 将一球场坐标拉成一个正方形，如下
     * (minLo,maxLa)*****(maxLo,maxLa)
     * *               *
     * (minLo,minLa)*****(maxLo,minLa)
     * 排除所有没在最大最小值范围内的数据
     *
     * @param dataList gps数据
     */
    private List<Point> generateGraphicsPoint(List<GPSLocation> dataList, GPSLocation maxGpsLocation, GPSLocation minGpsLocation) {
        List<Point> points = new ArrayList<>();
        dataList.forEach(l -> {
            //在范围内返回 {x,y}
            int[] p = l.checkPoint(maxGpsLocation, minGpsLocation);
            if (p != null) {
                points.add(new Point(p[0], p[1]));
            }
        });
        return points;
    }

    private double getAngle(GPSLocation location1, GPSLocation location2) {
        //atan2(y1-y0, x1-x0) ,0为参照点，1为旋转点
        double angle = Math.atan2(location2.getLatitude() - location1.getLatitude(), location2.getLongitude() - location1.getLongitude())
                / Math.PI * 180;
        return 360 - angle;
    }

    /**
     * 旋转点
     * 逆时针旋转
     *
     * @param reference 参照点
     * @param rotate    要旋转的点
     */
    private GPSLocation getRotateLocation(double angle, GPSLocation reference, GPSLocation rotate) {
        double[] pt = {rotate.getLongitude(), rotate.getLatitude()};
        // getRotateInstance(1,x,y) 1-旋转 ，x-旋转参照点坐标，y-旋转参照点坐标
        AffineTransform.getRotateInstance(Math.toRadians(angle), reference.getLongitude(), reference.getLatitude())
                .transform(pt, 0, pt, 0, 1); // numPts要生成的坐标点对数，取决于pt的对数
        Double newX = pt[0];
        Double newY = pt[1];
        GPSLocation gpsLocation = new GPSLocation();
        gpsLocation.setLongitude(newX.intValue());
        gpsLocation.setLatitude(newY.intValue());
        return gpsLocation;
    }

    /**
     * 判断长边短边
     *
     * @param maxGpsLocation 最大gps点
     * @param minGpsLocation 最小gps点
     * @return 1 经度为长边 2纬度为长边
     */
    private int getDirectionForLocation(GPSLocation maxGpsLocation, GPSLocation minGpsLocation) {
        double lot = maxGpsLocation.getLongitude() - minGpsLocation.getLongitude();
        double lat = maxGpsLocation.getLatitude() - minGpsLocation.getLatitude();
        return lot > lat ? 1 : 2;
    }

    public double[] getWidthAndHeight() {
        return widthAndHeight;
    }

    public List<Point> getData() {
        return data;
    }

}
