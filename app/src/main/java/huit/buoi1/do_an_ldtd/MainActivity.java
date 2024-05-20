package huit.buoi1.do_an_ldtd;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Đầu tiên, lấy ảnh từ drawable bằng ID của nó
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tai);
         tx1 = findViewById(R.id.tx1);


    }
    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
    private void callAPI(byte[] imageData){
        dcoApi2 api2 = new dcoApi2();
        api2.executeApiRequest(imageData, new dcoApi2.ApiResponseListener() {
            @Override
            public void onSuccess(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int predictedLabel = jsonObject.getInt("predicted_label");
                            //int enText = jsonObject.getJSONObject("predicted_label").getInt("text");
                            String strNumber = String.valueOf(predictedLabel);
                            tx1.setText(strNumber);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            tx1.setText(e.toString());
                        }
                    }
                });
            }
            @Override
            public void onFailure(final String errorMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tx1.setText(errorMessage);
                        //txt_en.setText(errorMessage);
                        //Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        //loadingPanel.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
}
