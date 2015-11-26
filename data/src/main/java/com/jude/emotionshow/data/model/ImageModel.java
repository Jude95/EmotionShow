package com.jude.emotionshow.data.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jude.beam.model.AbsModel;
import com.jude.emotionshow.data.server.DefaultTransform;
import com.jude.emotionshow.data.server.ServiceResponse;
import com.jude.emotionshow.domain.entities.Image;
import com.jude.emotionshow.domain.entities.Token;
import com.jude.utils.JUtils;
import com.qiniu.android.storage.UploadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * Created by zhuchenxi on 15/7/21.
 */
public class ImageModel extends AbsModel {
    public static String UID = "";
    public static final int IMAGE_SIZE_SMALL = 150;
    public static final int IMAGE_SIZE_LARGE = 960;


    public static final int IMAGE_WIDTH_MAX=480;
    public static final int IMAGE_HEIGHT_MIN=800;

    public static ImageModel getInstance() {
        return getInstance(ImageModel.class);
    }
    public static final String ADDRESS = "http://7xnrrg.com2.z0.glb.qiniucdn.com/";
    public static final String QINIU = "qiniucdn.com";
    private UploadManager mUploadManager;


    public static boolean isQiniuAddress(String address){
        return address.contains(QINIU);
    }

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        mUploadManager = new UploadManager();
    }

    public static Image getSmallImage(Image image){
        if (image==null||image.getUrl()==null)return null;
        image = calculateScaling(image,IMAGE_SIZE_SMALL,IMAGE_SIZE_SMALL);
        if (isQiniuAddress(image.getUrl())) image.setUrl(image.getUrl()+"?imageView2/0/w/"+IMAGE_SIZE_SMALL);
        return image;
    }

    public static Image getLargeImage(Image image){
        if (image==null||image.getUrl()==null)return null;
        image = calculateScaling(image,IMAGE_SIZE_LARGE,IMAGE_SIZE_LARGE);
        if (isQiniuAddress(image.getUrl())) image.setUrl(image.getUrl()+"?imageView2/0/w/"+IMAGE_SIZE_LARGE);
        return image;
    }

    public static Image getSizeImage(Image image,int width){
        if (image==null||image.getUrl()==null)return null;
        image = calculateScaling(image,width,width);
        if (isQiniuAddress(image.getUrl())) image.setUrl(image.getUrl()+"?imageView2/0/w/"+width);
        return image;
    }

    public static String getSmallImage(String image){
        if (image==null)return null;
        if (isQiniuAddress(image)) image+="?imageView2/0/w/"+IMAGE_SIZE_SMALL;
        return image;
    }

    public static String getLargeImage(String image){
        if (image==null)return null;
        if (isQiniuAddress(image)) image+="?imageView2/0/w/"+IMAGE_SIZE_LARGE;
        return image;
    }

    public static String getSizeImage(String image,int width){
        if (image==null)return null;
        if (isQiniuAddress(image)) image+="?imageView2/0/w/"+width;
        return image;
    }


    private static Image calculateScaling(Image image,int targetWidth,int targetHeight){
        int width = image.getWidth();
        int height = image.getHeight();
        float minWidthScal = (float)width/targetWidth;
        float minHeightScal = (float)height/targetHeight;
        float cur = Math.max(minWidthScal,minHeightScal);
        return new Image(image.getUrl(),(int)(width/cur),(int)(height/cur));
    }

    private String createName(File file){
        String realName = "u"+UID+System.currentTimeMillis()+file.hashCode()+".jpg";
        return realName;
    }

    /**
     * 异步上传
     * TODO 更改返回类型
     * @param file 需上传文件
     * @return 上传文件访问地址
     */
    public Observable<String> putImageAsync(Context ctx,final File file){
        return Observable.just(createName(file))
                .doOnNext(name -> CommonModel.getInstance().getQiNiuToken().subscribe(new ServiceResponse<Token>() {
                    @Override
                    public void onNext(Token token) {
                        mUploadManager.put(compressImage(ctx, file), name, token.getToken(), (key, info, response) -> {
                            if (!info.isOK()) JUtils.Toast("图片上传失败!");
                            else JUtils.Log("图片已上传");
                        }, null);
                    }
                }))
                .subscribeOn(Schedulers.io())
                .map(name -> ADDRESS + name)
                .compose(new DefaultTransform<>());
    }




    public Observable<String> putImageAsync(Context ctx,final File[] file){
        return Observable.from(file)
                .map(file1 -> {
                    String name = createName(file1);
                    CommonModel.getInstance().getQiNiuToken().subscribe(new ServiceResponse<Token>() {
                        @Override
                        public void onNext(Token token) {
                            mUploadManager.put(compressImage(ctx, file1), name, token.getToken(), (key, info, response) -> {
                                if (!info.isOK()) JUtils.Toast("图片上传失败!");
                                else JUtils.Log("图片已上传：" + name);
                            }, null);
                        }
                    });
                    return name;
                })
                .subscribeOn(Schedulers.io())
                .map(name -> ADDRESS + name)
                .compose(new DefaultTransform<>());
    }

    /**
     * 同步上传
     * @param file 需上传文件
     * @return 上传文件访问地址
     */
    public Observable<Image> putImageSync(Context ctx,final File file){
        String name = createName(file);
        return CommonModel.getInstance().getQiNiuToken()
                .flatMap(token -> Observable.create(new Observable.OnSubscribe<Image>() {
                    @Override
                    public void call(Subscriber<? super Image> subscriber) {
                        File f = compressImage(ctx, file);
                        Image i =  getSizeFromFile(f);
                        i.setUrl(ADDRESS + name);

                        mUploadManager.put(f, name, token.getToken(), (key, info, response) -> {
                            if (!info.isOK()) {
                                JUtils.Toast("图片上传失败!");
                                subscriber.onError(new Throwable("key:" + key + "  info:" + info + "  response:" + response));
                            } else {
                                JUtils.Log("图片已上传");
                                subscriber.onNext(i);
                            }
                            subscriber.onCompleted();
                        }, null);
                    }
                }))
                .doOnNext(s -> JUtils.Log("已上传：" + s))
                .compose(new DefaultTransform<>());
    }




    public Observable<Image> putImageSync(Context ctx,final File[] file){
        final int[] count = {0};
        return CommonModel.getInstance().getQiNiuToken()
                .flatMap(token -> Observable.create(new Observable.OnSubscribe<Image>() {
                    @Override
                    public void call(Subscriber<? super Image> subscriber) {
                        for (File temp : file) {
                            String name = createName(temp);
                            File f = compressImage(ctx, temp);
                            Image i = getSizeFromFile(f);
                            i.setUrl(ADDRESS + name);

                            mUploadManager.put(f, name, token.getToken(), (key, info, response) -> {
                                if (!info.isOK()) {
                                    JUtils.Toast("图片上传失败!");
                                    subscriber.onError(new Throwable("key:" + key + "  info:" + info + "  response:" + response));
                                } else {
                                    JUtils.Log("图片已上传");
                                    subscriber.onNext(i);
                                }
                                count[0]++;
                                if (count[0] == file.length)subscriber.onCompleted();
                            }, null);
                        }

                    }
                }))
                .doOnNext(s -> JUtils.Log("已上传：" + s))
                .compose(new DefaultTransform<>());
    }


    private File compressImage(Context ctx,File file){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        int reqHeight=IMAGE_HEIGHT_MIN;
        int reqWidth=IMAGE_WIDTH_MAX;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap= BitmapFactory.decodeFile(file.getPath(), options);

        File tempfile =  createTempImage(ctx);
        FileOutputStream baos;
        try {
            baos = new FileOutputStream(tempfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            baos.close();
        } catch (IOException e) {
            return null;
        }
        return tempfile;
    }

    private Image getSizeFromFile(File file){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);
        return new  Image(file.getPath(),options.outWidth,options.outHeight);
    }

    private File createTempImage(Context ctx){
        String state = Environment.getExternalStorageState();
        String name = Math.random()*10000+System.nanoTime()+".jpg";
        if(state.equals(Environment.MEDIA_MOUNTED)){
            // 已挂载
            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File tmpFile = new File(pic, name);
            return tmpFile;
        }else{
            File cacheDir = ctx.getCacheDir();
            File tmpFile = new File(cacheDir, name);
            return tmpFile;
        }
    }



}
