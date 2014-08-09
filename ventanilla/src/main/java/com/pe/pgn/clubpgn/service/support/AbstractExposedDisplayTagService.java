/*
 * Copyright 2007, Xebia, the Netherlands
 *
 * info@xebia.com
 */

package com.pe.pgn.clubpgn.service.support;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.pe.pgn.clubpgn.util.DisplayTagReplacementUtil;

/**
 * Generic Convenience service that gives you the ability to make your &lt;a
 * href="http://displaytag.sourceforge.net"&gt;DisplayTag&lt;/a&gt; <br />
 * Ajax enabled using &lt;a href="http://getahead.org/dwr"&gt;DWR&lt;/a&gt;<br />
 * <br />
 * The instance extending this class is populated by Dependency Injection.
 * <br />
 * The properties tableId and viewFragement should be injected.<br />
 * @author Mischa Dasberg, mdasberg@xebia.com
 * @param <T>
 */

public abstract class AbstractExposedDisplayTagService<T> implements InitializingBean {
	
	public static final String DEFAULT_MAXIMUM_RESULTS = "30";
	public String tableId;
	public String viewFragment;
	public String displayTagPage;
	public String displayTagSortOrder;
	public String displayTagOrderBy;

	/** {@inheritDoc} */
	public void afterPropertiesSet() throws Exception {
		// We need the tableId because we need to know how displaytag encoded the parameter names.
		Assert.notNull(tableId);
		// We need the viewFragment we want to return.
		Assert.notNull(viewFragment);
		displayTagPage = new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		displayTagSortOrder = new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER);
		displayTagOrderBy = new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT);
	}

	/**
	 * Returns an html fragment with Ajax enabled links.<br />
	 * DisplayTag generates html, but because this html is by default not Ajax enabled, <br />
	 * all links should be replaced so that future clicks on these links will be Ajax enabled.
	 * @param criteria The criteria, such as current page, sortOrder, orderBy etc.
	 * @return The updated html for the to be replaced div.
	 */
	public String findAllObjects(String criteria, String[] filters) {
		
		WebContext wctx = WebContextFactory.get();
		HttpServletRequest request = wctx.getHttpServletRequest();

		// split results and set values;
		int maxResults = Integer.parseInt(getCriterionValue(criteria, "maxResults", DEFAULT_MAXIMUM_RESULTS));
		int page = Integer.parseInt(getCriterionValue(criteria, displayTagPage, "1"));
		boolean ascending = Integer.parseInt(getCriterionValue(criteria, displayTagSortOrder, "1")) == 1 ? true : false;
		String orderBy = getCriterionValue(criteria, displayTagOrderBy, "id");
		int firstResult = (page - 1) * maxResults;

		int numberOfObjects = getNumberOfObjects(filters);

		// set the episodes on the request so dwr can reload the jsp part.
		request.setAttribute(getObjectsName(), getObjects(firstResult, maxResults, orderBy, ascending, filters));
		request.setAttribute(getNumberOfObjectsName(), numberOfObjects);
		try {
			String html = wctx.forwardToString(viewFragment);
			html = DisplayTagReplacementUtil.updatePagingHtml(html, page, maxResults, numberOfObjects, displayTagPage);
			html = DisplayTagReplacementUtil.updateSortOrderHtml(html, ascending, displayTagSortOrder);
			html = DisplayTagReplacementUtil.updateHtmlLinks(html);
			return html;
		} catch (ServletException e) {
			return "";
		} catch (IOException e) {
			return "";
		}
	}

	/**
	 * Returns the criterion value for the given criterion name.
	 * @param criteria The String containing criterion.
	 * @param criterionName The name of the criterion we want the value of.
	 * @param defaultValue The default value incase the criterion is not found.
	 * @return criterionValue the value of the criterion
	 */

	public String getCriterionValue(String criteria, String criterionName, String defaultValue) {
		for (String criterion : criteria.split("&")) {
			String value = criterion.substring(criterion.indexOf("=") + 1, criterion.length());
			if (criterion.contains(criterionName)) {
				return value;
			}
		}
		return defaultValue;
	}
	// ==========================================================================
	// Dependencies
	// ==========================================================================
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public void setViewFragment(String viewFragment) {
		this.viewFragment = viewFragment;
	}

	/**
	 * Returns a {@link List} of &lt;T&gt;s.<br />
	 * We only need to retrieve a subset because we will not be displaying more than the maxResults.
	 * @param firstResult The first result that should be fetched.
	 * @param maxResults The maximum number of results that should be fetched.
	 * @param orderBy The attribute we order by.
	 * @param ascending Indicates if we are ordering ascending or descending.
	 * @return {@link List} containing the Ts matching the criteria.
	 */
	public abstract List<T> getObjects(int firstResult, int maxResults, String orderBy, 
			boolean ascending, String[] filters);

	/**
	 * Returns the name of the attribute we set the objects for.
	 * @return objects The name of the attribute we set the objects for.
	 */
	public abstract String getObjectsName();

	/**
	 * Returns the number of &lt;T&gt;s that match the criteria. <br />
	 * We need the number of &lt;T&gt;s for the size attribute of the displaytag.
	 * @return numberOfObject The number of objects.
	 */
	public abstract int getNumberOfObjects(String[] filters);

	/**
	 * Returns the name of the attribute we set the numberOfObjects for.
	 * @return numberOfObjectsName The name of the attribute we set the numberOfObjects for.
	 */
	public abstract String getNumberOfObjectsName();

}
