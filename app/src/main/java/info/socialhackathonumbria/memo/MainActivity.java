package info.socialhackathonumbria.memo;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int REQUEST_VOICE_ROCGNITION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        button.setText("Apri Activity");
        button.setOnClickListener(this);

        //button.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        startVoiceRecognition();
        //    }
        //});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //startDetailsActivity();
                startVoiceRecognition();
                //findViewById(R.id.button).setEnabled(false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_VOICE_ROCGNITION:
                //findViewById(R.id.button).setEnabled(true);
                if (resultCode == RESULT_OK) {
                    List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String myMessage = results.get(0);
                    startDetailsActivity(myMessage);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startDetailsActivity(String message) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("message", message);
        startActivity(intent);
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, REQUEST_VOICE_ROCGNITION);
    }
}
