package me.imstudio.core.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class GpsUtils {

    private final static String TAG = GpsUtils.class.getSimpleName();

    private final Context context;
    private final Geocoder geocoder;
    private static GpsUtils instance;

    private GpsUtils(Context context) {
        this.context = context;
        this.geocoder = new Geocoder(context, Locale.getDefault());
    }

    public static GpsUtils request(Context context) {
        if (instance == null)
            instance = new GpsUtils(context);
        return instance;
    }

    public interface LocationCallback {
        void onNewLocationAvailable(GPSCoordinate location);
    }

    public Context getContext() {
        return context;
    }

    public void tryToGetCurrentLocation(final LocationCallback callback) {
        final LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return;
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            locationManager.requestSingleUpdate(criteria, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (addresses != null && addresses.size() > 0) {
                        callback.onNewLocationAvailable(
                                new GPSCoordinate(location.getLatitude(), location.getLongitude(),
                                        addresses.get(0).getAddressLine(0)));
                    }
                }

                @Override
                public void onStatusChanged(String status, int i, Bundle bundle) {
                    Log.d(TAG, status);
                }

                @Override
                public void onProviderEnabled(String status) {
                    Log.d(TAG, status);
                }

                @Override
                public void onProviderDisabled(String status) {
                    Log.d(TAG, status);
                }

            }, null);
        } else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                locationManager.requestSingleUpdate(criteria, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (addresses != null && addresses.size() > 0) {
                            callback.onNewLocationAvailable(
                                    new GPSCoordinate(location.getLatitude(), location.getLongitude(),
                                            addresses.get(0).getAddressLine(0)));
                        }
                    }

                    @Override
                    public void onStatusChanged(String status, int i, Bundle bundle) {
                        Log.d(TAG, status);
                    }

                    @Override
                    public void onProviderEnabled(String status) {
                        Log.d(TAG, status);
                    }

                    @Override
                    public void onProviderDisabled(String status) {
                        Log.d(TAG, status);
                    }

                }, null);
            }
        }
    }

    public static boolean isAvailable(Context context) {
        String locationProviders = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return !(locationProviders == null || locationProviders.equals(""));
    }

    public static class GPSCoordinate {

        private String address;
        private double latitude;
        private double longitude;

        GPSCoordinate(double latitude, double longitude, String address) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.address = address;
        }

        public double getLatitude() {
            return latitude;
        }

        public String getAddress() {
            return address;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}