package com.hch.qrcodetest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QrCodeActivity extends Activity {

	private static final int WHITE = 0xFFFFFFFF;
	private static final int BLACK = 0xFF000000;

	private ImageView qrcodeImage;
	private Button saveButton;
	private TextView tipText;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode);
		setTitle("生成二维码");

		Bundle extra = getIntent().getExtras();
		String content = extra.getString("content");

		qrcodeImage = (ImageView) findViewById(R.id.qrcodeImage);
		saveButton = (Button) findViewById(R.id.saveButton);
		tipText = (TextView) findViewById(R.id.tipText);

		bitmap = encodeAsBitmap(content);
		qrcodeImage.setImageBitmap(bitmap);

		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveBitmap(bitmap);
			}
		});

	}

	/**
	 * 生成二维码图像
	 * 
	 * @param content
	 * @return
	 */
	private Bitmap encodeAsBitmap(String content) {
		Bitmap bitmap = null;
		BitMatrix matrix = null;
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		try {
			// matrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE,
			// 400, 400);// 这种方法不支持生成中文二维码
			matrix = multiFormatWriter.encode(
					new String(content.getBytes("UTF-8"), "ISO-8859-1"),
					BarcodeFormat.QR_CODE, 400, 400);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		int w = matrix.getWidth();
		int h = matrix.getHeight();
		int[] pixels = new int[w * h];
		for (int y = 0; y < h; y++) {
			int offset = y * w;
			for (int x = 0; x < w; x++) {
				pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
			}
		}
		bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, w, 0, 0, w, h);

		return bitmap;
	}

	/**
	 * 保存二维码图像到本地
	 * 
	 * @param bitmap
	 */
	private void saveBitmap(Bitmap bitmap) {
		String filePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/qrcode/";
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String fileName = filePath + getFileName();
		File file = new File(fileName);
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		tipText.setText("二维码保存到：" + fileName);
		Toast.makeText(QrCodeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

	}

	private String getFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = format.format(new Date()) + ".png";
		return fileName;
	}
}
