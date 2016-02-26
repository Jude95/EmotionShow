package com.jude.emotionshow.presentation.seed;

import android.os.Bundle;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.emotionshow.data.model.LocationModel;
import com.jude.emotionshow.domain.entities.Location;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mike on 2016/2/25.
 */
public class LocationPresenter extends BeamListActivityPresenter<LocationActivity, PoiItem> implements GeocodeSearch.OnGeocodeSearchListener {
    @Override
    protected void onCreate(LocationActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        LocationModel.getInstance().startLocation(getView().getApplicationContext());
        LocationModel.getInstance().registerLocationChange(location -> {
            this.location = location;
            GeocodeSearch geocodeSearch = new GeocodeSearch(getView());
            geocodeSearch.setOnGeocodeSearchListener(this);
            //latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(location.getLatitude(), location.getLongitude()), 2000, GeocodeSearch.AMAP);
            geocodeSearch.getFromLocationAsyn(query);
        });
    }

    Location location;

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        Observable.create(new Observable.OnSubscribe<List<PoiItem>>() {
            @Override
            public void call(Subscriber<? super List<PoiItem>> subscriber) {
                List<PoiItem> poiItemList = regeocodeResult.getRegeocodeAddress().getPois();
                poiItemList.add(0, new PoiItem("id", new LatLonPoint(0, 0), location.getAddress(), "默认："));
                subscriber.onNext(poiItemList);
            }
        }).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
    }
}
