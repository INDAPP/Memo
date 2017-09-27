package info.socialhackathonumbria.memo;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import info.socialhackathonumbria.memo.fragments.DetailsFragment;
import info.socialhackathonumbria.memo.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnHomeFragmentInteractionListener {
    private final static int REQUEST_VOICE_RECOGNITION = 1;
    private List<String> results = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView =
                (BottomNavigationView)findViewById(R.id.bottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment(), "home");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_VOICE_RECOGNITION:
                //findViewById(R.id.button).setEnabled(true);
                if (resultCode == RESULT_OK) {
                    List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String myMessage = results.get(0);
                    //startDetailsActivity(myMessage);
                    this.results.add(myMessage);
                    setupHomeFragment();
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
        startActivityForResult(intent, REQUEST_VOICE_RECOGNITION);
    }

    private void setupHomeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("home");
        if (fragment != null && fragment instanceof HomeFragment) {
            HomeFragment homeFragment = (HomeFragment)fragment;
            String[] messages = this.results.toArray(new String[this.results.size()]);
            homeFragment.updateMessages(messages);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_first:
                replaceFragment(new HomeFragment(), "home");
                break;
            case R.id.menu_second:
                replaceFragment(new DetailsFragment(), "details");
                break;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.commit();
    }

    @Override
    public void onFabClick(View view) {
        startVoiceRecognition();
    }

    @Override
    public String[] getMessages() {
//        return new String[] {
//                "Frase di prova 1",
//                "Frase di prova 2",
//                "Altra frase di prova più lunga del normale"
//        };
        return results.toArray(new String[results.size()]);
    }
}
