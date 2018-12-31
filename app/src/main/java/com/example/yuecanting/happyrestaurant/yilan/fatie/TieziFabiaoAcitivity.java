package com.example.yuecanting.happyrestaurant.yilan.fatie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yuecanting.happyrestaurant.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by 17631 on 2018/12/28.
 */

public class TieziFabiaoAcitivity extends AppCompatActivity implements View.OnClickListener{

    EditText tiezi_title,tiezi_content;
    RatingBar tiezi_ratingbar1,tiezi_ratingbar2,tiezi_ratingbar3;
    TextView tiezi_currenttime;
    Button tiezi_pingjia;
    ImageView tiezi_takephoto;

    String url,title,comment,currentDate;
    Float dianmian,caipin,fuwu;

    
    String meishi_objectId;       //美食的id
    String cantingId;             //餐厅的Id
    BmobFile bmobFile;

    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meishi_tiezi_fabiao);

        initView();

        //接收美食的objectId
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        meishi_objectId = data.getString("meishi_objectId");
        cantingId = data.getString("cantingId");

        getCurrentTime();


        //photopicker  使用参考 https://github.com/donglua/PhotoPicker
        recyclerView = (RecyclerView) findViewById(R.id.tiezi_takephoto_recyclerview);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        //PhotoPicker选择单张图片
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(1)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(TieziFabiaoAcitivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(TieziFabiaoAcitivity.this);
                        }
                    }
                }));

    }

    //得到当前时间
    private void getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        currentDate = simpleDateFormat.format(date);
        tiezi_currenttime.setText(""+simpleDateFormat.format(date));
    }

    private void initView() {
        tiezi_takephoto = (ImageView) findViewById(R.id.tiezi_takephoto);
        tiezi_title = (EditText) findViewById(R.id.tiezi_title);
        tiezi_content = (EditText) findViewById(R.id.tiezi_content);
        tiezi_ratingbar1 = (RatingBar) findViewById(R.id.tiezi_ratingbar1);
        tiezi_ratingbar2 = (RatingBar) findViewById(R.id.tiezi_ratingbar2);
        tiezi_ratingbar3 = (RatingBar) findViewById(R.id.tiezi_ratingbar3);
        tiezi_currenttime = (TextView) findViewById(R.id.tiezi_currenttime);
        tiezi_pingjia = (Button) findViewById(R.id.tiezi_pingjia);

        tiezi_takephoto.setOnClickListener(this);
        tiezi_pingjia.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tiezi_takephoto:
                //此处无，相机相册在上面的点击事件处理
                break;
            case R.id.tiezi_pingjia:
                pingjia();
                break;
        }
    }





    //提交评价
    private void pingjia() {
        url = bmobFile.getFileUrl();
        title = tiezi_title.getText().toString();
        dianmian = tiezi_ratingbar1.getRating();
        caipin = tiezi_ratingbar2.getRating();
        fuwu = tiezi_ratingbar3.getRating();
        comment = tiezi_content.getText().toString();

        if (title.equals("") || comment.equals("") || url.equals("")){
            Toast.makeText(getBaseContext(),"请输入完整帖子哦",Toast.LENGTH_SHORT).show();
        }else {
            Tiezi tiezi = new Tiezi();
            tiezi.setUserId(BmobUser.getCurrentUser().getObjectId());
            tiezi.setCantingId(cantingId);
            tiezi.setMeishiId(meishi_objectId);
            tiezi.setTitle(title);
            tiezi.setContent(comment);
            tiezi.setCaipin(caipin);
            tiezi.setDianmian(dianmian);
            tiezi.setFuwu(fuwu);
            tiezi.setXihuan(0);
            tiezi.setUrl(url);
            tiezi.setCurrentDate(currentDate);
            tiezi.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if(e==null){
                        startActivity(new Intent(getBaseContext(),FaBiaoOkActivity.class));
                        Toast.makeText(getBaseContext(),"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getBaseContext(),"创建数据失败：" + e.getMessage()+objectId,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {
                selectedPhotos.addAll(photos);

            }

            //选择了照片并展示出来
            photoAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            tiezi_takephoto.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(selectedPhotos.get(0)).into(tiezi_takephoto);
            //上传图片
            uploadpic(selectedPhotos.get(0));

        }


    }

    //上传图片
    private void uploadpic(String s) {

        String picPath = s;
        bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(getApplicationContext(),"上传图片成功" ,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"上传文件失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });

    }


}
