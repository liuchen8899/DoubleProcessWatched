package cn.edu.jlnu.doubleprocesswatched;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.edu.jlnu.doubleprocesswatched.service.LocalService;
import cn.edu.jlnu.doubleprocesswatched.service.RemoteService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startService(new Intent(this, LocalService.class));
        this.startService(new Intent(this, RemoteService.class));
    }
}
