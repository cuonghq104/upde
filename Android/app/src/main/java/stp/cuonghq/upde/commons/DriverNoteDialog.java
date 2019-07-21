package stp.cuonghq.upde.commons;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;

public class DriverNoteDialog extends Dialog {

    public interface DriverNoteListener{
        void onAccept(String note);
        void onCancel();
    }

    DriverNoteListener listener;

    public DriverNoteDialog(Context context, DriverNoteListener listener) {
        this(context);
        this.listener = listener;
    }

    public DriverNoteDialog(Context context) {
        super(context);
    }

    @BindView(R.id.btn_cancel)
    AppCompatButton mBtnCancel;

    @BindView(R.id.btn_accept)
    AppCompatButton mBtnAccept;

    @BindView(R.id.tv_char_limit)
    AppCompatTextView mTvCharLimit;

    @BindView(R.id.edt_note)
    AppCompatEditText mEdtNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_note);
        ButterKnife.bind(this);
        mTvCharLimit.setText("0 / 200 " + getContext().getResources().getString(R.string.title_character));
        mBtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    String note = mEdtNote.getText().toString();
                    listener.onAccept(note);
                }
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });

        mEdtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                int maxLength = 200;

                if (length == maxLength) {
                    Utilities.showToast(getContext(), getContext().getResources().getString(R.string.title_limit_reached));
                }
                StringBuilder sb = new StringBuilder(length + " / " + maxLength + " ");
                sb.append(getContext().getResources().getString(R.string.title_character));
                mTvCharLimit.setText(sb);
            }
        });
    }
}
