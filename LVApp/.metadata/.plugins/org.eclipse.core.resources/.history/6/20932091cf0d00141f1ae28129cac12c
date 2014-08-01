package com.example.contrastincrementor;

import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.samples.colorblobdetect.ColorBlobDetector;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class ImageEnhancer extends Activity implements CvCameraViewListener2,SensorEventListener {
    private static final String TAG = "OCVSample::Activity";

    
    public static final int      VIEW_MODE_RGBA      = 0;
    public static final int      VIEW_MODE_CONTRAST     = 1;
    private int                  mViewMode;
    
    
    private CameraBridgeViewBase mOpenCvCameraView;
    private MenuItem             mItemPreviewmoreContrast;
    
    private Size                 mSize0;

    private Mat                  mRgba;
    private Mat                  mIntermediateMat;
    private Mat                  mMat0;
    private MatOfInt             mChannels[];
    private MatOfInt             mHistSize;
    private int                  mHistSizeNum = 25;
    private MatOfFloat           mRanges;
    private Scalar               mColorsRGB[];
    private Scalar               mColorsHue[];
    private Scalar               mWhilte;
    private Point                mP1;
    private Point                mP2;
    private float                mBuff[];
    private Mat                  mSepiaKernel;
    private ColorBlobDetector    mDetector;
    private Scalar               CONTOUR_COLOR;
    private Scalar               mBlobColorRgba;
    private Scalar               mBlobColorHsv;
    private Mat                  mSpectrum;
   
    
    
    
    public static int           viewMode = VIEW_MODE_RGBA;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    double ht = 1.7;
    TextView Xc;
    
    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    
    public ImageEnhancer() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.image_enhancer);
        //get the sensor service
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //get the accelerometer sensor
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.image_enhancer);
        mOpenCvCameraView.setCvCameraViewListener(this);
        RelativeLayout relativeLayoutSensorsData = (RelativeLayout) findViewById(R.id.sensors_data_layout);
        relativeLayoutSensorsData.bringToFront();
        Xc = (TextView) findViewById(R.id.Xc);
    }

        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
         mItemPreviewmoreContrast = menu.add("Increase Contrast");
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        
		if (item == mItemPreviewmoreContrast)
            viewMode = VIEW_MODE_CONTRAST;
		
        
        return true;
    }

