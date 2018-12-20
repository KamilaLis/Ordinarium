package com.example.klis.ordinarium;

import android.os.Bundle;
import android.widget.TextView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ReadActivity extends BaseActivity {

    private TextView whole_mass;

    private Client client;
    private Index index;
    private CompletionHandler completionHandler;

    private String ordinarium[] = {
            "Pozdrowienie",
            "Akt pokuty",
            "Kyrie",
            "Gloria",
            "Liturgia słowa",
            "Pozdrowienie",
            "Przed Ewangelią",
            "Po Ewangelii",
            "Credo",
            "Przygotowanie darów",
            "Prefacja"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        whole_mass = (TextView)findViewById(R.id.whole_mass);

        client = new Client("EPZ932AYK3", "9292e34ca4e0c67c02ccba8c84e5d16f");
        index = client.getIndex(language_code);

//        BrowseIterator iterator = new BrowseIterator(index, new Query(), new BrowseIterator.BrowseIteratorHandler() {
//            @Override
//            public void handleBatch(@NonNull BrowseIterator iterator, JSONObject result, AlgoliaException error) {
//                // Handle the result/error. [...]
//                if(error!=null){
//                    whole_mass.setText("error");
//                }
//                whole_mass.setText(result.toString());
//            }
//        });
//        iterator.start();


        index.getObjectsAsync(Arrays.asList(ordinarium), new CompletionHandler() {
            @Override
            public void requestCompleted(JSONObject content, AlgoliaException error) {
                if(error!=null){
                    whole_mass.setText("error");
                }
                else{
                    try {
                        JSONArray _result = content.getJSONArray("results");
                        String result = "";
                        StringBuilder sB = new StringBuilder(result);
                        for (int i=0; i<_result.length(); ++i){
//                            SpannableString styledString = new SpannableString(_result.getJSONObject(i).getString("objectID"));
//                            styledString.setSpan(new StyleSpan(Typeface.BOLD), 0, styledString.length(), 0);
//                            whole_mass.setText(styledString);
//                            JSONArray names = _result.getJSONObject(i).names();
//                            for (int k=0; k < names.length(); k++) {
//                                if (names.getString(k).equals("pl")) continue;
//                                sB.append(_result.getJSONObject(i).getString(names.getString(k)));
//                                sB.append('\n');
////                                System.out.println(_result.getJSONObject(i).getString(names.getJSONObject(k).toString()));
//                            }
//                            sB.append('\n');\
                            sB.append(_result.getJSONObject(i).getString("objectID"));sB.append(": ");
                            sB.append(_result.getJSONObject(i).getString("pl"));sB.append("\nK: ");
                            sB.append(_result.getJSONObject(i).getString("K"));sB.append("\nW: ");
                            sB.append(_result.getJSONObject(i).getString("W"));
                            if (_result.getJSONObject(i).length() > 4){
                                sB.append("\nK: "); sB.append(_result.getJSONObject(i).getString("K1"));
                                sB.append("\nW: "); sB.append(_result.getJSONObject(i).getString("W1"));
                            }
                            sB.append("\n\n");
                        }
                        result = sB.toString();
                        whole_mass.setText(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        whole_mass.setText(content.toString());
//                        whole_mass.setText("Nie znaleziono odpowiedzi.");
                    }
                }
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_read;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_read;
    }

}