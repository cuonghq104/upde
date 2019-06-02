package stp.cuonghq.upde.commons;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;

/**
 * Created by cuong.hq1 on 5/4/2019.
 */

public class DisplayTextView extends LinearLayout {

    View mLayout;
    @BindView(R.id.tv_title)
    AppCompatTextView mTvTitle;
    @BindView(R.id.tv_content)
    AppCompatTextView mTvContent;

    public DisplayTextView(Context context) {
        this(context, null);
    }

    public DisplayTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = inflater.inflate(R.layout.app_display_textview, this, true);
        ButterKnife.bind(this, mLayout);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DisplayTextView, 0, 0);
        String title = typedArray.getString(R.styleable.DisplayTextView_displayTitle);
        String content = typedArray.getString(R.styleable.DisplayTextView_displayContent);
        mTvTitle.setText(title);
        mTvContent.setText(content);
        typedArray.recycle();
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setContent(String content) {
        mTvContent.setText(content);
    }
}
