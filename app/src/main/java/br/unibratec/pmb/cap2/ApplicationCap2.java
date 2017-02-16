package br.unibratec.pmb.cap2;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by thiago.sousa on 13/02/2017.
 */

public class ApplicationCap2 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}
