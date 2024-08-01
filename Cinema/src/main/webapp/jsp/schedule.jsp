<%--
  Created by IntelliJ IDEA.
  User: egorf
  Date: 18.05.2022
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ftg" %>
<%@ taglib prefix="mtg" uri="mytags" %>

<fmt:bundle basename="i18n">
    <fmt:message key="general.currency.short" var="currency"/>
    <fmt:message key="session.time" var="time"/>
    <fmt:message key="session.timePrefix" var="timePrefix"/>
    <fmt:message key="session.seatsRemain" var="seatsRemain"/>
    <fmt:message key="session.ticketPrice" var="ticketPrice"/>
    <fmt:message key="session.buyTicket" var="buyTicket"/>
    <fmt:message key="film.duration" var="duration"/>
    <fmt:message key="film.duration.postfix" var="durationPostfix"/>
    <fmt:message key="film.genres" var="genres"/>
    <fmt:message key="general.selector.multipleSelectTips" var="selectorTips"/>
    <fmt:message key="film.goToFilmPage" var="filmPage"/>
    <fmt:message key="admin.sessionsSetting.sessionInfo" var="sessionInfo"/>
    <fmt:message key="pagination.selectPageSize.label" var="selectSizeLabel"/>
    <fmt:message key="pagination.selectPageSize.option" var="selectSizeOption"/>
    <fmt:message key="pagination.prev" var="prev"/>
    <fmt:message key="pagination.next" var="next"/>
</fmt:bundle>

<fmt:bundle basename="i18n" prefix="schedule.">
    <fmt:message key="pageTitle" var="pageTitle"/>
    <fmt:message key="title" var="title"/>
    <fmt:message key="sortAndFilter" var="sortAndFilter"/>
    <fmt:message key="listTitle" var="listTitle"/>
    <fmt:message key="showSorter" var="showSorter"/>
    <fmt:message key="showSorterAll" var="showSorterAll"/>
    <fmt:message key="showSorterOnlyAv" var="showSorterOnlyAv"/>
    <fmt:message key="sortBy" var="sortBy"/>
    <fmt:message key="sortBy.datetime" var="sortDatetime"/>
    <fmt:message key="sortBy.filmName" var="sortFilmName"/>
    <fmt:message key="sortBy.seatsRemain" var="sortSeatsRemain"/>
    <fmt:message key="sortMethod" var="sortMethod"/>
    <fmt:message key="sortMethod.asc" var="asc"/>
    <fmt:message key="sortMethod.desc" var="desc"/>
    <fmt:message key="submitBtn" var="submitBtn"/>
    <fmt:message key="clearBtn" var="clearBtn"/>
</fmt:bundle>

<ftg:header pageTitle="${pageTitle}"/>
<c:set value="${sessionScope.userRole}" var="userRole"/>
<ftg:menu userRole="${userRole}"/>


