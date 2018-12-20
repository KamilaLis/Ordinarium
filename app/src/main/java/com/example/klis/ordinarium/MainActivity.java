package com.example.klis.ordinarium;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity {

    private RadioGroup radioLanguageGroup;
    private RadioButton radioLanguageButton;
    private Button btnSave;
    private Map<CharSequence, String> languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        languages = new HashMap<CharSequence, String>();
        languages.put("Angielski", "en-US");
        languages.put("Hiszpański", "es-ES");
        languages.put("Włoski", "it-IT");

        addListenerOnButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateRadioGroupState();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_language;
    }

    public void addListenerOnButton() {

        radioLanguageGroup = (RadioGroup) findViewById(R.id.radioLanguage);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioLanguageGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioLanguageButton = (RadioButton) findViewById(selectedId);

                CharSequence languageButtonText = radioLanguageButton.getText();
                language_code = languages.get(languageButtonText);
                radio_id = selectedId;
            }
        });
    }

    private void updateRadioGroupState(){
        if (radio_id > 0){
            radioLanguageGroup.check(radio_id);
        }
    }

}

//public class MainActivity extends AppCompatActivity {
//
//    protected BottomNavigationView navigation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(getContentViewId());
//
//        navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
//        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        updateNavigationBarState();
//    }
//
//    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
//    @Override
//    public void onPause() {
//        super.onPause();
//        overridePendingTransition(0, 0);
//    }
//
//    // handle navigation
//    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                    switch (menuItem.getItemId()) {
//                        case R.id.navigation_language:
//                            languageItemSelected();
//                            return true;
//                        case R.id.navigation_speak:
//                            speakItemSelected();
//                            return true;
//                        case R.id.navigation_read:
//                            readItemSelected();
//                            return true;
//                    }
//                    return false;
//                }
//            };
//
//    public void languageItemSelected(){
//
//    }
//
//    public void speakItemSelected(){
//
//    }
//
//    public void readItemSelected(){
//        startActivity(new Intent(this, ReadActivity.class));
//    }
//
//    private void updateNavigationBarState(){
//        int actionId = getNavigationMenuItemId();
//        selectBottomNavigationBarItem(actionId);
//    }
//
//    void selectBottomNavigationBarItem(int itemId) {
//        Menu menu = navigation.getMenu();
//        for (int i = 0, size = menu.size(); i < size; i++) {
//            MenuItem item = menu.getItem(i);
//            boolean shouldBeChecked = item.getItemId() == itemId;
//            if (shouldBeChecked) {
//                item.setChecked(true);
//                break;
//            }
//        }
//    }
//
//    public int getContentViewId(){
//        return R.layout.activity_main;
//    }
//
//    public int getNavigationMenuItemId(){
//        return R.id.navigation_speak;
//    }
//}
