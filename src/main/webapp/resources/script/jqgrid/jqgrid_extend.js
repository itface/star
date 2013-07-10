$(function(){
	var tempPostUrlObj={};//用于重写的edit方法中，存储请求的url
	/*
	*重写delete方法，适应restful风格
	*/
	$.extend($.jgrid.del, {
	    mtype: "DELETE",
	    serializeDelData: function () {
	        return ""; // don't send and body for the HTTP DELETE
	    },
	    //处理url，处理成restful风格url
	    onclickSubmit: function (params, postdata) {
	        params.url += '/' + encodeURIComponent(postdata);
	    }
	});
	/*
	*重写edit方法，适应restful风格,并且只重写更新，不用重写新增，因为新增就是post
	*/
	$.extend($.jgrid.edit, {
	    //ajaxEditOptions: { contentType: "application/json" }, // can be not required
	    onclickSubmit: function (params, postdata) {
	    //list_id为postdata对象中的参数，list为table的id,如果是add则postdata['list_id']为_empty,params.url就是配置的url
	    //如果是add，则不处理url
	    	//this.id为grid的id
	    	var dataId = this.id+'_id';
	    	if(postdata[dataId]!='_empty'){
	    		//因为更新时，如果使用formeditor,并且使用前台和后退查看记录时，会导致url重复连接。每一次请求前，先去tempPostUrlObj中看是否有该url，没有则添加。
	    		if(tempPostUrlObj[dataId]!=null&&tempPostUrlObj[dataId]!=''&&tempPostUrlObj[dataId]!=''){
	    			params.url = tempPostUrlObj[dataId]+'/' + encodeURIComponent(postdata[dataId]);
	    		}else{
		    		tempPostUrlObj[dataId]=params.url;
		    		params.url += '/' + encodeURIComponent(postdata[dataId]);
	    		}
	    	}
	    }
	});
	
});