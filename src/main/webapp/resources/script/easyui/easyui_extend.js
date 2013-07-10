$(function(){
	//easyui,tree添加取半选中状态节点方法
	$.extend($.fn.tree.methods,{
		getCheckedExt: function(jq){
			var checked = new Array();
			var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
			$.each(checkbox2,function(){
				var node = $.extend({}, $.data(this, "tree-node"), {
					target : this
				});
				checked.push(node);
			});
			return checked;
		},
		//easyui,tree添加取兄弟节点方法
		getSolidExt:function(jq){
			var checked =[];
			var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
			$.each(checkbox2,function(){
				var node = $.extend({}, $.data(this, "tree-node"), {
					target : this
				});
				checked.push(node);
			});
			return checked;
		}
	});
});