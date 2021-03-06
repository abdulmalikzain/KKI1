package abdul.malik.intaihere.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import abdul.malik.intaihere.MenuAnggota.AnggotaActivity;
import abdul.malik.intaihere.MenuProfile.ProfileActivity;
import abdul.malik.intaihere.MenuSetting.SettingActivity;
import abdul.malik.intaihere.MenuTask.TaskActivity;
import abdul.malik.intaihere.R;

/**
 * Created by Lenovo on 04/01/2018.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
        bottomNavigationViewEx.setTextSize(10);

    }


    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

//                    case R.id.ic_house:
//                        Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0
//                        context.startActivity(intent1);
//                        break;
                    case R.id.ic_profile:
                        Intent intent2 = new Intent(context, TaskActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_setting:
                        Intent intent3 = new Intent(context, SettingActivity.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_lingkaran:
                        Intent intent4 = new Intent(context, AnggotaActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        break;

                }
                return false;
            }
        });
    }
}
