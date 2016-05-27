<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${pageID eq 'payment'}">
<iframe src="https://px.ozonemedia.com/travel?px_id=000031&type=3&adv_id=ADV000026&siteID=http://www.yatra.com&section=5&flight_type=${searchCriteria.scope}&origin=${fn:substring(searchCriteria.origin, 1, 4)}&destination=${fn:substring(searchCriteria.destination, 1, 4)}&depart_date=${departDate}&return_date=${returnDate}&depart_time=${departTime}&return_time=${returnTime}&ADT=${searchCriteria.ADT}&CHD=${searchCriteria.CHD}&INF=${searchCriteria.INF}&class=${searchCriteria['class']}&value=${PRICE}&currency=INR&merchant=${airlineName}" scrolling="no" width="1" height="1" marginheight="0" marginwidth="0" frameborder="0" ></iframe>

<script type="text/javascript">
(function() {
    try {
        var viz = document.createElement("script");
        viz.type = "text/javascript";
        viz.async = true;
        viz.src = ("https:" == document.location.protocol ?"https://ssl.vizury.com" : "http://www.vizury.com")+ "/analyze/pixel.php?account_id=VIZVRM135";

        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(viz, s);
        viz.onload = function() {
            try {
                pixel.parse();
            } catch (i) {
            }
        };
        viz.onreadystatechange = function() {
            if (viz.readyState == "complete" || viz.readyState == "loaded") {
                try {
                    pixel.parse();
                } catch (i) {
                }
            }
        };
    } catch (i) {
    }
})();
</script>
</c:if>

<c:if test="${pageID eq 'search'}">
	<yatra:affiliateTag affiliateurl='${trackerBaseUrl}/affiliateLogger?version=VIZURY_OTHER&PAGE_TYPE=SEARCH&PRODUCT_TYPE=AIR&logger=TRUE' paramMap='${affiliationParamMap}' />					      		

<script type="text/javascript">

adroll_adv_id = "HD5VXTTKQRD3FJVNLVTWMC";
adroll_pix_id = "W4PITGWG4ZCCXKYLAUMQPJ";
(function () {
var oldonload = window.onload;
window.onload = function(){
   __adroll_loaded=true;
   var scr = document.createElement("script");
   var host = (("https:" == document.location.protocol) ? "https://s.adroll.com" : "http://a.adroll.com");
   scr.setAttribute('async', 'true');
   scr.type = "text/javascript";
   scr.src = host + "/j/roundtrip.js";
   ((document.getElementsByTagName('head') || [null])[0] ||
    document.getElementsByTagName('script')[0].parentNode).appendChild(scr);
   if(oldonload){oldonload()}};
}());
</script>

<iframe src="http://px.ozonemedia.com/travel?px_id=000031&type=3&adv_id=ADV000026&siteID=http://www.yatra.com&section=3&search=Flights&flight_type=${tenantScope}&origin=${affiliationParamMap.DEPART_CITY_1}&destination=${affiliationParamMap.DEST_CITY_1}&depart_date=${affiliationParamMap.DEPART_DATE}&return_date=${affiliationParamMap.RETURN_DATE}&ADT=${affiliationParamMap.ADT}&CHD=${affiliationParamMap.CHD}&INF=${affiliationParamMap.INF}&class=${affiliationParamMap.CLASS_1}" scrolling="no" width="1" height="1" marginheight="0" marginwidth="0" frameborder="0" ></iframe>

<script type = "text/javascript"> 
var ae_parms_kv ={
  section: 'Air',  
  triptype: '${affiliationParamMap.TYPE}', 
  org_city_1: '${affiliationParamMap.DEPART_CITY_1}',
  dest_city_1: '${affiliationParamMap.DEST_CITY_1}',
  flt_dptr_date_1: '${affiliationParamMap.DEPART_DATE}',
  class_1: '${affiliationParamMap.CLASS_1}',
  org_city_2: '${affiliationParamMap.DEPART_CITY_2}',
  dest_city_2: '${affiliationParamMap.DEST_CITY_2}',
  flt_dptr_date_2: '${affiliationParamMap.DEPARTURE_DATE2}',
  class_2: '${affiliationParamMap.CLASS_2}',
  adult: '${affiliationParamMap.ADT}',
  child: '${affiliationParamMap.CHD}',
  infant: '${affiliationParamMap.INF}',
  depth: '1' 
};
(function () {var el_adv_id = "e03cd4d40f2923f7bc04b247f944bbf7"; var oldonload = window.onload;window.onload = function(){__ele_loaded=true;var scr = document.createElement("script");var host = (("https:" == document.location.protocol) ? "https://d313lzv9559yp9.cloudfront.net" : "http://cache.adelement.com");scr.setAttribute("async", "true");scr.type = "text/javascript";scr.src = host + "/"+el_adv_id+".js";document.documentElement.firstChild.appendChild(scr);if(oldonload){oldonload()}};}());
</script>
			
</c:if>