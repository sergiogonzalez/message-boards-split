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

import com.liferay.message.boards.display.context.MBListDisplayContext;
import com.liferay.message.boards.kernel.service.MBCategoryServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.UUID;

import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class MBSplitListDisplayContext implements MBListDisplayContext {

	public MBSplitListDisplayContext(
		MBListDisplayContext parentMBListDisplayContext,
		HttpServletRequest request, long categoryId) {

		_parentMBListDisplayContext = parentMBListDisplayContext;
		_request = request;
		_categoryId = categoryId;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	@Override
	public boolean isShowMyPosts() {
		return _parentMBListDisplayContext.isShowMyPosts();
	}

	@Override
	public boolean isShowRecentPosts() {
		return _parentMBListDisplayContext.isShowRecentPosts();
	}

	@Override
	public boolean isShowSearch() {
		return _parentMBListDisplayContext.isShowRecentPosts();
	}

	@Override
	public void populateResultsAndTotal(SearchContainer searchContainer)
		throws PortalException {

		PortletConfig portletConfig = (PortletConfig)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		boolean portletTitleBasedNavigation = GetterUtil.getBoolean(
			portletConfig.getInitParameter("portlet-title-based-navigation"));

		if (isShowSearch() || isShowMyPosts() || isShowRecentPosts() ||
			portletTitleBasedNavigation) {

			_parentMBListDisplayContext.populateResultsAndTotal(
				searchContainer);

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		searchContainer.setTotal(
			MBCategoryServiceUtil.getCategoriesCount(
				themeDisplay.getScopeGroupId(), _categoryId,
				WorkflowConstants.STATUS_APPROVED));
		searchContainer.setResults(
			MBCategoryServiceUtil.getCategories(
				themeDisplay.getScopeGroupId(), _categoryId,
				WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(),
				searchContainer.getEnd()));
	}

	private static final UUID _UUID = UUID.fromString(
		"03f04053-99c2-411d-9a5a-08625de72e6b");

	private final long _categoryId;
	private final MBListDisplayContext _parentMBListDisplayContext;
	private final HttpServletRequest _request;

}