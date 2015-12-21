package com.jude.emotionshow.data.model;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.Dir;
import com.jude.emotionshow.domain.entities.Location;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Mr.Jude on 2015/1/28.
 * 地理位置的管理API
 */
public class LocationModel extends AbsModel {

    private static final String FILENAME = "location";
    private Location mLocation;

    public static LocationModel getInstance(){
        return getInstance(LocationModel.class);
    }

    private BehaviorSubject<Location> mLocationSubject = BehaviorSubject.create();


    public double getDistance(double lat,double lng){
        return JUtils.distance(mLocation.getLongitude(), mLocation.getLatitude(), lng, lat);
    }

    //注册位置变动通知
    public Subscription registerLocationChange(Action1<Location> action1){
        return mLocationSubject.subscribe(action1);
    }
    //取当前位置
    public Location getCurLocation(){
        return mLocation;
    }

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        //启动时读取存储的上次的地点，避免不知道选择所处位置的情况。
        mLocation = (Location) JFileManager.getInstance().getFolder(Dir.Object).readObjectFromFile(FILENAME);
        //如果没有，那我也没办法，new一个，有默认位置。
        if (mLocation == null)mLocation = new Location();
        startLocation(ctx);
        //先注册一个，位置一变动就保存起来并上传服务器。＝ ＝不要问为什么要搜集用户隐私。
        registerLocationChange(location -> {
            mLocation = location;
            JUtils.Log("Location update"+location.getAddress());
            JFileManager.getInstance().getFolder(Dir.Object).writeObjectToFile(location, FILENAME);
            uploadAddress();
        });
    }

    /**
     * 配置高德
     * @param ctx
     */
    public void startLocation(final Context ctx){
        AMapLocationClient mLocationClient = new AMapLocationClient(ctx);
        AMapLocationClientOption option = new AMapLocationClientOption();
        //每分钟取一下最新位置。
        //option.setInterval(60000);
        //只取一次位置就够了，我相信他不会移动太多距离，有问题再修改吧。
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(aMapLocation -> {
            JUtils.Log("GetLocation");
            //只有位置变动时才上传，就算每分钟获取，不进行网络请求也不会费电。
            if (!mLocation.equals(createLocation(aMapLocation)));
            //位置有变动，发布新位置
            mLocationSubject.onNext(createLocation(aMapLocation));
        });
        mLocationClient.startLocation();
    }

    private Location createLocation(AMapLocation aMapLocation){
        Location location = new Location();
        location.address = aMapLocation.getAddress();
        location.altitude = aMapLocation.getAltitude();
        location.latitude = aMapLocation.getLatitude();
        location.longitude = aMapLocation.getLongitude();
        location.city = aMapLocation.getCity();
        location.country = aMapLocation.getCountry();
        location.district = aMapLocation.getDistrict();
        location.province = aMapLocation.getProvince();
        location.regionCode = Integer.parseInt(aMapLocation.getAdCode());
        return location;
    }

    /**
     * 上传用户位置
     */
    public void uploadAddress(){
        if (UserModel.getInstance().isLogin())
        CommonModel.getInstance().updateAddress(mLocation.getLatitude(), mLocation.getLongitude())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onServiceError(int status, String info) {
                    }
                });
    }
}
