package com.edu.thss.smartdental;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class OneEMRActivity extends Activity {
	private WebView emrWebView;
	private Handler emrHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_emr);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg));
		
		this.emrWebView = (WebView)findViewById(R.id.emr_table);
		WebSettings webSettings = this.emrWebView.getSettings(); 
		webSettings.setBuiltInZoomControls(true); 
		webSettings.setSupportZoom(true);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		this.emrWebView.setHorizontalScrollBarEnabled(true);
		
		/*webSettings.setJavaScriptEnabled(true);
		this.emrWebView.addJavascriptInterface(new Object(){
			public void clickOnAndroid(){
				emrHandler.post(new Runnable(){

					@Override
					public void run() {
						emrWebView.loadUrl("javascript:function()");
						
					}
					
				});
			}
		}, "table");
         */
		//�жϲ�������
		int  emrclass = getIntent().getExtras().getInt("emrclass");
		if(emrclass==2){
			this.emrWebView.loadUrl("file:///android_asset/table2.html");
		}
		else if(emrclass == 3){
			this.emrWebView.loadUrl("file:///android_asset/table3.html");
		}
		this.emrWebView.getSettings().setDefaultTextEncodingName("utf-8") ;
	//	this.emrWebView.loadData(creatHtml(), "text/html", "utf-8");  
	//	this.emrWebView.loadDataWithBaseURL(null,creatHtml(), "text/html", "utf-8",null);
	}
    private String creatHtml(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("<html>").append("<head>");
    	sb.append("<meta charset=\"utf-8\"/>"); 
    	sb.append("</head><style type=\"text/css\">");
    	sb.append(".title{font-size: 30px;font-weight: bolder;}");
    	sb.append(".subtitle{font-size: 20px;font-weight: bolder;/*background-color: #ECF1EF;   */}");
    	sb.append(".basicInfo{background-color: #FFFFF2;}");
    	sb.append(".additionalInfo{background-color:#FBF5E6;}");
    	sb.append(".history{background-color:#FCF6CF;}");
    	sb.append(".visit{background-color:#effff8;}");
    	sb.append(".cost{background-color:#efffef}");
    	sb.append(".result{background-color:#eff8ff}");
    	sb.append(".surgery{background-color:#efefff}");
    	sb.append(".anesthesia{background-color:#fff8ef}");
    	sb.append(".hospital{background-color:#fff4f4}");
    	sb.append(".prescription{background-color:#ffffef}");
    	sb.append("</style><body style=\"width=600px; height=auto\">");
    	sb.append("<table width=\"100%\"border=\"1\" cellspacing=\"0px\" style=\"border-collapse:collapse;font-family:Microsoft YaHei\">");
    	sb.append("<caption class=\"title\">���Ӳ�����</caption>");
    	sb.append("<tr><td class=\"subtitle\" colspan=\"6\">���Ӳ�����ҳ</td></tr>");
    	sb.append("<tr class=\"basicInfo\">");
    	sb.append("<td width=\"13.3%\">����</td>");
    	sb.append("<td width=\"13.3%\">&nbsp;</td>"); //!!
    	sb.append("<td width=\"13.3%\">�Ա�</td>");
    	sb.append("<td width=\"13.3%\">&nbsp;</td>"); //!!
    	sb.append("<td width=\"13.3%\">��������</td>");
    	sb.append("<td width=\"13.3%\">&nbsp;</td>"); //!!
    	sb.append("</tr><tr class=\"basicInfo\">");
    	sb.append("<td>���֤��</td>");
    	sb.append("<td colspan=\"2\">&nbsp;</td>"); //!!
    	sb.append("<td>ҽ����</td>");
    	sb.append("<td colspan=\"2\">&nbsp;</td>"); //
    	sb.append("</tr><tr class=\"basicInfo\">");
    	sb.append("<td rowspan=\"4\">��ϵ��ʽ</td>");
    	sb.append("<td colspan=\"1\">��ͥסַ</td>");
    	sb.append("<td colspan=\"4\">&nbsp;</td>"); //
    	sb.append("</tr><tr class=\"basicInfo\">");
    	sb.append("<td colspan=\"1\">��ͥ�绰</td>");
    	sb.append("<td colspan=\"4\">&nbsp;</td>"); //
    	sb.append("</tr><tr class=\"basicInfo\">");
    	sb.append("<td colspan=\"1\">�ֻ�</td>");
    	sb.append("<td colspan=\"4\">&nbsp;</td>"); //
    	sb.append("</tr><tr class=\"basicInfo\">");
    	sb.append("<td colspan=\"1\">Email</td>");
    	sb.append("<td colspan=\"4\">&nbsp;</td>"); //
    	sb.append("</tr><tr class=\"additionalInfo\">");
    	sb.append("<td>����</td>");
    	sb.append("<td>&nbsp;</td>");//
    	sb.append("<td>����</td>");
    	sb.append("<td>&nbsp;</td>");//
    	sb.append("<td>Ѫ��</td>");
    	sb.append("<td>&nbsp;</td>"); //
    	sb.append("</tr><tr class=\"additionalInfo\">");
    	sb.append("<td>ְҵ</td>");
    	sb.append("<td colspan=\"2\">&nbsp;</td>");//
    	sb.append("<td>�ܽ����̶�</td>");
    	sb.append("<td colspan=\"2\">&nbsp;</td>");//
    	sb.append("</tr><tr class=\"additionalInfo\">");
    	sb.append("<td rowspan=\"2\">������ϵ��ʽ</td>");
    	sb.append("<td>��ͥסַ</td>");
    	sb.append("<td colspan=\"4\">&nbsp;</td>");//
    	sb.append("</tr><tr class=\"additionalInfo\">");
    	sb.append("<td>��ͥ�绰</td>");
    	sb.append("<td colspan=\"4\">&nbsp;</td>");//
    	sb.append("</tr><tr class=\"history\">");
    	sb.append("<td>����ʷ</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");//
    	sb.append("</tr><tr class=\"history\">");
    	sb.append("<td>����ʷ</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");//
    	sb.append("</tr><tr class=\"history\">");
    	sb.append("<td>����ʷ</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");//
    	sb.append("</tr><tr>");
    	sb.append("<td class=\"subtitle\"colspan=\"6\">������Ϣ</td></tr>");
    	sb.append("<tr class=\"visit\">");
    	sb.append("<td>����ʱ��</td>");
    	sb.append("<td colspan=\"2\">&nbsp;</td>"); // 
    	sb.append("<td>������</td>");
    	sb.append("<td colspan=\"2\">&nbsp;</td>");
    	sb.append("</tr><tr>");
    	sb.append("<td class=\"subtitle\" colspan=\"6\">����</td></tr>");
    	sb.append("<tr class=\"cost\">");
    	sb.append("<td>�ܷ���</td>");
    	sb.append("<td colspan=\"2\">&nbsp;</td>");
    	sb.append("<td>�Ƿ񽻿�</td>");
    	sb.append("<td colspan=\"2\">&nbsp;</td>");
    	sb.append("</tr><tr class=\"cost\">");
    	sb.append("<td>��ϸ</td>");
    	sb.append("<td colspan=\"5\"><table width=\"100%\"><tr>");
    	sb.append("<th>���</th><th>��Ŀ</th><th>����</th><th>����</th><th>�ϼ�</th></tr><tr>");
    	sb.append("<td>&nbsp;</tr>");
    	sb.append("<td>&nbsp;</tr>");
    	sb.append("<td>&nbsp;</tr>");
    	sb.append("<td>&nbsp;</tr>");
    	sb.append("<td>&nbsp;</tr>");
    	sb.append("</tr></table></td></tr><tr>");
    	sb.append("<td class=\"subtitle\" colspan=\"6\">������</td>");
    	sb.append("</tr><tr class=\"result\">");
    	sb.append("<td>����</td>");
    	sb.append("<td>&nbsp;</td>"); //
    	sb.append("<td>ҽ��</td>");
    	sb.append("<td>&nbsp;</td>");
    	sb.append("<td>��ʿ</td>");
    	sb.append("<td>&nbsp;</td>");
    	sb.append("</tr><tr class=\"result\">");
    	sb.append("<td>����</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>"); //
    	sb.append("</tr><tr class=\"result\">");
    	sb.append("<td>�����</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");
    	sb.append("</tr><tr class=\"result\">");
    	sb.append("<td>��Ͻ��</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");
    	sb.append("</tr><tr class=\"result\">");
    	sb.append("<td>���Ƽƻ�</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");
    	sb.append("</tr><tr class=\"result\">");
    	sb.append("<td>ҽ��</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");//
    	sb.append("</tr><tr>");
    	sb.append("<td class=\"subtitle\"colspan=\"6\">һ��������¼</td></tr>");
    	sb.append("<tr class=\"surgery\"><td colspan=\"6\">&nbsp;</td></tr>");
    	sb.append("<tr><td class=\"subtitle\"colspan=\"6\">����������¼</td></tr>");
    	sb.append("<tr class=\"anesthesia\">");
    	sb.append("<td>��ǰ���Ӽ�¼</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>"); //
    	sb.append("</tr><tr class=\"anesthesia\">");
    	sb.append("<td>�����¼</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");//
    	sb.append("</tr><tr class=\"anesthesia\">");
    	sb.append("<td>������Ӽ�¼</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");
    	sb.append("</tr><tr>");
    	sb.append("<td class=\"subtitle\" colspan=\"6\">סԺ��¼</td></tr>");
    	sb.append("<tr class=\"hospital\">");
    	sb.append("<td>��Ժ��¼</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");
    	sb.append("</tr><tr class=\"hospital\">");
    	sb.append("<td>�ճ����̼�¼</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");
    	sb.append("</tr><tr class=\"hospital\">");
    	sb.append("<td>��Ժ��¼</td>");
    	sb.append("<td colspan=\"5\">&nbsp;</td>");
    	sb.append("</tr><tr>");
    	sb.append("<td class=\"subtitle\"colspan=\"6\">����</td></tr>");
    	sb.append("<tr class=\"prescription\"><td colspan=\"6\">&nbsp;</td></tr>");
    	sb.append("</table></body></html>");
    	
    	return sb.toString();
    }

}
