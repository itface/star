function getLen(s) { 
     var c = s.match(/[^\x00-\xff]/ig); 
     return s.length + (c == null ? 0 : c.length);
}
function getPageHeight($){
	if($.browser.msie){ 
		return document.compatMode == "CSS1Compat"? document.documentElement.clientHeight : document.body.clientHeight; 
	}else{ 
		return self.innerHeight; 
	} 
}
function getPageWidth($){
	if($.browser.msie){ 
		return document.compatMode == "CSS1Compat"? document.documentElement.clientWidth : document.body.clientWidth; 
	}else{ 
		return self.innerWidth; 
	} 
}
(function initAjaxOptons(){
	//debugger;
	if($){
		$.ajaxSetup({cache: false });
	}
})();