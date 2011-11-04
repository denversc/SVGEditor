/*
 * SVGEditorScreen.java
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

import denver.svgeditor.Main;
import denver.svgeditor.strings.SVGEditorResource;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.container.MainScreen;

/**
 * The main screen for editing and viewing SVG content.
 */
public class SVGEditorScreen extends MainScreen {

    private Action newAction;
    private Action openAction;

    /**
     * Creates a new instance of <code>SVGEditorScreen</code>.
     */
    public SVGEditorScreen() {
        this.addMenuItem(new NewMenuItem(0, 0));
        this.addMenuItem(new OpenMenuItem(0, 1));
    }

    /**
     * Invokes the action to perform when the user selects to create a new file.
     * This method gets the action from {@link #getNewAction()} and invokes it
     * with this object if it is not null.
     * 
     * @return true if getNewAction() returned non-null and therefore was
     * invoked; false otherwise
     * @see #getNewAction()
     */
    public boolean doNewAction() {
        final Action action = this.getNewAction();
        boolean invoked;
        if (action == null) {
            invoked = false;
        } else {
            invoked = true;
            action.doAction(this);
        }
        return invoked;
    }

    /**
     * Invokes the action to perform when the user selects to create a new file.
     * This method gets the action from {@link #getOpenAction()} and invokes it
     * with this object if it is not null.
     * 
     * @return true if getOpenAction() returned non-null and therefore was
     * invoked; false otherwise
     * @see #getOpenAction()
     */
    public boolean doOpenAction() {
        final Action action = this.getOpenAction();
        boolean invoked;
        if (action == null) {
            invoked = false;
        } else {
            invoked = true;
            action.doAction(this);
        }
        return invoked;
    }

    /**
     * Gets the action to perform when the user selects to create a new file.
     * 
     * @return the action; may be null if not set
     * @see #setNewAction(SVGEditorScreen.Action)
     * @see #doNewAction()
     */
    public Action getNewAction() {
        return this.newAction;
    }

    /**
     * Gets the action to perform when the user selects to open an existing
     * file.
     * 
     * @return the action; may be null if not set
     * @see #setOpenAction(SVGEditorScreen.Action)
     * @see #doOpenAction()
     */
    public Action getOpenAction() {
        return this.openAction;
    }

    /**
     * Sets the action to perform when the user selects to create a new file.
     * 
     * @param action the action to set; may be null to clear any previously-set
     * action
     * @see #getNewAction()
     * @see #doNewAction()
     */
    public void setNewAction(Action action) {
        this.newAction = action;
    }

    /**
     * Sets the action to perform when the user selects to open an existing
     * file.
     * 
     * @param action the action to set; may be null to clear any previously-set
     * action
     * @see #getNewAction()
     * @see #doOpenAction()
     */
    public void setOpenAction(Action action) {
        this.openAction = action;
    }

    /**
     * Listener which performs an action in response to the user. All
     * invocations of methods defined in this interface will occur on the event
     * thread of the application.
     */
    public static interface Action {

        /**
         * Performs the action.
         * 
         * @param screen the instance of SVGEditorScreen on which the action was
         * performed
         * @throws NullPointerException if screen==null
         */
        public void doAction(SVGEditorScreen screen);
    }

    /**
     * Menu item which invokes the "create new file" action.
     */
    private class NewMenuItem extends MenuItem {

        /**
         * Creates a new instance of <code>NewMenuItem</code>.
         * 
         * @param ordinal the ordinal for the menu item
         * @param priority the priority for the menu item
         */
        public NewMenuItem(int ordinal, int priority) {
            super(Main.getResourceBundle(), SVGEditorResource.MENU_NEW,
                ordinal, priority);
        }

        /**
         * Invokes {@link SVGEditorScreen#doNewAction()}.
         */
        public void run() {
            SVGEditorScreen.this.doNewAction();
        }
    }

    /**
     * Menu item which invokes the "open existing file" action.
     */
    private class OpenMenuItem extends MenuItem {

        /**
         * Creates a new instance of <code>OpenMenuItem</code>.
         * 
         * @param ordinal the ordinal for the menu item
         * @param priority the priority for the menu item
         */
        public OpenMenuItem(int ordinal, int priority) {
            super(Main.getResourceBundle(), SVGEditorResource.MENU_OPEN,
                ordinal, priority);
        }

        /**
         * Invokes {@link SVGEditorScreen#doOpenAction()}.
         */
        public void run() {
            SVGEditorScreen.this.doOpenAction();
        }
    }
}
