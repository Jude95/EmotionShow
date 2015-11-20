package com.jude.emotionshow.data.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.domain.entities.Region;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Mr.Jude on 2015/7/11.
 */
public class RegionModel extends AbsModel {

    private SQLiteDatabase db;
    private RegionDBHelper dbHelper;
    private static final String DATABASE_TABLE = "citycode";
    public static final String KEY_ID = "id";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_CID = "cid";
    public static final String KEY_PARENTID = "parentId";
    public static final String KEY_NAME = "name";

    public static final String[] result_columns = new String[] { KEY_ID,
            KEY_LEVEL, KEY_CID, KEY_PARENTID, KEY_NAME};

    public static RegionModel getInstance() {
        return getInstance(RegionModel.class);
    }

    public  void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public boolean isOpen() {
        return db.isOpen();
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        dbHelper = new RegionDBHelper(ctx, "citycode.db", null, 1);
    }

    public ArrayList<Region> getProvinceList(){
        open();
        String where = KEY_LEVEL + "='" + 1 +"'";
        Cursor result = db.query(DATABASE_TABLE, result_columns, where, null,
                null, null, KEY_ID);
        if (result == null) {
            return null;
        } else {
            if (result.getCount() > 0) {
                int iID = result.getColumnIndexOrThrow(KEY_ID);
                int iCID = result.getColumnIndexOrThrow(KEY_CID);
                int iLEVEL = result.getColumnIndexOrThrow(KEY_LEVEL);
                int iPARENTED = result.getColumnIndexOrThrow(KEY_PARENTID);
                int iNAME = result.getColumnIndexOrThrow(KEY_NAME);
                ArrayList<Region> arr = new ArrayList<>(result.getCount());
                while (result.moveToNext()){
                    arr.add(new Region(result.getInt(iID),result.getInt(iLEVEL),result.getInt(iCID),result.getInt(iPARENTED),result.getString(iNAME)));
                }
                result.close();
                close();
                return arr;
            } else {
                result.close();
                close();
                return null;
            }
        }
    }

    public ArrayList<Region> getCityList(int provinceCode){
        open();
        String where = KEY_LEVEL + "='" + 2 +"' and "+KEY_PARENTID+"='"+provinceCode+"'";
        Cursor result = db.query(DATABASE_TABLE, result_columns, where, null,
                null, null, KEY_ID);
        if (result == null) {
            return null;
        } else {
            if (result.getCount() > 0) {
                int iID = result.getColumnIndexOrThrow(KEY_ID);
                int iCID = result.getColumnIndexOrThrow(KEY_CID);
                int iLEVEL = result.getColumnIndexOrThrow(KEY_LEVEL);
                int iPARENTED = result.getColumnIndexOrThrow(KEY_PARENTID);
                int iNAME = result.getColumnIndexOrThrow(KEY_NAME);
                ArrayList<Region> arr = new ArrayList<>(result.getCount());
                while (result.moveToNext()){
                    arr.add(new Region(result.getInt(iID),result.getInt(iLEVEL),result.getInt(iCID),result.getInt(iPARENTED),result.getString(iNAME)));
                }
                result.close();
                close();
                return arr;
            } else {
                result.close();
                close();
                return null;
            }
        }
    }

    public ArrayList<Region> getRegionList(int cityCode){
        open();
        String where = KEY_LEVEL + "='" + 3 +"' and "+KEY_PARENTID+"='"+cityCode+"'";
        Cursor result = db.query(DATABASE_TABLE, result_columns, where, null,
                null, null, KEY_ID);
        if (result == null) {
            return null;
        } else {
            if (result.getCount() > 0) {
                int iID = result.getColumnIndexOrThrow(KEY_ID);
                int iCID = result.getColumnIndexOrThrow(KEY_CID);
                int iLEVEL = result.getColumnIndexOrThrow(KEY_LEVEL);
                int iPARENTED = result.getColumnIndexOrThrow(KEY_PARENTID);
                int iNAME = result.getColumnIndexOrThrow(KEY_NAME);
                ArrayList<Region> arr = new ArrayList<>(result.getCount());
                while (result.moveToNext()){
                    arr.add(new Region(result.getInt(iID),result.getInt(iLEVEL),result.getInt(iCID),result.getInt(iPARENTED),result.getString(iNAME)));
                }
                result.close();
                close();
                return arr;
            } else {
                result.close();
                close();
                return null;
            }
        }
    }

    public Region findRegionByCode(int code){
        open();
        String where = KEY_CID + "='" + code +"' ";
        Cursor result = db.query(DATABASE_TABLE, result_columns, where, null,
                null, null, KEY_ID);
        if (result == null) {
            return null;
        } else {
            if (result.getCount() > 0) {
                result.moveToNext();
                int iID = result.getColumnIndexOrThrow(KEY_ID);
                int iCID = result.getColumnIndexOrThrow(KEY_CID);
                int iLEVEL = result.getColumnIndexOrThrow(KEY_LEVEL);
                int iPARENTED = result.getColumnIndexOrThrow(KEY_PARENTID);
                int iNAME = result.getColumnIndexOrThrow(KEY_NAME);
                Region region = new Region(result.getInt(iID),result.getInt(iLEVEL),result.getInt(iCID),result.getInt(iPARENTED),result.getString(iNAME));
                result.close();
                close();
                return region;
            } else {
                result.close();
                close();
                return null;
            }
        }
    }

    public Region findProvince(int code){
        Region region = findRegionByCode(code);
        if (region == null){
            return getProvinceList().get(0);
        }
        while (region.getLevel()>1){
            region = findRegionByCode(region.getParentId());
        }
        return region;
    }

    public Region findCity(int code){
        Region region = findRegionByCode(code);
        if (region == null){
            return getCityList(getProvinceList().get(0).getCid()).get(0);
        }
        if (region.getLevel() == 1 ){
            return getCityList(region.getCid()).get(0);
        }else if (region.getLevel() == 2){
            return region;
        }else{
            return findRegionByCode(region.getParentId());
        }
    }

    public Region findRegion(int code){
        Region region = findRegionByCode(code);
        if (region == null){
            return getRegionList(getCityList(getProvinceList().get(0).getCid()).get(0).getCid()).get(0);
        }
        if (region.getLevel() == 1 ){
            return getRegionList(getCityList(region.getCid()).get(0).getCid()).get(0);
        }else if (region.getLevel() == 2){
            return getRegionList(region.getCid()).get(0);
        }else{
            return region;
        }
    }

    private class RegionDBHelper extends SQLiteOpenHelper {
        private Context ctx;
        SQLiteDatabase myDataBase;

        public RegionDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            ctx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            myDataBase = db;
            try {
                initDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * inital your database from your local res-raw-folder to the just created
         * empty database in the system folder, from where it can be accessed and
         * handled.
         * */
        private void initDataBase() throws IOException {
            InputStream myInput = ctx.getResources().getAssets().open("citycode.sql");
            InputStreamReader reader = new InputStreamReader(myInput);
            BufferedReader breader = new BufferedReader(reader);

            String str;

            while ((str = breader.readLine()) != null) {
                myDataBase.execSQL(str); //exec your SQL line by line.
            }

            myInput.close();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                initDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
