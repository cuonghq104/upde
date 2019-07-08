package stp.cuonghq.upde.screen.profile;


import android.app.Activity;
import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.AppContext;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.AppToolbar;
import stp.cuonghq.upde.commons.AvatarResponse;
import stp.cuonghq.upde.commons.BaseFragment;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.screen.container.HostContainerActivity;
import stp.cuonghq.upde.screen.container.SupplierContainerActivity;
import stp.cuonghq.upde.screen.editinfo.EditInformationActivity;
import stp.cuonghq.upde.screen.listhome.ListHomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment<ProfileFragment, Presenter> implements Contract.View {

    public static final int EDIT_INFORMATION_REQUEST_CODE = 2244;

    public static final int PERMISSION_REQUEST_CODE = 1111;
    public static final int CAPTURE_IMAGE_REQUEST_CODE = 1122;
    public static final int CHOOSE_FROM_GALLERY_REQUEST_CODE = 2233;

    @BindView(R.id.btn_help)
    LinearLayout btnHelp;

    @BindView(R.id.rl_clip)
    RelativeLayout mRlClip;

    @BindView(R.id.tv_name)
    AppCompatTextView mTvName;

    @BindView(R.id.tv_email)
    AppCompatTextView mTvEmail;

    @BindView(R.id.btn_houses)
    LinearLayout mLLHouse;

    @BindView(R.id.tv_version)
    AppCompatTextView mTvVersion;

    @BindView(R.id.btn_profile)
    CircleImageView btnProfile;

    @BindView(R.id.toolbar)
    AppToolbar mToolbar;

    private LoginData data;
    private String role;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);
        initData();
        setupUI();
        addListener();
        return view;
    }

    private void addListener() {
        mToolbar.setRightBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logOut();
            }
        });
    }

    @Override
    protected Presenter initPresenter() {
        return new Presenter();
    }

    private void initData() {
        this.data = presenter.getUserData();
        this.role = presenter.getLoginType();
    }

    void updateUserInfo() {
        mTvEmail.setText(data.getEmail());
        mTvName.setText(data.getName());

        if (!StringUtils.equals(data.getAvatar(), "")) {
            Picasso.with(getActivity())
                    .load(Constants.imageUrl(data.getAvatar()))
                    .into(btnProfile);
        }
    }

    private void setupUI() {
        mRlClip.getBackground().setLevel(3000);
        //mTvName.setText(data.getEmail());
        mTvVersion.setText("Version: ");
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String version = pInfo.versionName;
            mTvVersion.append(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        updateUserInfo();
        mLLHouse.setVisibility((role.equalsIgnoreCase(Constants.LOGIN_AS_SUPPLIER_TYPE)) ? View.GONE : View.VISIBLE);
    }


    private void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btn_edit_information)
    public void editInformation() {
        Intent intent = EditInformationActivity.getInstance(getContext());
        startActivityForResult(intent, EDIT_INFORMATION_REQUEST_CODE);
    }

    @OnClick(R.id.btn_houses)
    public void houseList() {
        Intent intent = ListHomeActivity.getInstance(getContext());
        startActivity(intent);
    }

    @OnClick(R.id.btn_profile)
    public void getNewImage() {
        checkPermission();
    }
    @Override
    public void logOutSuccess(String msg) {
        Utilities.showToast(getContext(), msg);

        Activity mActivity = getActivity();
        if (mActivity instanceof SupplierContainerActivity) {
            ((SupplierContainerActivity) mActivity).logout();
        }

        else if (mActivity instanceof HostContainerActivity) {
            ((HostContainerActivity) mActivity).logout();
        }
    }

    @Override
    public void changeProfileImageSuccess(AvatarResponse response) {
        data.setAvatar(response.getAvatar());
        AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.DATA, data);
        updateUserInfo();
    }

    @Override
    public void changeProfileImageFailed(String msg) {
        Utilities.showToast(getActivity(), msg);
    }

    public void checkPermission() {
        if (!hasPermissions(AppContext.getInstance(), Constants.PERMISSION_NEEDED)) {
            requestPermissions(Constants.PERMISSION_NEEDED, PERMISSION_REQUEST_CODE);
        } else {
            showGetPictureDialog();
        }
    }

    private void showGetPictureDialog() {
        CharSequence options[] = new CharSequence[]{"Take new picture", "From gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Add a product image from: ");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    chooseFromGallery();
                } else if (which == 0) {
                    captureImage();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (hasPermissions(AppContext.getInstance(), Constants.PERMISSION_NEEDED)) {
            showGetPictureDialog();
        } else {
            Utilities.showToast(ProfileFragment.this.getContext(), "Permission denied");
        }
    }

    private void showImagePickerDialog() {
        CharSequence options[] = new CharSequence[]{"Take new picture", "From gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Add a product image from: ");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    chooseFromGallery();
                } else if (which == 0) {
                    captureImage();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void captureImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_FROM_GALLERY_REQUEST_CODE);
    }
    private Uri selectedImageUri;
    private String mImagePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CHOOSE_FROM_GALLERY_REQUEST_CODE) {
                selectedImageUri = data.getData();
                mImagePath = Utilities.getPathFromURI(getActivity(), selectedImageUri);
                if (mImagePath != null) {
                    File f = new File(mImagePath);
                    selectedImageUri = Uri.fromFile(f);
                }
                presenter.changeProfileImage(getActivity().getContentResolver().getType(selectedImageUri), Utilities.getPath(getContext(), selectedImageUri));

            } else if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                selectedImageUri = Utilities.getImageUri(getContext(), bitmap);
                mImagePath = Utilities.getPathFromURI(getActivity(), selectedImageUri);
                presenter.changeProfileImage(getActivity().getContentResolver().getType(selectedImageUri), Utilities.getPath(getContext(), selectedImageUri));

            } else if (requestCode == EDIT_INFORMATION_REQUEST_CODE) {
                boolean updated = data.getBooleanExtra(Constants.Extras.RESULT, false);
                if (updated) {
                    this.data = presenter.getUserData();
                    updateUserInfo();
                }
            }
        }
    }

}
