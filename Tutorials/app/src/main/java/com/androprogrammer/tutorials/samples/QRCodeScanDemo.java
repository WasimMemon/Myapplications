package com.androprogrammer.tutorials.samples;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.util.Common;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QRCodeScanDemo extends Baseactivity {


	// permission request codes need to be < 256
	private static final int RC_HANDLE_CAMERA_PERM = 11;

	private View viewLayout;

	@Bind(R.id.layout_barcodeReader_container)
	CoordinatorLayout layoutBarcodeReaderContainer;
	@Bind(R.id.camera_view_barcode)
	SurfaceView cameraViewBarcode;

	private CameraSource mCameraSource;

	private static final String TAG = "BarcodeReaderActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

			//set the transition
			Transition ts = new Explode();
			ts.setDuration(5000);
			getWindow().setEnterTransition(ts);
			getWindow().setExitTransition(ts);
		}

		super.onCreate(savedInstanceState);

		// base class methods
		setSimpleToolbar(true);
		setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));
		setToolbarSubTittle(this.getClass().getSimpleName());

		// To display back arrow
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		setReference();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				finish();
				overridePendingTransition(0, R.anim.activity_close_scale);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setReference() {
		viewLayout = LayoutInflater.from(this).inflate(R.layout.activity_qr_codescan_demo, container);

		ButterKnife.bind(this, viewLayout);

		// Check for the camera permission before accessing the camera.  If the
		// permission is not granted yet, request permission.
		int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
		if (rc == PackageManager.PERMISSION_GRANTED) {
			createCameraSource(true);
		} else {
			requestCameraPermission();
		}
	}

	/**
	 * Handles the requesting of the camera permission.  This includes
	 * showing a "Snackbar" message of why the permission is needed then
	 * sending the request.
	 */
	private void requestCameraPermission() {
		Log.w(TAG, "Camera permission is not granted. Requesting permission");

		final String[] permissions = new String[]{Manifest.permission.CAMERA};

		if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
				Manifest.permission.CAMERA)) {
			ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
			return;
		}


		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ActivityCompat.requestPermissions(QRCodeScanDemo.this, permissions, RC_HANDLE_CAMERA_PERM);
			}
		};

		layoutBarcodeReaderContainer.setOnClickListener(listener);
		Snackbar.make(layoutBarcodeReaderContainer, R.string.permission_camera_rationale,
				Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, listener).show();
	}

	/**
	 * Callback for the result from requesting permissions. This method
	 * is invoked for every call on {@link #requestPermissions(String[], int)}.
	 * <p>
	 * <strong>Note:</strong> It is possible that the permissions request interaction
	 * with the user is interrupted. In this case you will receive empty permissions
	 * and results arrays which should be treated as a cancellation.
	 * </p>
	 *
	 * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
	 * @param permissions  The requested permissions. Never null.
	 * @param grantResults The grant results for the corresponding permissions
	 *                     which is either {@link PackageManager#PERMISSION_GRANTED}
	 *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
	 * @see #requestPermissions(String[], int)
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
	                                       @NonNull int[] grantResults) {
		if (requestCode != RC_HANDLE_CAMERA_PERM) {
			Log.d(TAG, "Got unexpected permission result: " + requestCode);
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
			return;
		}

		if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			Log.d(TAG, "Camera permission granted - initialize the camera source");
			// permission granted, so create the camera source
			createCameraSource(true);
			return;
		}

		Log.e(TAG,
				"Permission not granted: results len = " + grantResults.length + " Result code = "
						+ (grantResults.length > 0 ? grantResults[0] : "(empty)"));

	}

	/**
	 * Creates and starts the camera.  Note that this uses a higher resolution in comparison
	 * to other detection examples to enable the barcode detector to detect small barcodes
	 * at long distances.
	 * <p>
	 * Suppressing InlinedApi since there is a check that the minimum version is met before using
	 * the constant.
	 */
	@SuppressLint("InlinedApi")
	private void createCameraSource(boolean autoFocus) {
		Context context = getApplicationContext();

		// A barcode detector is created to track barcodes.  An associated multi-processor instance
		// is set to receive the barcode detection results, track the barcodes, and maintain
		// graphics for each barcode on screen.  The factory is used by the multi-processor to
		// create a separate tracker instance for each barcode.
		BarcodeDetector barcodeDetector =
				new BarcodeDetector.Builder(context)
						.setBarcodeFormats(Barcode.QR_CODE | Barcode.ISBN)
						.build();

		if (!barcodeDetector.isOperational()) {
			Log.w(TAG, "Detector dependencies are not yet available.");

			// Check for low storage.  If there is low storage, the native library will not be
			// downloaded, so detection will not become operational.
			IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
			boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

			if (hasLowStorage) {
				Common.showToast(this, getString(R.string.low_storage_error));
				Log.w(TAG, getString(R.string.low_storage_error));
			}
		}

		cameraViewBarcode.getHolder().addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) {

				try {
					mCameraSource.start(cameraViewBarcode.getHolder());
				} catch (IOException ie) {
					Log.e("CAMERA SOURCE ERROR", ie.getMessage());
				}

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				mCameraSource.stop();
			}
		});

		barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
			@Override
			public void release() {
			}

			@Override
			public void receiveDetections(Detector.Detections<Barcode> detections) {

				final SparseArray<Barcode> barcodes = detections.getDetectedItems();

				if (barcodes.size() != 0) {

					Snackbar.make(layoutBarcodeReaderContainer, barcodes.valueAt(0).displayValue,
							Snackbar.LENGTH_INDEFINITE).show();
				}
			}
		});

		// Creates and starts the camera.  Note that this uses a higher resolution in comparison
		// to other detection examples to enable the barcode detector to detect small barcodes
		// at long distances.
		CameraSource.Builder builder =
				new CameraSource.Builder(getApplicationContext(), barcodeDetector).setFacing(
						CameraSource.CAMERA_FACING_BACK)
						.setRequestedPreviewSize(640, 480)
						.setRequestedFps(15.0f);

		// make sure that auto focus is an available option
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			builder.setAutoFocusEnabled(autoFocus);

		}

		mCameraSource = builder.build();

	}
}