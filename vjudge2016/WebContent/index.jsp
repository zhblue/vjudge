<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />  

<!DOCTYPE html>
<html xmlns:wb=“http://open.weibo.com/wb”>
    <head>
        <title>Virtual Judge</title>
        <%@ include file="/header.jsp" %>

        <s:if test='#session.country == "CN"'>
	        <meta property="og:type" content="webpage" />
	        <meta property="og:url" content="acm.hust.edu.cn/vjudge" />
	        <meta property="og:title" content="Virtual Judge" />
	        <meta property="og:description" content="ICPC OnlineJudge JudgeOnline OJ Coding Algorithm 竞赛 算法" />
	        <script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
        </s:if>
    </head>

    <body>
        <s:if test='#session.country != "CN"'>
	        <div id="fb-root"></div>
	        <script>(function(d, s, id) {
	          var js, fjs = d.getElementsByTagName(s)[0];
	          if (d.getElementById(id)) return;
	          js = d.createElement(s); js.id = id;
	          js.src = "//connect.facebook.net/zh_CN/sdk.js#xfbml=1&version=v2.0";
	          fjs.parentNode.insertBefore(js, fjs);
	        }(document, 'script', 'facebook-jssdk'));</script>
        </s:if>

        <s:include value="/top.jsp" />

		<div id="title">
			<div style="text-align: center">Virtual Judge</div>
		
		</div>

    	<div style="width:900px;MARGIN-RIGHT:auto;MARGIN-LEFT:auto;clear:both;">
            <p>
                Virtual Judge is not a real online judge. It can grab problems from other regular online judges and simulate submissions to other online judges.
                It aims to enable holding contests when you don't have the test data.<br /><br />
                Currently, this system supports the following online judges:<br />
            </p>
            
            <div id="ojs">
            <%
            for(judge.remote.RemoteOjInfo oj:judge.action.BaseAction.OJList){
                %>
                <span><a href="<%=oj.urlForIndexDisplay%>" target="_blank"><img src="${contextPath}/<%=oj.faviconUrl%>" /><%=oj.literal%></a></span>
                <%
            }
            %>

            </div>
        
        </div>
        <s:include value="/bottom.jsp" />
    </body>
</html>
