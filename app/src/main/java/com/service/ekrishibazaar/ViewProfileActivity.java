package com.service.ekrishibazaar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ViewProfileActivity extends AppCompatActivity {

    Context context;
    ImageView profile_imageview, back_image;
    TextView name_tv, vid_tv, mobile_number_tv, block_tv, district_tv, state_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        context = this;
        profile_imageview = findViewById(R.id.profile_imageview);
        back_image = findViewById(R.id.back_image);
        name_tv = findViewById(R.id.name_tv);
        vid_tv = findViewById(R.id.vid_tv);
        mobile_number_tv = findViewById(R.id.mobile_number_tv);
        block_tv = findViewById(R.id.block_tv);
        district_tv = findViewById(R.id.district_tv);
        state_tv = findViewById(R.id.state_tv);

        Intent intent = getIntent();
        Glide.with(context)
                .load(intent.getStringExtra("profile_url"))
                .fitCenter()
                .into(profile_imageview);
        name_tv.setText("Name : " + intent.getStringExtra("name"));
        vid_tv.setText("VID  : " + intent.getStringExtra("vid"));
        mobile_number_tv.setText("Mobile Number : " + intent.getStringExtra("mobile_number"));
        block_tv.setText("Block :" + intent.getStringExtra("block"));
        district_tv.setText("District : " + intent.getStringExtra("district"));
        state_tv.setText("State : " + intent.getStringExtra("state"));

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewProfileActivity.super.onBackPressed();
            }
        });
    }
}