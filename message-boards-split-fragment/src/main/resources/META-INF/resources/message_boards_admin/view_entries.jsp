<%--
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
--%>

<%@ include file="/message_boards/init.jsp" %>

<%
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));

SearchContainer entriesSearchContainer = (SearchContainer)request.getAttribute("view.jsp-entriesSearchContainer");

long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

boolean showBreadcrumb = ParamUtil.getBoolean(request, "showBreadcrumb", true);

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

if (groupThreadsUserId > 0) {
	portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));
%>

<div class="container-fluid-1280 view-entries-container">
	<c:if test="<%= category != null %>">

		<%
		long parentCategoryId = category.getParentCategoryId();
		String parentCategoryName = LanguageUtil.get(request, "message-boards-home");

		if (!category.isRoot()) {
			MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

			parentCategoryId = parentCategory.getCategoryId();
			parentCategoryName = parentCategory.getName();
		}
		%>

		<portlet:renderURL var="backURL">
			<c:choose>
				<c:when test="<%= parentCategoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
				</c:when>
				<c:otherwise>
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view_category" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" />
				</c:otherwise>
			</c:choose>
		</portlet:renderURL>

		<%
		if (portletTitleBasedNavigation) {
			portletDisplay.setShowBackIcon(true);
			portletDisplay.setURLBack(backURL.toString());

			renderResponse.setTitle(category.getName());
		}
		%>

	</c:if>

	<c:if test="<%= showBreadcrumb %>">

		<%
		MBBreadcrumbUtil.addPortletBreadcrumbEntries(categoryId, request, renderResponse);
		%>

		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>
	</c:if>

	<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />

		<%
		request.setAttribute("view_entries_search_container.jsp-searchContainer", entriesSearchContainer);
		%>

		<liferay-util:include page='<%= "/message_boards_admin/view_entries_search_container.jsp" %>' servletContext="<%= application %>" />
	</aui:form>
</div>