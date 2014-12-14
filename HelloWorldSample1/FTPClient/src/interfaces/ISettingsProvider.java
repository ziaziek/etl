/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import settings.Settings;

/**
 *
 * @author Przemo
 */
public interface ISettingsProvider {
    
    Settings loadSettings();
    
    boolean saveSettings(Settings settings);
}
