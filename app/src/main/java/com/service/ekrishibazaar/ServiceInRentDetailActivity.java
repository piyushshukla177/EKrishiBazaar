package com.service.ekrishibazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.service.ekrishibazaar.adapter.SliderAdapterExample;
import com.service.ekrishibazaar.model.SliderItem;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ServiceInRentDetailActivity extends AppCompatActivity {

    TextView name_tv, vid_tv, mobile_number_tv, joined_tv, state_tv, district_tv, block_tv, village_tv, machine_type_tv, work_tv, reaching_on_time_tv, price_tv, additional_details_tv;

    Context context;

    ArrayList<SliderItem> slider_list = new ArrayList();
    ImageView back_image, profile_imageview;

    Button view_profile_btn, make_offer_btn;
    String category_type, image1, image2, image3, user_first_name, user_last_name, vid, date_joined, user_mobile_no, profile_image, state, district, block, village;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_in_rent_detail);
        init();
    }

    void init() {
        context = this;
        machine_type_tv = findViewById(R.id.machine_type_tv);
        work_tv = findViewById(R.id.work_tv);
        reaching_on_time_tv = findViewById(R.id.reaching_on_time_tv);
        price_tv = findViewById(R.id.price_tv);
//        posted_by_tv = findViewById(R.id.posted_by_tv);

        additional_details_tv = findViewById(R.id.additional_details_tv);
        name_tv = findViewById(R.id.name_tv);
        vid_tv = findViewById(R.id.vid_tv);
        mobile_number_tv = findViewById(R.id.mobile_number_tv);
        joined_tv = findViewById(R.id.joined_tv);
        state_tv = findViewById(R.id.state_tv);
        district_tv = findViewById(R.id.district_tv);
        block_tv = findViewById(R.id.block_tv);
        village_tv = findViewById(R.id.village_tv);
        view_profile_btn = findViewById(R.id.view_profile_btn);
        make_offer_btn = findViewById(R.id.make_offer_btn);
        profile_imageview = findViewById(R.id.profile_imageview);
        back_image = findViewById(R.id.back_image);

        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ServiceInRentDetailActivity.super.onBackPressed();
                    }
                }
        );
        view_profile_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
        make_offer_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
        Intent intent = getIntent();
        category_type = intent.getStringExtra("category_type");
        image1 = intent.getStringExtra("image1");
        image2 = intent.getStringExtra("image2");
        image3 = intent.getStringExtra("image3");
        user_first_name = intent.getStringExtra("user_first_name");
        user_last_name = intent.getStringExtra("user_last_name");
        user_mobile_no = intent.getStringExtra("mobile");
        vid = intent.getStringExtra("vid");
        date_joined = intent.getStringExtra("joined_on");
        profile_image = intent.getStringExtra("profile_image");
        state = intent.getStringExtra("state");
        district = intent.getStringExtra("district");
        block = intent.getStringExtra("block");
        village = intent.getStringExtra("village");

        SliderItem s = new SliderItem();
        s.setDescription("");
        s.setImageUrl(image1);
        slider_list.add(s);

        SliderItem s1 = new SliderItem();
        s1.setDescription("");
        s1.setImageUrl(image2);
        slider_list.add(s1);

        SliderItem s2 = new SliderItem();
        s2.setDescription("");
        s2.setImageUrl(image3);
        slider_list.add(s2);

        SliderView sliderView = findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(new SliderAdapterExample(ServiceInRentDetailActivity.this, slider_list));
        sliderView.run();
        Glide.with(context)
                .load(profile_image)
                .fitCenter()
                .into(profile_imageview);

        name_tv.setText(user_first_name + "" + user_last_name);
        vid_tv.setText(vid);
        mobile_number_tv.setText(user_mobile_no);
        if (date_joined != null && date_joined.length() > 10) {
            joined_tv.setText(date_joined.substring(0, 10));
        }
        state_tv.setText(state);
        district_tv.setText(district);
        block_tv.setText(block);
        village_tv.setText(village);

        machine_type_tv.setText(intent.getStringExtra("machine_type"));
        work_tv.setText(intent.getStringExtra("work"));
        reaching_on_time_tv.setText(intent.getStringExtra("reaching_on_time"));
        price_tv.setText(intent.getStringExtra("price"));
//        posted_by_tv.setText(intent.getStringExtra("If_preganent"));
        additional_details_tv.setText(intent.getStringExtra("additional_details"));
    }
}

/*
Type of machine
work
reacjing ion time
price additioinal nfo
posted by
 */
