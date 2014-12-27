package com.edu.thss.smartdental;

import java.util.ArrayList;

import com.edu.thss.smartdental.adapter.EMRListAdapter;
import com.edu.thss.smartdental.model.EMRElement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;



public class EMRTabAllFragment extends Fragment {

	private EMRListAdapter listAdapter;
	private EditText editText;
	private ListView list;
	private ArrayList<EMRElement> emrs;
	
	private Spinner spinner;
	private String searchClass; //ѡ����������סԺ������
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_emr_all, container,false);
		editText = (EditText)rootView.findViewById(R.id.emr_searchbox);
		editText.addTextChangedListener(filterTextWatcher);
		list = (ListView)rootView.findViewById(R.id.emr_list);
		initEMRS();
		listAdapter = new EMRListAdapter(emrs,this.getActivity().getApplicationContext());
		list.setAdapter(listAdapter);
		
		searchClass="0";
		spinner = (Spinner)rootView.findViewById(R.id.emr_all_spinner);
		//��������Դ
		String[] spinnerItems = getResources().getStringArray(R.array.spinnername);
		//������Դ
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,spinnerItems);
		//�󶨿ؼ�
		spinner.setAdapter(adapter);
		//����¼�
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			   searchClass = Integer.toString(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		return rootView;
	}

	private TextWatcher filterTextWatcher = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable s) {
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			s = searchClass+" "+s;
			Log.i("s", s.toString());
			listAdapter.getFilter().filter(s);	
		}
	};

	private void initEMRS(){
		emrs = new ArrayList<EMRElement>();
		EMRElement e = new EMRElement("��������","�������","2014-1-13",2);
		emrs.add(e);
		e = new EMRElement("�����","��ǻ�����Ƽ��","2014-1-13",1);
		emrs.add(e);
		e = new EMRElement("�����鱨�浥","��¼һ�β������Ľ��","2014-1-20",2);
		emrs.add(e);
		e = new EMRElement("���̼�¼","��¼�ڿ�ǻҽԺһ�ο������","2014-1-20",2);
		emrs.add(e);
		e = new EMRElement("�����¼","��¼һ���������������е��������","2014-1-20",2);
		emrs.add(e);
		e = new EMRElement("��Ժ��¼","��¼�ڿ�ǻҽԺסԺ���","2014-1-20",2);
		emrs.add(e);
	}

}
