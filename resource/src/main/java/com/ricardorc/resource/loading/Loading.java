package com.ricardorc.resource.loading;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.ricardorc.resource.R;

public class Loading extends RelativeLayout {

    private static final int DEFAULT_TYPE_FACE = 0;
    private Context context;
    private RelativeLayout relativeLayout;
    private TextView messageTextViewAlertDialog;
    private LottieAnimationView lottieAnimationViewAlertDialog;
    private AlertDialog alertDialog;
    private TypedArray typedArray;
    private static final int DEFAULT_TEXT_SIZE = 16;

    public Loading(Context context) {
        super(context);
        views(context);
    }

    public Loading(Context context, AttributeSet attrs) {
        super(context, attrs);
        views(context);
        setTypeArray(context, attrs);
    }

    public Loading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        views(context);
        setTypeArray(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Loading(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        views(context);
        setTypeArray(context, attrs);
    }

    private void views(Context context) {
        inflate(getContext(), R.layout.custom_loading, this);
        binds(context);
    }

    private void binds(Context context) {
        this.context = context;
        this.relativeLayout = findViewById(R.id.relative_layout_custom_loading);
        this.relativeLayout.setVisibility(GONE);
    }

    private void setTypeArray(Context context, AttributeSet attrs) {
        this.typedArray = context.obtainStyledAttributes(attrs, R.styleable.Loading);
        setupTypeArray(this.typedArray, context);
        this.typedArray.recycle();
    }

    private void setupTypeArray(TypedArray typedArray, Context context) {
        this.relativeLayout.setBackgroundResource(typedArray.getResourceId(R.styleable.Loading_loading_background, R.drawable.background_transparent));
        setupAlertDialog();
        setupTextMessage();
        setupTextSizeMessage();
        setupTextColorMessage();
        //setupMessageTypeFace(typedArray, context);
    }

    private void setupAlertDialog() {
        this.alertDialog = new AlertDialog.Builder(this.context).create();
        View view = LayoutInflater.from(this.context).inflate(R.layout.custom_loading, null);
        this.messageTextViewAlertDialog = view.findViewById(R.id.message_text_view_custom_loading);
        this.lottieAnimationViewAlertDialog = view.findViewById(R.id.loading_lottie_animation_view);
        this.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.alertDialog.setView(view);
        this.alertDialog.setCancelable(false);
    }

    public void setupIconColor(int color) {
        SimpleColorFilter filter = new SimpleColorFilter(typedArray.getColor(R.styleable.Loading_loading_icon_color, color));
        this.lottieAnimationViewAlertDialog.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback<ColorFilter>(filter));
    }

    private void setupTextMessage() {
        this.messageTextViewAlertDialog.setText(typedArray.getText(R.styleable.Loading_loading_text_message));
    }

    private void setupTextSizeMessage() {
        int dimensionPixelSize = this.typedArray.getDimensionPixelSize(R.styleable.Loading_loading_text_size_message, DEFAULT_TEXT_SIZE);
        this.messageTextViewAlertDialog.setTextSize(TypedValue.COMPLEX_UNIT_SP, dimensionPixelSize);
    }

    private void setupTextColorMessage() {
        this.messageTextViewAlertDialog.setTextColor(typedArray.getColor(R.styleable.Loading_loading_text_color_message, getResources().getColor(R.color.white)));
    }

    /*private void setupMessageTypeFace(TypedArray typedArray, Context context) {
        int integer = typedArray.getInteger(R.styleable.Loading_loading_message_type_face, DEFAULT_TYPE_FACE);
        int fontName = 0;
        switch (integer) {
            case 0:
                fontName = R.string.regular;
                break;
            case 1:
                fontName = R.string.regular_italic;
                break;
            case 2:
                fontName = R.string.bold;
                break;
            case 3:
                fontName = R.string.bold_italic;
                break;
        }
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + context.getResources().getString(fontName) + ".ttf");
        this.messageTextViewAlertDialog.setTypeface(typeface);
    }*/

    public void setTextSizeMessage(float size) {
        this.messageTextViewAlertDialog.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setTextColorMessage(int color) {
        this.messageTextViewAlertDialog.setTextColor(color);
    }

    public void show(String message) {
        setTextMessage(message);
        this.alertDialog.show();
    }

    public void show() {
        this.alertDialog.show();
    }

    public void setTextMessage(String message) {
        this.messageTextViewAlertDialog.setText(message);
    }

    /***
     * Oculta y también destruye el diálogo. para volver a mostrar el diálogo, primero hay
     * que volver a crearlo.
     */
    public void dismiss() {
        this.alertDialog.dismiss();
    }
}
