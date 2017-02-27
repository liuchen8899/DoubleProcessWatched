package cn.edu.jlnu.doubleprocesswatched.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import cn.edu.jlnu.doubleprocesswatched.ICommonAidlInterface;
import cn.edu.jlnu.doubleprocesswatched.R;

/**
 * Created by Administrator on 2017/2/23.
 */

public class RemoteService extends Service {

    private RemoteBinder mRemoteBinder;
    private RemoteServiceConnection conn;
    private PendingIntent mIntent;
    private NotificationManager mNotificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mRemoteBinder;
    }
    class RemoteBinder extends ICommonAidlInterface.Stub{
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //连接本地服务
        this.bindService(new Intent(this,LocalService.class),conn, Context.BIND_IMPORTANT);
        //提高服务优先级，避免被杀掉
        mIntent= PendingIntent.getService(this,0,intent,0);
        mNotificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("安全服务")
                .setContentText("防火防盗防Danny")
                .setContentIntent(mIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setTicker("安全服务启动中...")
                .build();
        mNotificationManager.notify(0, notification);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mRemoteBinder==null){
            mRemoteBinder=new RemoteBinder();
        }
        conn=new RemoteServiceConnection();
    }

    class RemoteServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //说明远程服务挂了
            RemoteService.this.startService(new Intent(RemoteService.this,LocalService.class));
            //连接远程服务
            RemoteService.this.bindService(new Intent(RemoteService.this,LocalService.class),conn,Context.BIND_IMPORTANT);
        }
    }
}
