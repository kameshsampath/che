/*******************************************************************************
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.api.debug.shared.dto.action;

import org.eclipse.che.api.debug.shared.model.action.SuspendAction;
import org.eclipse.che.dto.shared.DTO;

/**
 * @author Anatoliy Bazko
 */
@DTO
public interface SuspendActionDto extends ActionDto, SuspendAction {
    TYPE getType();

    void setType(TYPE type);

    SuspendActionDto withType(TYPE type);
}
