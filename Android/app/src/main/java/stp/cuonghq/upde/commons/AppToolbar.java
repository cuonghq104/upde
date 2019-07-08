package stp.cuonghq.upde.commons;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;

public class AppToolbar extends RelativeLayout {
    View mLayout;
    @BindView(R.id.tv_title)
    AppCompatTextView mTvTitle;

    @BindView(R.id.btn_left)
    AppCompatImageButton mBtnLeft;

    @BindView(R.id.btn_right)
    AppCompatImageButton mBtnRight;

    String title;

    public AppToolbar(Context context) {
        super(context, null);
    }

    public AppToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = inflater.inflate(R.layout.layout_action_bar, this, true);
        ButterKnife.bind(this, mLayout);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppToolbar, 0, 0);
        updateTitle(typedArray);
        updateLeftButton(typedArray);
        updateRightButton(typedArray);
        updateTheme(typedArray);
        typedArray.recycle();
    }

    private void updateTheme(TypedArray typedArray) {
        int theme = typedArray.getInt(R.styleable.AppToolbar_toolbar_theme, 1111);
        int colorPrimary;
        int colorSecondary;

        if (theme == 1111) {
            colorPrimary = getResources().getColor(R.color.colorPrimary);
            colorSecondary = getResources().getColor(R.color.colorWhite);
        } else {
            colorPrimary = getResources().getColor(R.color.colorWhite);
            colorSecondary = getResources().getColor(R.color.colorPrimary);
        }

        this.setBackgroundColor(colorPrimary);
        mTvTitle.setTextColor(colorSecondary);
        mBtnLeft.setColorFilter(colorSecondary);
        mBtnRight.setColorFilter(colorSecondary);
    }

    private void updateRightButton(TypedArray typedArray) {
        int resId = typedArray.getResourceId(R.styleable.AppToolbar_right_src, 0);
        if (resId != 0) {
            mBtnRight.setImageResource(resId);
        }
    }

    private void updateLeftButton(TypedArray typedArray) {
        int resId = typedArray.getResourceId(R.styleable.AppToolbar_left_src, 0);
        if (resId != 0) {
            mBtnLeft.setImageResource(resId);
        }
    }

    private void updateTitle(TypedArray typedArray) {
        title = typedArray.getString(R.styleable.AppToolbar_title);
        mTvTitle.setText(title);

        int orientation = typedArray.getInt(R.styleable.AppToolbar_titleOrientation, Integer.MAX_VALUE);
        Log.d("updateTitle", (orientation == 12) ? "CENTER" : "START");
        if (orientation == 12) {
            orientationCenter();
        } else {
            orientationStart();
        }
    }

    private void orientationStart() {
        RelativeLayout.LayoutParams params = (LayoutParams) mTvTitle.getLayoutParams();
        params.addRule(RelativeLayout.RIGHT_OF, R.id.btn_left);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        mTvTitle.setLayoutParams(params);
    }

    private void orientationCenter() {
        RelativeLayout.LayoutParams params = (LayoutParams) mTvTitle.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTvTitle.setLayoutParams(params);
    }

    public void setTitle(String title) {
        this.title = title;
        mTvTitle.setText(title);
    }

    public void setLeftBtnListener(OnClickListener listener) {
        mBtnLeft.setOnClickListener(listener);
    }

    public void setRightBtnListener(OnClickListener listener) {
        mBtnRight.setOnClickListener(listener);
    }
}