@Override
    public void onCameraViewStarted(int width, int height) {
	
	    mRgba = new Mat(height, width, CvType.CV_8UC4);
        mIntermediateMat = new Mat();
        mSize0 = new Size();
        mChannels = new MatOfInt[] { new MatOfInt(0), new MatOfInt(1), new MatOfInt(2) };
        mBuff = new float[mHistSizeNum];
        mHistSize = new MatOfInt(mHistSizeNum);
        mRanges = new MatOfFloat(0f, 256f);
        mMat0  = new Mat();
        mColorsRGB = new Scalar[] { new Scalar(200, 0, 0, 255), new Scalar(0, 200, 0, 255), new Scalar(0, 0, 200, 255) };
        mColorsHue = new Scalar[] {
                new Scalar(255, 0, 0, 255),   new Scalar(255, 60, 0, 255),  new Scalar(255, 120, 0, 255), new Scalar(255, 180, 0, 255), new Scalar(255, 240, 0, 255),
                new Scalar(215, 213, 0, 255), new Scalar(150, 255, 0, 255), new Scalar(85, 255, 0, 255),  new Scalar(20, 255, 0, 255),  new Scalar(0, 255, 30, 255),
                new Scalar(0, 255, 85, 255),  new Scalar(0, 255, 150, 255), new Scalar(0, 255, 215, 255), new Scalar(0, 234, 255, 255), new Scalar(0, 170, 255, 255),
                new Scalar(0, 120, 255, 255), new Scalar(0, 60, 255, 255),  new Scalar(0, 0, 255, 255),   new Scalar(64, 0, 255, 255),  new Scalar(120, 0, 255, 255),
                new Scalar(180, 0, 255, 255), new Scalar(255, 0, 255, 255), new Scalar(255, 0, 215, 255), new Scalar(255, 0, 85, 255),  new Scalar(255, 0, 0, 255)
        };
        mWhilte = Scalar.all(255);
        mP1 = new Point();
        mP2 = new Point();

        // Fill sepia kernel
        mSepiaKernel = new Mat(4, 4, CvType.CV_32F);
        mSepiaKernel.put(0, 0, /* R */0.189f, 0.769f, 0.393f, 0f);
        mSepiaKernel.put(1, 0, /* G */0.168f, 0.686f, 0.349f, 0f);
        mSepiaKernel.put(2, 0, /* B */0.131f, 0.534f, 0.272f, 0f);
        mSepiaKernel.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 1f);
    }

	@Override
	public void onCameraViewStopped() {
        // Explicitly deallocate Mats
        if (mIntermediateMat != null)
            mIntermediateMat.release();

        mIntermediateMat = null;
    }
	
	public boolean dispatchKeyEvent(KeyEvent event,CvCameraViewFrame inputFrame) {
	    int action = event.getAction();
	    int keyCode = event.getKeyCode();
	        switch (keyCode) {
	        case KeyEvent.KEYCODE_VOLUME_UP:
	            if (action == KeyEvent.ACTION_UP) {
	            	
	            	
	                //TODO
	            }
	            return true;
	        case KeyEvent.KEYCODE_VOLUME_DOWN:
	            if (action == KeyEvent.ACTION_DOWN) {
	                //TODO
	            }
	            return true;
	        default:
	            return super.dispatchKeyEvent(event);
	        }
	        }
	public Mat Contrastenhancer(CvCameraViewFrame inputFrame){
		Mat rgba = inputFrame.rgba();
        Mat newrgba = new Mat(rgba.rows(),rgba.cols(),rgba.type());                
        Imgproc.equalizeHist(rgba, newrgba);
        
		
		return newrgba;
        	
	}
	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		
		/*// Color Detection
		
	    mDetector.process(mRgba);
        List<MatOfPoint> contours = mDetector.getContours();
        Log.e(TAG, "Contours count: " + contours.size());
        Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);

        Mat colorLabel = mRgba.submat(4, 68, 4, 68);
        colorLabel.setTo(mBlobColorRgba);

        Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70, 70 + mSpectrum.cols());
        mSpectrum.copyTo(spectrumLabel);*/
        
        
        // Contrast Enhancement

		  Mat rgba = inputFrame.rgba();
		 
		  Mat newrgba = new Mat(rgba.rows(),rgba.cols(),rgba.type());
		    
		  if (viewMode == VIEW_MODE_RGBA) {
			  newrgba = rgba ;
		} else if (viewMode == VIEW_MODE_CONTRAST) {
			//rgba = Contrastenhancer(inputFrame);
			Imgproc.cvtColor(inputFrame.gray(), newrgba, Imgproc.COLOR_GRAY2RGBA, 4);
			
	                        
	        //Imgproc.equalizeHist(temprgba, newrgba);
			//Imgproc.cvtColor(inputFrame.gray(), newrgba, Imgproc.COLOR_GRAY2RGBA, 4);
			/*Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(19,19));
			Mat closed = new Mat(); // closed will have type CV_32F
			Imgproc.morphologyEx(rgba, closed, Imgproc.MORPH_CLOSE, kernel);
			Core.divide(rgba, closed, closed, 1, CvType.CV_32F);
			Core.normalize(closed, rgba, 0, 255, Core.NORM_MINMAX, CvType.CV_8U);
			Imgproc.threshold(rgba, rgba, -1, 255, Imgproc.THRESH_BINARY_INV);
			   // +Imgproc.THRESH_OTSU); 
			    * */
			    
					

		}
		
		return newrgba;
    }


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		 //   float x = event.values[0];
	      //  float y = event.values[1];
	    //    float z = event.values[2];
	        float[] g = new float[3]; 
	        g = event.values.clone();

	        double norm_Of_g = Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2]);

	        // Normalize the accelerometer vector
	        g[0] = (float) (g[0] / norm_Of_g);
	        g[1] = (float) (g[1] / norm_Of_g);
	        g[2] = (float) (g[2] / norm_Of_g);


	    //    double inclination = (Math.toDegrees(Math.acos(g[0])));


	        double rotation = (int) Math.round(Math.toDegrees(Math.atan2(g[0], g[2])));
	        double distance = ht*Math.tan(Math.PI*rotation/180);
	        //Toast.makeText(getApplicationContext(), String.valueOf(distance), Toast.LENGTH_SHORT).show();
	        Xc.setText("Distance" + "\t\t" + distance);
	        
		
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
	@Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
        mSensorManager.unregisterListener(this);
    }


    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

        
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*
        case ImageEnhancer.VIEW_MODE_CONTRAST:
        	System.out.println("hiiiiiiiiiiiiiiiiiiiiii");
            //Imgproc.equalizeHist(rgba, newrgba);  
            //Imgproc.GaussianBlur(rgba, newrgba, sizeRgba, 2);
            //Imgproc.cvtColor(rgba,newrgba,Imgproc.COLOR_BGR2GRAY);
        	Imgproc.cvtColor(inputFrame.gray(), mRgba, Imgproc.COLOR_GRAY2RGBA, 4);
                       break;
        default  :
        	newrgba = rgba ;
        	break;

        }
       
        return newrgba;
    }
	*/

}