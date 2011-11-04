/*
 * PushScreenRunnable.java
 * By: Denver Coneybeare <denver.coneybeare@gmail.com>
 * Nov 04, 2011
 *
 * Copyright 2011 Denver Coneybeare
 *
 * This file is part of SVGEditor.
 * 
 * SVGEditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SVGEditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SVGEditor.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package denver.svgeditor.ui;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;

/**
 * A Runnable that pushes a screen onto the display stack. This class is useful
 * for pushing screens from non-event threads using
 * {@link UiApplication#invokeAndWait(Runnable)} or
 * {@link UiApplication#invokeLater(Runnable)}.
 */
public class PushScreenRunnable implements Runnable {

    private final Screen screen;
    private final UiApplication app;

    /**
     * Creates a new instance of <code>PushScreenRunnable</code>.
     * 
     * @param screen the screen to push onto the display stack
     * @param app the application onto whose display stack the screen is to be
     * pushed
     * @throws NullPointerException if any argument is null
     */
    public PushScreenRunnable(Screen screen, UiApplication app) {
        if (screen == null) {
            throw new NullPointerException("screen==null");
        } else if (app == null) {
            throw new NullPointerException("app==null");
        }
        this.screen = screen;
        this.app = app;
    }

    /**
     * Returns the UiApplication object onto whose display stack the screen will
     * be pushed by this object's run() method.
     * 
     * @return the UiApplication object that was specified to the constructor;
     * never returns null
     */
    public UiApplication getApp() {
        return this.app;
    }

    /**
     * The screen that will be pushed onto the display stack by this object's
     * run() method.
     * 
     * @return the Screen object that was specified to the constructor; never
     * returns null
     */
    public Screen getScreen() {
        return this.screen;
    }

    /**
     * Pushes the screen onto the display stack. This method invokes
     * {@link #getApp()} and invokes its
     * {@link UiApplication#pushScreen(Screen)} method, specifying the screen
     * returned from {@link #getScreen()}.
     */
    public void run() {
        final UiApplication app = this.getApp();
        final Screen screen = this.getScreen();
        app.pushScreen(screen);
    }

}
