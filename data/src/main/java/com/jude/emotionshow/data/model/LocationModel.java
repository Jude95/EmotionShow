package com.jude.emotionshow.data.model;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
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
 * 地理位置的管理
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

    public Subscription registerLocationChange(Action1<Location> action1){
        return mLocationSubject.subscribe(action1);
    }

    public Location getCurLocation(){
        return mLocation;
    }

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        mLocation = (Location) JFileManager.getInstance().getFolder(Dir.Object).readObjectFromFile(FILENAME);
        if (mLocation == null)mLocation = new Location();
        startLocation(ctx);
        registerLocationChange(new Action1<Location>() {
            @Override
            public void call(Location location) {
                mLocation = location;
                JFileManager.getInstance().getFolder(Dir.Object).writeObjectToFile(location, FILENAME);
                uploadAddress();
            }
        });
    }

    public void startLocation(final Context ctx){
        AMapLocationClient mLocationClient = new AMapLocationClient(ctx);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                mLocationSubject.onNext(createLocation(aMapLocation));
            }
        });
    }

    private Location createLocation(AMapLocation aMapLocation){
        Location location = new Location();
        location.address = location.getAddress();
        location.altitude = location.getAltitude();
        location.latitude = location.getLatitude();
        location.longitude = location.getLongitude();
        location.city = location.getCity();
        location.country = location.getCountry();
        location.district = location.getDistrict();
        location.floor = location.getFloor();
        location.province = location.getProvince();
        location.street = location.getStreet();
        location.regionCode = Integer.parseInt(aMapLocation.getAdCode());
        return location;
    }

    public void uploadAddress(){
        JUtils.Log("Update Address");
        if (UserModel.getInstance().isLogin())
        CommonModel.getInstance().updateAddress(mLocation.getLatitude(), mLocation.getLongitude())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onServiceError(int status, String info) {
                    }
                });
    }
}
