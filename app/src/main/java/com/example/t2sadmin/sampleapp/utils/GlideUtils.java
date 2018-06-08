package com.example.t2sadmin.sampleapp.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.t2sadmin.sampleapp.main.Application;


public class GlideUtils {

    public static GlideUtils GLIDE_INSTANCE;
    private Context mContext;

    public GlideUtils(Context context) {
        mContext = context;
    }

    public static GlideUtils getInstance() {
        if (GLIDE_INSTANCE == null)
            GLIDE_INSTANCE = new GlideUtils(Application.getAppContext());
        return GLIDE_INSTANCE;
    }

    public void setZoomImageWithUrl(ZoomImageView mImgView, String mUrl, int mErrorImg) {

        Glide.with(mContext)
                .load(mUrl)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .error(mErrorImg)
                        .centerCrop())
                .into(mImgView);

    }

    public void setZoomImageWithUri(ZoomImageView mImgView, Uri mUri, int mErrorImg) {

        Glide.with(mContext)
                .load(mUri)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .error(mErrorImg)
                        .centerCrop())
                .into(mImgView);

    }

    public void setImageWithUrl(ImageView mImgView, String mUrl, int mErrorImg) {

        Glide.with(mContext)
                .load(mUrl)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .error(mErrorImg)
                        .centerCrop())
                .into(mImgView);

    }

    public void setProfileImageWithUri(ImageView mImgView, Uri mUri, int mErrorImg) {

        Glide.with(mContext)
                .load(mUri)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .error(mErrorImg)
                        .override(200, 200)
                        .centerCrop())
                .into(mImgView);

    }
}
