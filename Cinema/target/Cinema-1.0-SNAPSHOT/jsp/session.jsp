<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ftg" %>
<%@ taglib prefix="mtg" uri="mytags" %>

<fmt:bundle basename="i18n">
    <fmt:message key="general.currency.short" var="currency"/>
    <fmt:message key="session.pageTitle" var="pageTitle"/>
    <fmt:message key="session.time" var="time"/>
    <fmt:message key="session.timePrefix" var="timePrefix"/>
    <fmt:message key="session.seatsRemain" var="seatsRemain"/>
    <fmt:message key="session.ticketPrice" var="ticketPrice"/>
    <fmt:message key="session.buyTicket" var="buyTicket"/>
    <fmt:message key="film.duration" var="duration"/>
    <fmt:message key="film.duration.postfix" var="durationPostfix"/>
    <fmt:message key="film.genres" var="genres"/>
    <fmt:message key="film.description" var="description"/>
    <fmt:message key="general.selector.multipleSelectTips" var="selectorTips"/>
    <fmt:message key="film.goToFilmPage" var="filmPage"/>
    <fmt:message key="session.chooseSeat" var="chooseSeat"/>
</fmt:bundle>

<ftg:header pageTitle="${pageTitle}"/>
<ftg:menu userRole="${sessionScope.userRole}"/>

<c:set var="session" value="${requestScope.session}"/>
<c:set var="film" value="${session.film}"/>
<c:set var="room" value="${session.room}"/>
<c:set var="allSeatList" value="${requestScope.allSeatList}"/>
<c:set var="freeSeatList" value="${requestScope.freeSeatList}"/>

<main class="container" data-new-gr-c-s-check-loaded="14.1062.0" data-gr-ext-installed="">
    <div class="container-fluid">
        <h1>${pageTitle}</h1>
        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-3">
                        <img class="poster-img card-img" src="${film.posterUrl}">
                    </div>
                    <div class="col-md-9">
                        <div>
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
                                    Room ${room.roomName}
                                </li>
                            </ul>
                            <h4 class="card-text">${ticketPrice}: ${session.ticketPrice} ${currency}</h4>
                        </div>
                        <div>
                            <h5>${chooseSeat}:</h5>
                            <form name="seatIds" method="post" action="loadPaying">
                                <input type="hidden" name="command" value="buyTicketPage">
                                <input type="hidden" name="sessionId" value="${session.id}">
                                <input type="hidden" name="roomId" value="${room.id}">
                                <div class="row under-filter">
                                    <div class="col-md-8 under-filter-block px-3">

                                        <c:forEach var="seat" items="${allSeatList}" varStatus="upSeatCounter">
                                            <c:set var="isFree" value="false"/>
                                            <c:forEach var="freeSeat" items="${freeSeatList}">
                                                <c:if test="${freeSeat.id == seat.id}">
                                                    <c:set var="isFree" value="true"/>
                                                </c:if>
                                            </c:forEach>


                                            <c:if test="${seat.placeNumber == 1 && upSeatCounter.index > 0}">
                                                <div class="w-100"></div>
                                            </c:if>

                                            <c:choose>
                                                <c:when test="${isFree == false}">
                                                    <label>
                                                        <input type="checkbox" name="seatIds" class="seatsElement"
                                                               value="${seat.id}" disabled>

                                                        <img class="seatsImg"
                                                             src="<c:url value="/images/seat_gray.png"/>">
                                                    </label>
                                                </c:when>

                                                <c:otherwise>
                                                    <label>
                                                        <input type="checkbox" name="seatIds" class="seatsElement"
                                                               value="${seat.id}">
                                                        <img class="seatsImg"
                                                             src="<c:url value="/images/seat_blue.png"/>">
                                                    </label>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                    </div>
                                    <c:if test="${userRole == 'USER'}">
                                        <div class="col-md-3 under-filter-block px-3">
                                            <button type="submit" class="btn btn-lg btn-block btn-primary">
                                                ${buyTicket}
                                            </button>
                                        </div>
                                    </c:if>
                                    <c:if test="${userRole == null || userRole == 'GUEST'}">
                                        <p>Login to buy tickets</p>
                                    </c:if>


                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="w-100"></div>

                    <div class="col-md-12 py-2">
                        <c:if test="${film.description != null && film.description != ''}">
                            <h5>${description}:</h5>
                            <p>${film.description}</p>
                        </c:if>
                    </div>

                </div>
            </div>
        </div>
    </div>
</main>
<ftg:footer/>
