package com.service.ekrishibazaar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutusActivity extends AppCompatActivity {

    ImageView back_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        back_image = findViewById(R.id.back_image);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AboutusActivity.super.onBackPressed();
                    }
                }
        );
    }
}
