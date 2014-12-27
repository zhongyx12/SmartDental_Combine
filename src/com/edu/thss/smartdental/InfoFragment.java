package com.edu.thss.smartdental;

import java.io.File;

import com.edu.thss.smartdental.db.DBManager;
import com.edu.thss.smartdental.model.general.SDPatient;
import com.edu.thss.smartdental.util.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoFragment extends Fragment{
	
	private String[] ops = new String[]{"ѡ�񱾵�ͼƬ","����"};
	private static final String IMAGE_FILE_NAME = "faceimage"; /*ͷ������*/
	/*������*/
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	private static final int EDIT_BASIC_CODE = 3;
	private static final int EDIT_EXTENDS_CODE = 4;
	private static final int EDIT_CONTACT_CODE = 5;
	private static final int EDIT_PASS_CODE = 6;
	private static final int EDIT_ALLERGY_CODE = 7;
	private static final int EDIT_FAMILY_CODE = 8;
	
	ImageView editbasic;
	ImageView editextends;
	ImageView editcontact;
	ImageView editpass;
	ImageView editallergy;
	ImageView editfamily;
	
	TextView tname;
	TextView tgender;
	TextView tbirth;
	TextView tid;
	TextView tmedicare;
	
	Context context;
	ImageView faceImage;
	
	/*���ݿ����*/
	private DBManager mgr;
	
	private View.OnClickListener listener = new View.OnClickListener(){

		@Override
		public void onClick(View arg0) {
			showDialog();
			
		}
		
	};
	
	private View.OnClickListener editlistener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == editbasic.getId()){
				Intent intent = new Intent(getActivity(),EditBasicInfoActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("name", tname.getText());
				intent.putExtra("gender", tgender.getText());
				intent.putExtra("birth", tbirth.getText());
				intent.putExtra("id", tid.getText());
				intent.putExtra("medicare", tmedicare.getText());
				startActivityForResult(intent,EDIT_BASIC_CODE);
			}
			if(v.getId() == editextends.getId()){
				
			}
			if(v.getId() == editcontact.getId()){
				
			}
			if(v.getId() == editpass.getId()){
				
			}
			if(v.getId() == editallergy.getId()){
				
			}
			if(v.getId() == editfamily.getId()){
				
			}
			
		}
	};
	public InfoFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView =inflater.inflate(R.layout.fragment_info, container,false);
		
		this.context = rootView.getContext();
		mgr = new DBManager(this.context);
	    mgr.openDatabase();
		SDPatient patient= mgr.queryByName("��һ");
		Button upload = (Button)rootView.findViewById(R.id.infoupload);
		faceImage = (ImageView)rootView.findViewById(R.id.infoImageItem);
		
		//------textview---------
		tname = (TextView)rootView.findViewById(R.id.infoName);
		tgender = (TextView)rootView.findViewById(R.id.infogender_c);
		tbirth = (TextView)rootView.findViewById(R.id.infobirth_c);
		tid = (TextView)rootView.findViewById(R.id.infoid_c);
		tmedicare = (TextView)rootView.findViewById(R.id.infomedicare_c);
		
		tname.setText(patient.name);
		tbirth.setText(patient.birth);
		tid.setText(patient.idNum);
		//------button----------
		editbasic = (ImageView)rootView.findViewById(R.id.infobasic_edit);
		editextends = (ImageView)rootView.findViewById(R.id.infoextends_edit);
		editcontact = (ImageView)rootView.findViewById(R.id.infocontact_edit);
		editpass = (ImageView)rootView.findViewById(R.id.infopass_edit);
		editallergy = (ImageView)rootView.findViewById(R.id.infoallergy_edit);
		editfamily = (ImageView)rootView.findViewById(R.id.infofamily_edit);
		
		editbasic.setOnClickListener(editlistener);
		//this.context = rootView.getContext();
		upload.setOnClickListener(listener);
		
		return rootView;
	}
	
	/**
	 * ��ʾ�Ի���
	 * */
	private void showDialog(){
		new AlertDialog.Builder(context).setTitle("����ͷ��").setItems(ops, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				case 0:
					Intent intentFromLocal = new Intent();
					//�����ļ�����
					intentFromLocal.setType("image/*");
					intentFromLocal.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromLocal,IMAGE_REQUEST_CODE);
					break;
				case 1:
					Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if(Tools.hasSDCard()){
						File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
						File file = new File(path,IMAGE_FILE_NAME);
						intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					}
					startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
					break;
				}	
			}
		}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("result",Integer.toString(resultCode));
		if(requestCode == EDIT_BASIC_CODE&&resultCode == EditBasicInfoActivity.RESULT_CODE){
			Log.i("a","a");
			if(data != null){
				
				Bundle bundle = data.getExtras();
				tgender.setText(bundle.getString("gender"));
				tbirth.setText(bundle.getString("birth"));
				tid.setText(bundle.getString("id"));
				tmedicare.setText(bundle.getString("medicare"));
				
			}
		}
		else if(resultCode != Activity.RESULT_CANCELED){
			switch(requestCode){
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if(Tools.hasSDCard()){
					File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
					File tempFile = new File(path,IMAGE_FILE_NAME);
						startPhotoZoom(Uri.fromFile(tempFile));
				}else{
					Toast.makeText(getActivity(), "δ�ҵ��洢�����޷��洢��Ƭ��", Toast.LENGTH_LONG).show();
				}
				break;
			case RESULT_REQUEST_CODE:
				if(data != null){
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * �ü�ͼƬ
	 * */
   public void startPhotoZoom(Uri uri){
	   Intent intent = new Intent("com.android.camera.action.CROP");
	   intent.setDataAndType(uri, "image/*");
	   //���òü�
	   intent.putExtra("crop", "true");
	   intent.putExtra("aspectX", 1);
	   intent.putExtra("aspectY", 1);
	   intent.putExtra("outputX", 80);
	   intent.putExtra("outputY", 80);
	   intent.putExtra("return-data",true);
	   startActivityForResult(intent,RESULT_REQUEST_CODE);
   }
   /**
    * ����ü�֮���ͼƬ����
    * */
   private void getImageToView(Intent data){
	   Bundle extras = data.getExtras();
	   if(extras != null){
		   Bitmap photo = extras.getParcelable("data");
		   Drawable drawable = new BitmapDrawable(this.getActivity().getResources(),photo);
		   faceImage.setImageDrawable(drawable);
	   }
   }

@Override
public void onDestroy() {
	super.onDestroy();
	mgr.closeDB();
}
   
}
