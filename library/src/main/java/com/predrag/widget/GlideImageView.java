package com.predrag.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.pedoo.giv.R;

import java.io.File;


/**
 * GlideImageView is simple {@link ImageView} widget that utilizes image loading library Glide to
 * load Images or Gifs. <br/> <br/>
 *
 * <b>GlideImageView does not require to be put into separate thread for loading the resource.</b><br/><br/>
 *
 * <p>To start loading process call one of the load methods: <br/>
 * {@link #load(String)} Load with url provided as a String <br/>
 * {@link #load(File)} Load from provided File <br/>
 * {@link #load(Uri)} Load from provided Uri </p>
 *
 * <p>Placeholder resource and error resource can be set also: <br/>
 * {@link #setPlaceholder(int)} set placeholder from resources <br/>
 * {@link #setPlaceholder(Drawable)} set placeholder from Drawable <br/>
 * {@link #setError(int)} set error from resources <br/>
 * {@link #setError(Drawable)} set placeholder from Drawable </p>
 *
 * <p>Scale type is respected and can be set also: <br/>
 * {@link ImageView#setScaleType(ScaleType)} </p>
 *
 * <p>Note - Placeholder, error and scale type should be set before calling load method</p>
 *
 *
 * @version 1.0.0
 * @author Predrag Simonovski
 */
public class GlideImageView extends ImageView {

    private int mPlaceholderResourceId = 0;
    private int mErrorResourceId = 0;
    private boolean mPlaceholderSet;
    private boolean mErrorSet;

    private Context mContext;
    private Drawable mPlaceholderDrawable = null;
    private Drawable mErrorDrawable = null;
    private String mUrl = null;

    public GlideImageView(Context context) {
        super(context);
        mContext = context;
    }

    public GlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initFromAttributes(context, attrs, 0, 0);

        if(!TextUtils.isEmpty(mUrl))
            load(mUrl);
        else if(mPlaceholderSet)
            setBackgroundResource(mPlaceholderResourceId);
    }

    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initFromAttributes(context, attrs, defStyleAttr, 0);

        if(!TextUtils.isEmpty(mUrl))
            load(mUrl);
        else if(mPlaceholderSet)
            setBackgroundResource(mPlaceholderResourceId);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GlideImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initFromAttributes(context, attrs, defStyleAttr, defStyleRes);

        if(!TextUtils.isEmpty(mUrl))
            load(mUrl);
        else if(mPlaceholderSet)
            setBackgroundResource(mPlaceholderResourceId);
    }

    /**
     * Sets placeholder resource from resources
     *
     * @param resourceId Id of the resource
     */
    public void setPlaceholder(int resourceId) {
        mPlaceholderResourceId = resourceId;
        mPlaceholderSet = true;
    }

    /**
     * Sets placeholder resource from drawable
     *
     * @param drawable Drawable
     */
    public void setPlaceholder(Drawable drawable) {
        mPlaceholderDrawable = drawable;
        mPlaceholderSet = true;
    }

    /**
     * Sets error resource from resources
     *
     * @param resourceId Id of the resource
     */
    public void setError(int resourceId) {
        mErrorResourceId = resourceId;
        mErrorSet = true;
    }

    /**
     * Sets error resource from drawable
     *
     * @param drawable Drawable
     */
    public void setError(Drawable drawable) {
        mErrorDrawable = drawable;
        mErrorSet = true;
    }


    /**
     * Begin a load with Glide
     *
     * @param url Source
     * @throws IllegalArgumentException
     */
    public void load(String url) {

        /* Check if URL is present */
        if(TextUtils.isEmpty(url))
            throw new IllegalArgumentException("URL should not be empty");

        DrawableTypeRequest<String> drawableTypeRequest = Glide.with(mContext).load(url);

        loadResources(drawableTypeRequest);

        drawableTypeRequest.into(this);
    }

    /**
     * Begin a load with Glide
     *
     * @param uri Source
     * @throws IllegalArgumentException
     */
    public void load(Uri uri) {

        /* Check if URL is present */
        if(uri == null)
            throw new IllegalArgumentException("URL should not be null");

        DrawableTypeRequest<Uri> drawableTypeRequest = Glide.with(mContext).load(uri);

        loadResources(drawableTypeRequest);

        drawableTypeRequest.into(this);
    }

    /**
     * Begin a load with Glide
     *
     * @param file Source
     * @throws IllegalArgumentException
     */
    public void load(File file) {

        /* Check if URL is present */
        if(file == null)
            throw new IllegalArgumentException("File should not be null");

        DrawableTypeRequest<File> drawableTypeRequest = Glide.with(mContext).load(file);

        loadResources(drawableTypeRequest);

        drawableTypeRequest.into(this);
    }


    /**
     * Loads placeholder and error
     *
     * @param requestBuilder
     */
    private void loadResources(GenericRequestBuilder requestBuilder) {

        /* Define placeholder*/
        if(mPlaceholderSet) {
            if(mPlaceholderDrawable != null)
                requestBuilder.placeholder(mPlaceholderDrawable);
            else
                requestBuilder.placeholder(mPlaceholderResourceId);
        }

        /* Define error */
        if(mErrorSet) {
            if(mErrorDrawable != null)
                requestBuilder.error(mErrorDrawable);
            else
                requestBuilder.error(mErrorResourceId);
        }

    }


    /**
     * Initialize resources from attributes
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    private void initFromAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                .GlideImageView, defStyleAttr, defStyleRes);

        try {
            mUrl = a.getString(R.styleable.GlideImageView_url);
            mPlaceholderResourceId = a.getResourceId(R.styleable.GlideImageView_placeholder, 0);
            mErrorResourceId = a.getResourceId(R.styleable.GlideImageView_error, 0);
        } finally {
            a.recycle();
        }

        if(mPlaceholderResourceId != 0)
            mPlaceholderSet = true;

        if(mErrorResourceId != 0)
            mErrorSet = true;
    }
}
