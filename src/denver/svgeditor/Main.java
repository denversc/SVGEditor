/*
 * Main.java
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
package denver.svgeditor;

import denver.svgeditor.strings.SVGEditorResource;
import denver.svgeditor.ui.PushScreenRunnable;
import denver.svgeditor.ui.SVGEditorScreen;

import net.rim.device.api.i18n.ResourceBundle;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.util.StringProvider;

public class Main extends UiApplication implements Runnable {

    private static ResourceBundle resourceBundle;

    private final String[] args;

    /**
     * Creates a new instance of <code>Main</code>.
     * 
     * @param args the arguments to use; a reference to the given array is
     * stored internally and therefore undefined behaviour may occur if the
     * array is modified outside of this object; may be null
     */
    public Main(String[] args) {
        this.args = args;
    }

    /**
     * Returns this object's args.
     * 
     * @return the args array that was specified to the constructor; may be null
     */
    public String[] getArgs() {
        return this.args;
    }

    /**
     * Runs this application based on the args that were given to the
     * constructor.
     */
    public void run() {

        // wait for the event thread to start to avoid any race conditions
        while (!this.hasEventThread()) {
            Thread.yield();
        }

        // process the args (at this point this is just to suppress warnings)
        final String[] args = this.getArgs();
        if (args != null && args.length > 0) {
            final String arg = args[0];
            if (arg != null) {
                arg.toString();
            }
        }

        // create the screen title (ignore any exceptions)
        String screenTitle = "SVG Editor";
        try {
            final String title = getTitle();
            if (title != null) {
                screenTitle = title;
            }
            final String version = getVersion();
            if (version != null) {
                screenTitle += " " + version;
            }
        } catch (final SecurityException e) {
            // permission to access code module management denied; oh well
        }

        // create the main screen and push it onto the display stack
        final SVGEditorScreen screen = new SVGEditorScreen();
        screen.setTitle(screenTitle);
        this.invokeAndWait(new PushScreenRunnable(screen, this));
    }

    /**
     * Returns the resource bundle for this application. The first invocation of
     * this method within an application does the work to parse and load the
     * bundle; all subsequent invocations will receive the exact same object
     * which was cached from the first invocation.
     * 
     * @return the resource bundle for this application; never returns null
     */
    public static ResourceBundle getResourceBundle() {
        ResourceBundle resourceBundle = Main.resourceBundle;
        if (resourceBundle == null) {
            final long bundleId = SVGEditorResource.BUNDLE_ID;
            final String bundleName = SVGEditorResource.BUNDLE_NAME;
            resourceBundle = ResourceBundle.getBundle(bundleId, bundleName);
        }
        return resourceBundle;
    }

    /**
     * Creates and returns a StringProvider for a string from this application's
     * resource bundle.
     * 
     * @param id the ID of the string to return
     * @return a newly-created StringProvider created from this application's
     * resource bundle (the one returned from {@link #getResourceBundle()})
     * whose value is the string with the given ID; never returns null
     */
    public static StringProvider getString(int id) {
        return new StringProvider(SVGEditorResource.BUNDLE_NAME, id);
    }

    /**
     * Gets the title of this application. This method retrieves the title from
     * the {@link ApplicationDescriptor} of the main entry point of this
     * application.
     * 
     * @return the title of this application, or null if it is unable to be
     * determined
     * @throws SecurityException if permission to access the code module
     * management functionality is denied by the OS
     */
    public static String getTitle() {
        final ApplicationDescriptor descriptor =
            Util.getApplicationDescriptorForClass(Main.class);
        final String title =
            (descriptor == null) ? null : descriptor.getLocalizedName();
        return title;
    }

    /**
     * Gets the version of this application. This method retrieves the version
     * from the {@link ApplicationDescriptor} of the main entry point of this
     * application.
     * 
     * @return the version of this application, or null if it is unable to be
     * determined
     * @throws SecurityException if permission to access the code module
     * management functionality is denied by the OS
     */
    public static String getVersion() {
        final ApplicationDescriptor descriptor =
            Util.getApplicationDescriptorForClass(Main.class);
        final String version =
            (descriptor == null) ? null : descriptor.getVersion();
        return version;
    }

    /**
     * Loads an image resource.
     * 
     * @param path the path of the resource to load
     * @return the EncodedImage that was loaded; returns null if no image with
     * the given path is found or if the file is not a valid image
     * @throws NullPointerException if path==null
     */
    public static EncodedImage loadImageResource(String path) {
        if (path == null) {
            throw new NullPointerException("path==null");
        }

        EncodedImage image;
        try {
            image = EncodedImage.getEncodedImageResource(path);
        } catch (final IllegalArgumentException e) {
            return null; // image is malformed or does not exist
        }

        return image;
    }

    /**
     * The main entry point.
     * 
     * @param args the startup arguments; may be null
     */
    public static void main(String[] args) {
        final Main app = new Main(args);
        new Thread(app).start();
        app.enterEventDispatcher();
    }
}
