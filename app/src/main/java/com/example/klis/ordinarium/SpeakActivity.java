package com.example.klis.ordinarium;

import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
//import com.google.firebase.firestore.FirebaseFirestore;
//
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpeakActivity extends BaseActivity {

    private static final int SPEECH_REQUEST_CODE = 0;
    private TextView text, partName, textPL;
    private ImageButton miscBtn;

    private Client client;
    private Index index;
    private CompletionHandler completionHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = new Client("EPZ932AYK3", "9292e34ca4e0c67c02ccba8c84e5d16f");
        index = client.getIndex(language_code);

        text = (TextView)findViewById(R.id.textView);
        partName = (TextView)findViewById(R.id.name);
        textPL = (TextView)findViewById(R.id.textPL);
        miscBtn = (ImageButton) findViewById(R.id.miscButton);
        miscBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displaySpeechRecognizer();
            }
        });

        JSONObject settings;
        try {
            settings = new JSONObject()
                    .put("searchableAttributes", new JSONArray()
                            .put("K")
                            .put("W"));
//                    .put("searchableAttributes", "W")
//                    .put("removeWordsIfNoResults", "lastWords");
            index.setSettingsAsync(settings, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        completionHandler = new CompletionHandler() {
            @Override
            public void requestCompleted(JSONObject content, AlgoliaException error) {
                if(error!=null){
                    text.setText("error");
                }
                else{
                    try {
//                        System.out.println(language_code);
                        JSONObject _highlightResult = content.getJSONArray("hits").getJSONObject(0);
                        String answer = _highlightResult.getString("W");
                        if (_highlightResult.has("W1"))
                            answer += "\n\n" + _highlightResult.getString("W1");
                        text.setText(answer);
                        partName.setText(_highlightResult.getString("objectID"));
                        textPL.setText(_highlightResult.getString("pl"));
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        text.setText(content.toString());
                        text.setText("Nie znaleziono odpowiedzi.");
                    }
                }
            }
        };
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        System.out.println("Configuration change!");
//        if (newConfig.orientation ==
//                Configuration.ORIENTATION_LANDSCAPE) {
//            // Change things
//        } else if (newConfig.orientation ==
//                Configuration.ORIENTATION_PORTRAIT){
//            // Change other things
//        }
//    }

    @Override
    int getContentViewId() {
        return R.layout.activity_speak;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_speak;
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language_code);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            // Do something with spokenText
            index.searchAsync(new Query(spokenText),
                              completionHandler);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}