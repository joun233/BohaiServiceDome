package com.example.bohaiservicedome.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.entity.Subject;
import com.example.bohaiservicedome.utils.LogUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;

public class PdfViewActivity extends BaseActivity {
    public static final String TAG = "PdfViewActivity";
    @BindView(R.id.pdfView)
    PDFView mPDFView;
    @BindView(R.id.img_pgbar)
    ImageView imgPgbar;
    private AnimationDrawable ad;
    private static int REQUEST_CODE_CONTACT = 101;
    private Subject subject;
    OkGo mOkGo = OkGo.getInstance();
    String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected int contentViewID() {
        return R.layout.activity_pdf_view;
    }

    @Override
    protected void initialize() {
        setLeftBtnFinish();
        ad = (AnimationDrawable) imgPgbar.getDrawable();
        imgPgbar.postDelayed(new Runnable() {
            @Override
            public void run() {
                ad.start();
            }
        }, 100);

        Intent intent = getIntent();
        subject = (Subject) intent.getSerializableExtra("subject");
        setTopTitle(subject.getTitle(), true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_CONTACT);
            }
        }
        showPdfView(subject);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CONTACT) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i(TAG, "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
            showPdfView(subject);
        }
    }

    /**
     * okgo下载pdf题库，显示pdf
     * @param subject
     */
    private void showPdfView(Subject subject) {
        if (subject == null){
            return;
        }
        try {
            final File file = new File(Environment.getExternalStorageDirectory() + File.separator + subject.getTitle());
            if (!file.exists()) {
                file.mkdirs();
            }
            String target = Environment.getExternalStorageDirectory() + File.separator + subject.getDesc() + ".pdf";
            mOkGo.<File>get(subject.getPdfUrl().getFileUrl())
                    .execute(new FileCallback(target, subject.getDesc()) {
                        @Override
                        public void onSuccess(Response<File> response) {
                            LogUtils.d(TAG, "报告下载成功..." + response.body());
                            try {
                                mPDFView.fromFile(response.body())
                                        .defaultPage(0)//默认展示第一页
                                        .load();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFinish() {
                            super.onFinish();
                            imgPgbar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onError(Response<File> response) {
                            super.onError(response);
                            LogUtils.e("下载失败", response.message());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOkGo.cancelTag(this);
    }
}
