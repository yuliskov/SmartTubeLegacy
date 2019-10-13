package com.liskovsoft.smartyoutubetv.flavors.common;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.liskovsoft.sharedutils.dialogs.YesNoDialog;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.misc.MainApkUpdater;

public class CrashHandlerActivity extends AppCompatActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YesNoDialog.create(this, R.string.app_crashed, this, R.style.AppDialog);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //Yes button clicked
                new MainApkUpdater(this).start();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                finish();
                break;
        }
    }
}
