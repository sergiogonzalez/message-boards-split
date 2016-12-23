/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.message.boards.split.web.internal.display.context;

import com.liferay.message.boards.display.context.MBDisplayContextFactory;
import com.liferay.message.boards.display.context.MBHomeDisplayContext;
import com.liferay.message.boards.display.context.MBListDisplayContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(service = MBDisplayContextFactory.class)
public class MBSplitDisplayContextFactory implements MBDisplayContextFactory {

	@Override
	public MBHomeDisplayContext getMBHomeDisplayContext(
		MBHomeDisplayContext parentMBHomeDisplayContext,
		HttpServletRequest request, HttpServletResponse response) {

		return parentMBHomeDisplayContext;
	}

	@Override
	public MBListDisplayContext getMBListDisplayContext(
		MBListDisplayContext parentMBListDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		long categoryId) {

		return new MBSplitListDisplayContext(
			parentMBListDisplayContext, request, categoryId);
	}

}