<main class="container" data-new-gr-c-s-check-loaded="14.1062.0" data-gr-ext-installed="">
    <h1>${title}</h1>
    <div class="filter-sorter">
        <div class="row">
            <%--  Filter  --%>
            <aside class="col-md-8">
                <header class="card-header-custom">
                    <h5 class="card-custom-title">${sortAndFilter}</h5>
                </header>
                <form name="filtration" method="get" action="schedule">
                    <input type="hidden" name="command" value="schedulePage">
                    <div class="card-group">
                        <article class="card card-filter">
                            <div class="filter-content">
                                <div class="card-body">
                                    <h5 class="card-title">${showSorter}</h5>
                                    <label class="form-check">
                                        <input class="form-check-input" type="radio"
                                               name="show" value="all"
                                               <c:if test="${param.show == null || param.show == 'all'}">checked</c:if>>
                                        <span class="form-check-label">${showSorterAll}</span>
                                    </label>
                                    <label class="form-check">
                                        <input class="form-check-input" type="radio" name="show"
                                               value="onlyAvailable"
                                               <c:if test="${param.show == 'onlyAvailable'}">checked</c:if>>
                                        <span class="form-check-label">${showSorterOnlyAv}</span>
                                    </label>
                                </div> <!-- card-body.// -->
                            </div>
                        </article> <!-- card-group-item.// -->

                        <article class="card card-sorter">
                            <div class="filter-content">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <h5 class="card-title">${sortBy}</h5>
                                            <div class="form-group">
                                                <select name="sortBy" id="sortBy" class="form-control"
                                                        style="min-width: 150px;" required>
                                                    <option value="dateTime"
                                                            <c:if test="${param.sortBy == 'dateTime'}">selected</c:if>>
                                                        ${sortDatetime}</option>
                                                    <option value="filmName"
                                                            <c:if test="${param.sortBy == 'filmName'}">selected</c:if>>
                                                        ${sortFilmName}</option>
                                                    <option value="seatsRemain"
                                                            <c:if test="${param.sortBy == 'seatsRemain'}">selected</c:if>>
                                                        ${sortSeatsRemain}</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <h5 class="card-title">${sortMethod}</h5>
                                            <div class="form-group">
                                                <select name="sortMethod" id="sortMethod" class="form-control"
                                                        style="min-width: 150px;"
                                                        required>
                                                    <option value="asc"
                                                            <c:if test="${param.sortMethod == 'asc'}">selected</c:if>>${asc}</option>
                                                    <option value="desc"
                                                            <c:if test="${param.sortMethod == 'desc'}">selected</c:if>>${desc}</option>
                                                </select>
                                            </div>
                                        </div>
                                        <%--<div class="w-100"></div>--%>

                                    </div>
                                </div> <!-- card-body.// -->
                            </div>
                        </article>
                        <div class="w-100"></div>
                        <article class="card card-sorter" style="border-top: 0;">
                            <div class="row">
                                <div class="col-6 py-2 filter-helper">
                                    <label for="amountSelector" class="py-1">${selectSizeLabel}: </label>
                                    <select id="amountSelector" class="form-control form-select" name="size">
                                        <option value>${selectSizeLabel}</option>
                                        <option value="2" <c:if test="${param.size == '2'}">selected</c:if>>
                                            2
                                        </option>
                                        <option value="4" <c:if test="${param.size == '4'}">selected</c:if>>
                                            4
                                        </option>
                                        <option value="10" <c:if test="${param.size == '10'}">selected</c:if>>
                                            10
                                        </option>
                                        <option value="20" <c:if test="${param.size == '20'}">selected</c:if>>
                                            20
                                        </option>
                                    </select>
                                </div>

                                <div class="col-3 py-2 filter-helper">
                                    <button type="submit" class="btn btn-primary w-25 btn-sorter">${submitBtn}
                                    </button>
                                </div>
                                <div class="col-3 py-2 filter-helper">
                                    <a type="button" href="${pageContext.request.contextPath}/main?command=schedulePage"
                                       class="btn btn-outline-secondary w-25 btn-sorter">${clearBtn}
                                    </a>
                                </div>
                            </div>
                        </article>

                    </div> <!-- card.// -->
                </form>
            </aside> <!-- col.// -->
        </div>

        <%-- SESSIONS --%>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="film-posts py-4">
                        <table>
                            <thead>
                            </thead>
                            <tbody>
                            <c:forEach var="session" items="${requestScope.sessionList}">
                                <c:set var="film" value="${session.getFilm()}"/>
                                <c:set var="room" value="${session.getRoom()}"/>
                                <tr>
                                    <td>
                                        <div class="p-4 film-post card w-100">
                                            <div class="row card-body">
                                                <div class="col-md-2">
                                                    <img class="poster-img card-img" src="${film.posterUrl}">
                                                </div>
                                                <div class="col-md-7">
                                                    <h2 class="card-title">${film.name}</h2>
                                                    <ul class="list-unstyled mt-3 mb-4">
                                                        <li class="card-text">${genres}:
                                                            <mtg:filmGenresList film="${film}"/>
                                                        </li>
                                                        <li class="card-text">
                                                                ${duration}: ${film.getDurationInMinutes()} ${durationPostfix}
                                                        </li>
                                                        <li class="card-text">
                                                                ${time}: ${session.date} ${timePrefix} ${session.time}
                                                        </li>
                                                        <li class="card-text">
                                                                Ph�ng : ${room.roomName}
                                                        </li>
                                                        <li class="card-text">
                                                                ${seatsRemain}: ${session.seatsAmount}
                                                        </li>
                                                    </ul>
                                                    <h5 class="card-text">${ticketPrice}: ${session.ticketPrice} ${currency}</h5>
                                                </div>

                                                <div class="col-md-3">
                                                    <div class="vertical-buttons-4">
                                                        <form name="film" method="get" action="/detailfilm">
                                                            <input type="hidden" name="command"
                                                                   value="filmPage">
                                                            <input type="hidden" name="filmId"
                                                                   value="${film.id}">
                                                            <button type="submit"
                                                                    class="btn btn-lg btn-block btn-primary my-2">
                                                                    ${filmPage}
                                                            </button>
                                                        </form>
                                                        <c:choose>
                                                            <c:when test="${userRole == 'ADMIN'}">
                                                                <form name="session" method="get" action="sessionInfor">
                                                                    <input type="hidden" name="command"
                                                                           value="sessionInfoPage">
                                                                    <input type="hidden" name="sessionId"
                                                                           value="${session.id}">
                                                                    <button type="submit"
                                                                            class="btn btn-lg btn-block btn-primary my-2">
                                                                            ${sessionInfo}
                                                                    </button>
                                                                </form>
                                                            </c:when>

                                                            <c:otherwise>
                                                                <form name="session" method="get" action="/sessionDetail">
                                                                    <input type="hidden" name="command"
                                                                           value="sessionPage">
                                                                    <input type="hidden" name="sessionId"
                                                                           value="${session.id}">
                                                                    <button type="submit"
                                                                            class="btn btn-lg btn-block btn-primary">
                                                                            ${buyTicket}
                                                                    </button>
                                                                </form>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <mtg:pagination request="${pageContext.request}" totalPages="${requestScope.totalPages}"
                                        prev="${prev}" next="${next}"/>

                    </div>
                </div>
            </div>
        </div>
</main>
<ftg:footer/>