/*
 * Util.java
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

import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.CodeModuleManager;

/**
 * Utility functions.
 */
public class Util {

    /**
     * Private constructor to prevent instantiation.
     */
    private Util() {
    }

    /**
     * Returns the ApplicationDescriptor for the given class with no args.
     * 
     * @param clazz the class whose ApplicationDescriptor to return; may be
     * null, in which case this method behaves as if no ApplicationDescriptor
     * was found
     * @return the ApplicationDescriptor for the given class with no args;
     * returns null if not found
     * @throws SecurityException if permission to access the code module
     * management functionality is denied by the OS
     */
    public static ApplicationDescriptor getApplicationDescriptorForClass(
            Class clazz) {
        if (clazz == null) {
            return null; // as documented
        }

        // determine the handle of the eldest sibling module
        final int handle = CodeModuleManager.getModuleHandleForClass(clazz);
        final String moduleName = CodeModuleManager.getModuleName(handle);
        final int index = moduleName.lastIndexOf('-');
        final String eldestSiblingModuleName;
        if (index < 0) {
            eldestSiblingModuleName = moduleName;
        } else {
            eldestSiblingModuleName = moduleName.substring(0, index);
        }
        final int eldestSiblingModuleHandle =
            CodeModuleManager.getModuleHandle(eldestSiblingModuleName);

        // search the module's ApplicationDescriptors for the no-args one
        final ApplicationDescriptor[] descriptors =
            CodeModuleManager
                .getApplicationDescriptors(eldestSiblingModuleHandle);
        ApplicationDescriptor descriptor = null;
        if (descriptors != null) {
            for (int i = descriptors.length - 1; i >= 0; i--) {
                final ApplicationDescriptor curDescriptor = descriptors[i];
                if (curDescriptor != null) {
                    final String[] args = curDescriptor.getArgs();
                    if (args == null || args.length == 0) {
                        descriptor = curDescriptor;
                        break;
                    }
                }
            }
        }

        // return the found descriptor, or null if not found, as documented
        return descriptor;
    }
}
