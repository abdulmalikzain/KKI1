package abdul.malik.intaihere.MenuTask;

import android.content.Context;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import abdul.malik.intaihere.App.AppController;
import abdul.malik.intaihere.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailTaskActivity extends AppCompatActivity {
   private TextView tvDetUsername, tvDetWaktu, tvDetStatus, tvDetTujuan, tvFotoStatus;
   private ImageView ivFotoStatus;
   private String fotoStatus, fotoImage;
   private String TAG = "";
   Toolbar mActionToolbar;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        tvDetUsername = findViewById(R.id.tv_detUsername);
        tvDetWaktu    = findViewById(R.id.tv_detWaktu);
        tvDetStatus   = findViewById(R.id.tv_detStatus);
        tvDetTujuan   = findViewById(R.id.tv_detTujuan);
        ivFotoStatus  = findViewById(R.id.iv_detGambar);

        Bundle bundle = getIntent().getExtras();
        tvDetUsername.setText(bundle.getString("username"));
        tvDetWaktu.setText(bundle.getString("waktu"));
        tvDetStatus.setText(bundle.getString("status"));
        tvDetTujuan.setText(bundle.getString("tujuan"));
        fotoStatus = bundle.getString("foto_status");
        Picasso.with(getApplication()).load(fotoStatus).error(R.drawable.boy)
                .centerInside().resize(500, 1200).into(ivFotoStatus);


        mActionToolbar = findViewById(R.id.tabsdetailtask);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
