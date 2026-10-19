package com.warehouse.ui.theme;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.jthemedetecor.OsThemeDetector;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Central theme manager.
 * - Default theme is LIGHT.
 * - On startup, matches OS theme (System mode).
 * - Listens for OS theme changes and switches live on the EDT.
 * - Persists the user's choice (System / Light / Dark) in Preferences.
 *
 * TODO: wire to backend (theme preference sync, if ever needed server-side)
 */
public final class ThemeManager {

    public enum Mode { SYSTEM, LIGHT, DARK }

    private static final String PREF_KEY = "inventory.theme.mode";
    private static final ThemeManager INSTANCE = new ThemeManager();

    private final Preferences prefs = Preferences.userNodeForPackage(ThemeManager.class);
    private Mode currentMode = Mode.SYSTEM;
    private final List<Runnable> themeChangeListeners = new ArrayList<>();
    private OsThemeDetector osDetector;

    private ThemeManager() {}

    public static ThemeManager getInstance() { return INSTANCE; }

    /** Call once on the EDT at startup. */
    public void initialize() {
        currentMode = loadMode();
        if (currentMode == Mode.SYSTEM) {
            applySystemTheme(false); // no animation on startup
            startOsListener();
        } else {
            applyMode(currentMode, false);
        }
    }

    /** Change theme mode manually (from top bar or Settings). */
    public void setMode(Mode mode) {
        if (mode == currentMode) return;
        currentMode = mode;
        saveMode(mode);
        if (mode == Mode.SYSTEM) {
            applySystemTheme(true);
            startOsListener();
        } else {
            stopOsListener();
            applyMode(mode, true);
        }
        fireThemeChange();
    }

    public Mode getMode() { return currentMode; }

    public void addThemeChangeListener(Runnable listener) {
        themeChangeListeners.add(listener);
    }

    public void removeThemeChangeListener(Runnable listener) {
        themeChangeListeners.remove(listener);
    }

    // ---------- internal ----------

    private void applyMode(Mode mode, boolean animate) {
        try {
            if (animate) FlatAnimatedLafChange.showSnapshot();

            if (mode == Mode.DARK) {
                FlatDarkLaf.setup();
            } else {
                FlatLightLaf.setup();
            }
            FlatLaf.updateUI();

            if (animate) FlatAnimatedLafChange.hideSnapshotWithAnimation();
        } catch (Exception ex) {
            // Fallback: if setup fails, do nothing (keep current LaF)
            System.err.println("Theme switch failed: " + ex.getMessage());
            if (animate) {
                try { FlatAnimatedLafChange.hideSnapshotWithAnimation(); } catch (Exception ignored) {}
            }
        }
    }

    private void applySystemTheme(boolean animate) {
        boolean osDark = isOsDark();
        applyMode(osDark ? Mode.DARK : Mode.LIGHT, animate);
    }

    private boolean isOsDark() {
        try {
            if (osDetector == null) {
                osDetector = OsThemeDetector.getDetector();
            }
            return osDetector.isDark();
        } catch (Throwable t) {
            // If detection fails, fall back to LIGHT
            System.err.println("OS theme detection failed, falling back to LIGHT: " + t.getMessage());
            return false;
        }
    }

    private void startOsListener() {
        try {
            if (osDetector == null) osDetector = OsThemeDetector.getDetector();
            osDetector.registerListener(isDark -> {
                // The listener may fire off the EDT; hop to EDT for LaF changes
                SwingUtilities.invokeLater(() -> {
                    if (currentMode == Mode.SYSTEM) {
                        applyMode(isDark ? Mode.DARK : Mode.LIGHT, true);
                        fireThemeChange();
                    }
                });
            });
        } catch (Throwable t) {
            System.err.println("Could not register OS theme listener: " + t.getMessage());
        }
    }

    private void stopOsListener() {
        // jSystemThemeDetector does not expose an unregister method in v3.6,
        // so we guard with the currentMode check in the listener.
        // TODO: wire to backend (if a newer detector API offers unregister, use it)
    }

    private void fireThemeChange() {
        for (Runnable r : new ArrayList<>(themeChangeListeners)) {
            r.run();
        }
    }

    private Mode loadMode() {
        String stored = prefs.get(PREF_KEY, Mode.SYSTEM.name());
        try {
            return Mode.valueOf(stored);
        } catch (IllegalArgumentException e) {
            return Mode.SYSTEM;
        }
    }

    private void saveMode(Mode mode) {
        prefs.put(PREF_KEY, mode.name());
    }
}