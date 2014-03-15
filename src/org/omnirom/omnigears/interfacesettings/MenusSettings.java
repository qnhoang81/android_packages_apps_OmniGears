/*
 *  Copyright (C) 2013 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.omnirom.omnigears.interfacesettings;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;

import com.android.internal.util.omni.DeviceUtils;
import org.omnirom.omnigears.preference.SystemCheckBoxPreference;

public class MenusSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "MenusSettings";

    private Context mContext;
    private static final String POWER_MENU_CATEGORY = "category_power_menu";
    private static final String POWER_MENU_SCREENRECORD = "screenrecord_in_power_menu";
    private static final String POWER_MENU_MOBILE_DATA = "mobile_data_in_power_menu";

    private SystemCheckBoxPreference mScreenrecordPowerMenu;
    private SystemCheckBoxPreference mMobileDataPowerMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.menus_settings);

        PreferenceScreen prefSet = getPreferenceScreen();
        mContext = getActivity().getApplicationContext();
        PreferenceGroup powerMenuCategory = (PreferenceGroup)
                findPreference(POWER_MENU_CATEGORY);

        mMobileDataPowerMenu = (SystemCheckBoxPreference) prefSet.findPreference(POWER_MENU_MOBILE_DATA);
        if (!DeviceUtils.deviceSupportsMobileData(mContext)) {
            powerMenuCategory.removePreference(mMobileDataPowerMenu);
        }

        mScreenrecordPowerMenu = (SystemCheckBoxPreference) prefSet.findPreference(POWER_MENU_SCREENRECORD);
        if (!mContext.getResources().getBoolean(com.android.internal.R.bool.config_enableScreenrecordChord)) {
            powerMenuCategory.removePreference(mScreenrecordPowerMenu);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        return true;
    }
}
