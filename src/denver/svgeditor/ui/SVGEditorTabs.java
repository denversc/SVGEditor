/*
 * SVGEditorTabs.java
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

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.pane.HorizontalTabController;
import net.rim.device.api.ui.component.pane.HorizontalTabTitleView;
import net.rim.device.api.ui.component.pane.Pane;
import net.rim.device.api.ui.component.pane.PaneManagerController;
import net.rim.device.api.ui.component.pane.PaneManagerModel;
import net.rim.device.api.ui.component.pane.PaneManagerView;
import net.rim.device.api.ui.component.pane.PaneView;
import net.rim.device.api.ui.component.pane.TitleView;

/**
 * Manages the tabs of the UI widgets for editing SVG images.
 */
public class SVGEditorTabs {

    private final PaneManagerView view;

    /**
     * Creates a new instance of <code>SVGEditorTabs</code>.
     */
    public SVGEditorTabs() {
        final PaneManagerModel model = new PaneManagerModel();
        model.enableLooping(true);
        model.addPane(new Pane(new LabelField("Title 0", Field.FOCUSABLE),
            new LabelField("Content 0", Field.FOCUSABLE)));
        model.addPane(new Pane(new LabelField("Title 1", Field.FOCUSABLE),
            new LabelField("Content 1", Field.FOCUSABLE)));
        model.addPane(new Pane(new LabelField("Title 2", Field.FOCUSABLE),
            new LabelField("Content 2", Field.FOCUSABLE)));
        model.setCurrentlySelectedIndex(0);

        final TitleView titleView = new HorizontalTabTitleView(Field.FOCUSABLE);
        titleView.setModel(model);

        final PaneManagerController controller = new HorizontalTabController();

        final PaneView paneView = new PaneView(Field.FOCUSABLE);
        paneView.setModel(model);

        final PaneManagerView view = new PaneManagerView(Field.FOCUSABLE, titleView, paneView);
        view.setModel(model);
        model.setView(view);

        controller.setModel(model);
        controller.setView(view);
        model.setController(controller);
        view.setController(controller);
        
        this.view = view;
    }
    
    /**
     * Returns the field that displays the tabs.
     * @return the field that displays the tabs; never returns null
     */
    public Field getField() {
        return this.view;
    }
}
