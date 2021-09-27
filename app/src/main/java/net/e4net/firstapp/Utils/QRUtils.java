package net.e4net.firstapp.Utils;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

import net.e4net.firstapp.Acitivities.QRActivity;

public class QRUtils {
    public static void startQRScan(Activity activity){
        IntentIntegrator ii = new IntentIntegrator(activity);
        ii.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        ii.setPrompt("QR이미지를 스캔해주세요");
        ii.setOrientationLocked(true);
        ii.setCaptureActivity(QRActivity.class);
        ii.initiateScan();
    }
}
