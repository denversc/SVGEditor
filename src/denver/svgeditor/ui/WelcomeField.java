/*
 * WelcomeField.java
 * By: Denver Coneybeare <denver.coneybeare@gmail.com>
 * Nov 4, 2011
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

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.util.StringProvider;

/**
 * A manager which displays two icons: one for "new" and one for "open".
 */
public class WelcomeField extends Manager {

    private final Field newField;
    private final Field openField;

    public WelcomeField() {
        super(NO_VERTICAL_SCROLL | NO_HORIZONTAL_SCROLL);
        this.newField =
            this.getIconField("icon_new.png", SVGEditorResource.MENU_NEW);
        this.openField =
            this.getIconField("icon_open.png", SVGEditorResource.MENU_OPEN);
        this.add(this.newField);
        this.add(this.openField);

        final Background background =
            BackgroundFactory.createLinearGradientBackground(Color.DARKVIOLET,
                Color.DARKVIOLET, Color.DIMGRAY, Color.DIMGRAY);
        this.setBackground(background);
    }

    /**
     * Loads the icon for a field and creates a field for it.
     * 
     * @param iconResourcePath the path of the icon resource
     * @param labelResourceId the ID in the resource bundle of the string label
     * for the field if the icon is not available
     * @return a newly-created Field to use for the icon; never returns null
     * @throws NullPointerException if iconResourcePath==null
     */
    private Field getIconField(String iconResourcePath, int labelResourceId) {
        if (iconResourcePath == null) {
            throw new NullPointerException("iconResourcePath==null");
        }

        final StringProvider label = Main.getString(labelResourceId);
        final EncodedImage image = Main.loadImageResource(iconResourcePath);

        Field field;
        if (image == null) {
            field =
                new ButtonField(label.toString(), ButtonField.CONSUME_CLICK
                    | ButtonField.NEVER_DIRTY);
        } else {
            final Bitmap bitmap = image.getBitmap();
            field = new BitmapField(bitmap, Field.FOCUSABLE);
        }

        return field;
    }

    /**
     * Lays out this screen's fields.
     */
    protected void sublayout(int width, int height) {
        if (width > height) {
            this.sublayoutLandscape(width, height);
        } else {
            this.sublayoutProtrait(width, height);
        }
        this.setExtent(width, height);
    }

    /**
     * Lays out this manager's fields in the landscape orientation. This method
     * is called from sublayout() if it determines that a landscape orientation
     * should be used.
     * 
     * @param width the width in which to lay out the fields
     * @param height the height in which to lay out the fields
     * @see #sublayout(int, int)
     */
    private void sublayoutLandscape(final int width, final int height) {
        final int newFieldPreferredWidth = this.newField.getPreferredWidth();
        final int newFieldPreferredHeight = this.newField.getPreferredHeight();
        final int newFieldWidth = Math.max(0, newFieldPreferredWidth);
        final int newFieldHeight = Math.max(0, newFieldPreferredHeight);

        final int openFieldPreferredWidth = this.openField.getPreferredWidth();
        final int openFieldPreferredHeight =
            this.openField.getPreferredHeight();
        final int openFieldWidth = Math.max(0, openFieldPreferredWidth);
        final int openFieldHeight = Math.max(0, openFieldPreferredHeight);

        final int fieldWidths = newFieldWidth + openFieldWidth + 10;
        final int startX = (width - fieldWidths) / 2;

        final int newFieldX = Math.max(0, startX);
        final int newFieldY = Math.max(0, (height - newFieldHeight) / 2);
        this.setPositionChild(this.newField, newFieldX, newFieldY);
        this.layoutChild(this.newField, newFieldWidth, newFieldHeight);

        final int openFieldX = Math.max(0, startX + newFieldWidth + 10);
        final int openFieldY = Math.max(0, (height - openFieldHeight) / 2);
        this.setPositionChild(this.openField, openFieldX, openFieldY);
        this.layoutChild(this.openField, openFieldWidth, openFieldHeight);
    }

    /**
     * Lays out this manager's fields in the portrait orientation. This method
     * is called from sublayout() if it determines that a portrait orientation
     * should be used.
     * 
     * @param width the width in which to lay out the fields
     * @param height the height in which to lay out the fields
     * @see #sublayout(int, int)
     */
    private void sublayoutProtrait(final int width, final int height) {
        final int newFieldPreferredWidth = this.newField.getPreferredWidth();
        final int newFieldPreferredHeight = this.newField.getPreferredHeight();
        final int newFieldWidth = Math.max(0, newFieldPreferredWidth);
        final int newFieldHeight = Math.max(0, newFieldPreferredHeight);

        final int openFieldPreferredWidth = this.openField.getPreferredWidth();
        final int openFieldPreferredHeight =
            this.openField.getPreferredHeight();
        final int openFieldWidth = Math.max(0, openFieldPreferredWidth);
        final int openFieldHeight = Math.max(0, openFieldPreferredHeight);

        final int fieldHeights = newFieldHeight + openFieldHeight + 10;
        final int startY = (height - fieldHeights) / 2;

        final int newFieldX = Math.max(0, (width - newFieldWidth) / 2);
        final int newFieldY = Math.max(0, startY);
        this.setPositionChild(this.newField, newFieldX, newFieldY);
        this.layoutChild(this.newField, newFieldWidth, newFieldHeight);

        final int openFieldX = Math.max(0, (width - openFieldWidth) / 2);
        final int openFieldY = Math.max(0, startY + newFieldHeight + 10);
        this.setPositionChild(this.openField, openFieldX, openFieldY);
        this.layoutChild(this.openField, openFieldWidth, openFieldHeight);
    }

}
