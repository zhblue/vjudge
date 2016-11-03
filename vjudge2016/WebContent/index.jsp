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
			<s:if test='#session.country == "CN"'>
				<div style="float: right; width: 450px; padding-top: 10px;">
					<wb:like appkey="cAvMG" type="text">test</wb:like>
                    <wb:follow-button uid="5340553087" type="red_3" width="100%" height="24" ></wb:follow-button>
				</div>
			</s:if>
			<s:else>
				<div style="float: right; padding-top: 10px;">
					<div class="fb-like"></div>
				</div>
			</s:else>
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
            <div style="line-height:20px;padding-top:5px;clear:both;">
                <p>What's new:</p>
                <ul>
                    <li>2014-10-15  UI update. Please <a href="mailto:is.un@qq.com">contact me</a> if you find any bugs. Thanks very much!</li>
                    <li style="color:red;">
                        2014-09-20  Currently Virtual Judge has two access address:<br />
                        <ul>
                            <li><a href="http://acm.hust.edu.cn/vjudge" target="_blank">http://acm.hust.edu.cn/vjudge</a> : Since it was redirected to <a href="http://vjudge.net" target="_blank">vjudge.net</a> permanently incorrectly, you need clear your browser cache to use it. Recommended for Chinese visitors. </li>
                            <li><a href="http://vjudge.net" target="_blank">http://vjudge.net</a> : Reverse proxy hosted overseas. Recommended for visitors who can't use the above entrance.</li>
                        </ul>
                    </li>
                    <li>2010-10-07  Refer to <a href="https://github.com/trcnkq/virtual-judge/commits/master">this page</a> for recent change of Virtual Judge.</li>
                </ul>
            </div>
        </div>
        <s:include value="/bottom.jsp" />
    </body>
</html>
