package com.bangdev.wifichua.util;

public class AESCryptor  {

    public static native byte[] crypt(byte[] bArr, long j, int i);

    static {
        try {
           // System.loadLibrary("aes_ndk");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*

    public static byte[] getDBKey(Context context) {
        return Base64.decode(new String(crypt(hexStringToByteArray("ec1f5db766bc77940d038094b10886d722acd8f9aa05d7c694ba80c03fe515c0e8abb720cc95da61073f467b0677710925ef9b2b4545b7e11df55201b7bb16ae5d9806564fc8f59cbe34c378fd8c083ee7023d336d821f1f8934328497782203"), System.currentTimeMillis(), 1)), null);
    }
*/

    public static String getHotspotKey() {
        return bytes2HexStr(crypt(hexStringToByteArray("4de7229b1e17c186aee264fdf96b39eca13f18745a54cdccd32f6be68e6247e91e955cd56eeb893c32853310b81404a75347e34e8b648fb45fd3c743dad3ff07adb4c5e2e9db86e770f4e808161df327"), System.currentTimeMillis(), 1));
    }

    public static String getSecretKey() {
        return bytes2HexStr(crypt(hexStringToByteArray("10ce2c4534cee23e9299b5c9413f74ae7ab8e8e3cd1b4fc3e358c513fe3124ff6427667ac89874496fab738eaec6ef5427d9b64d4d1958c395b2f3254f0cb77b42ae7ed27b2f39fee6c6ba7d6099f2ae"), System.currentTimeMillis(), 1));
    }

    public static String getJWTKey() {
        return new String(crypt(hexStringToByteArray("baea60c18f0c61f8327d0e2031c198a71263b1553e8c052dc5c02587ab32c867"), System.currentTimeMillis(), 1));
    }

    public static String getQRKey() {
        return new String(crypt(hexStringToByteArray("2b86b8b4116dea2dfaab73fe29f535a4c7996c3f1e052ffa166d4bc517806a607fe0546b793de221b3d85dad4c4df28e6dc0803a599d5366958065708d447d3b04a06f6bcbfb276b14a0ee11d2c8c6b4b88cb2aab8457f706708150fae0728d8ddeefcf84c150d63331badfcdfcb46d7748f1cf7235276cd566c17aef102adfcb5f96b6ddfe8cd7e778c80220974f0a2"), System.currentTimeMillis(), 1));
    }

    public static byte[] hexStringToByteArray(String str) {
        if (str.length() < 1) {
            return null;
        }
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static String bytes2HexStr(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length != 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < bArr.length; i++) {
                    if ((bArr[i] & 255) < 16) {
                        // stringBuffer.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
                        stringBuffer.append("");
                    }
                    stringBuffer.append(Long.toHexString((long) (bArr[i] & 255)));
                }
                return stringBuffer.toString();
            }
        }
        return null;
    }
}