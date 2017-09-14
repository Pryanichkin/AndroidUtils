## SoundHelper and PreferencesHelper is simple, one file containing. So, they don't need an explanaton.

## Example of simple use of MusicHelper:

### 1) You need add to manifest this permision:
```xml
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE">
```
  
### 2) Example of MainActivity:
```java
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.pbteamstudio.androidhelpers.music.MusicListFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(R.id.fragment_container, new MusicListFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
```
### 3) Example of activity_main.xml:
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="10dp"
    android:paddingLeft="10dp"
    android:paddingRight="10dp"
    android:paddingTop="10dp"
    tools:context="com.pbteamstudio.androidutils.MainActivity">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/fragment_container"
        android:layout_alignParentStart="true" />
</RelativeLayout>
```
