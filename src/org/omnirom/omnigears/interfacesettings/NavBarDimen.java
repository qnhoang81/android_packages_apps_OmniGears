
package org.omnirom.omnigears.interfacesettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;

public class NavBarDimen extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String TAG = "NavBarDimen";
    private static final String PREF_NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
    private static final String PREF_NAVIGATION_BAR_WIDTH = "navigation_bar_width";
    private static final String KEY_DIMEN_OPTIONS = "navbar_dimen";

    private static final int MENU_RESET = Menu.FIRST;

    ListPreference mNavigationBarHeight;
    ListPreference mNavigationBarHeightLandscape;
    ListPreference mNavigationBarWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.navbar_dimen_settings);

        mNavigationBarHeight =
            (ListPreference) findPreference(PREF_NAVIGATION_BAR_HEIGHT);
        String navbarHeight = Settings.System.getString(getContentResolver(), Settings.System.NAVIGATION_BAR_HEIGHT);
        if (navbarHeight != null) {
            mNavigationBarHeight.setValue(navbarHeight);
        }
        mNavigationBarHeight.setOnPreferenceChangeListener(this);

        mNavigationBarWidth =
            (ListPreference) findPreference(PREF_NAVIGATION_BAR_WIDTH);
        String navbarWidth = Settings.System.getString(getContentResolver(), Settings.System.NAVIGATION_BAR_WIDTH);
        if (navbarWidth != null) {
            mNavigationBarWidth.setValue(navbarWidth);
        }
        mNavigationBarWidth.setOnPreferenceChangeListener(this);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, MENU_RESET, 0, R.string.reset)
                .setIcon(R.drawable.ic_settings_backup) // use the backup icon
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESET:
                resetToDefault();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void resetToDefault() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(R.string.reset);
        alertDialog.setMessage(R.string.navbar_dimensions_reset_message);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mNavigationBarHeight.setValue("48");
                mNavigationBarWidth.setValue("42");
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, null);
        alertDialog.create().show();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mNavigationBarWidth) {
            String newVal = (String) newValue;
            int dp = Integer.parseInt(newVal);
            Settings.System.putInt(getContentResolver(), Settings.System.NAVIGATION_BAR_WIDTH,
                    dp);
            return true;
        } else if (preference == mNavigationBarHeight) {
            String newVal = (String) newValue;
            int dp = Integer.parseInt(newVal);
            Settings.System.putInt(getContentResolver(), Settings.System.NAVIGATION_BAR_HEIGHT,
                    dp);
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
