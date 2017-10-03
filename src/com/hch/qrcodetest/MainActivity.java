package com.hch.qrcodetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dtr.zxing.activity.CaptureActivity;
import com.dtr.zxing.decode.DecodeThread;

public class MainActivity extends Activity {

	private Button qrcodeButton, barcodeButton, createButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		qrcodeButton = (Button) findViewById(R.id.qrcodeButton);
		barcodeButton = (Button) findViewById(R.id.barcodeButton);
		createButton = (Button) findViewById(R.id.createButton);
		// 二维码
		qrcodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);
				intent.putExtra("decodeMode", DecodeThread.QRCODE_MODE);
				startActivity(intent);
			}
		});
		// 条形码
		barcodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);
				intent.putExtra("decodeMode", DecodeThread.BARCODE_MODE);
				startActivity(intent);
			}
		});
		// 生成二维码
		createButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CreateCodeActivity.class);
				startActivity(intent);
			}
		});

	}
}
