package com.cn.org.cyberwayframework2_0.classes.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取硬件信息
 */
public class HardwareUtil {

    private Context context;

    private HardwareUtil(Context context){
        this.context = context;
    };

    private static HardwareUtil hardwareUtil;


    public static HardwareUtil getInstance(Context context){
        if (hardwareUtil == null){
            hardwareUtil = new HardwareUtil(context);
        }
        return hardwareUtil;
    }


    private String obtainSzImei(){
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }


    private String obtainM_szDevIDShort(){
        return  "35" + //we make this look like a valid IMEI
                Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 +
                Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10 +
                Build.ID.length()%10 +
                Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 +
                Build.TYPE.length()%10 +
                Build.USER.length()%10 ;
    }

    private String obtainM_szAndroidID(){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private String obtainM_szWLANMAC(){
        WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
       return wm.getConnectionInfo().getMacAddress();
    }

    private String obtainM_szBTMAC(){

        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return m_BluetoothAdapter.getAddress();
    }

    /**
     * 获取用户唯一标识码
     * @return
     */
    public String getDeviceId(){

        String m_szLongID = obtainSzImei() + obtainM_szDevIDShort()
                + obtainM_szAndroidID()+ obtainM_szWLANMAC() + obtainM_szBTMAC();
        // compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(),0,m_szLongID.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String m_szUniqueID = new String();
        for (int i=0;i<p_md5Data.length;i++) {
            int b =  (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                m_szUniqueID+="0";
            // add number to string
            m_szUniqueID+= Integer.toHexString(b);
        }   // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();

        return m_szUniqueID;
    }
}
