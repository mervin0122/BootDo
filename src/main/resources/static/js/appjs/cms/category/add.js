$().ready(function() {
	validateRule();
});

var isshow="1";
$(function() {
	$("#onoffswitch").on('click', function(){
		clickSwitch()
	});
	var clickSwitch = function() {
		if ($("#onoffswitch").is(':checked')) {
			isshow="1";
		} else {
			isshow="0";
		}
	};
});
$.validator.setDefaults({
	submitHandler : function() {
		save(isshow);
	}
});
function save(isshow) {
	/*$("#isshow").val(isshow);*/
	$.ajax({
		cache : true,
		type : "POST",
		url : "/cms/category/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : "required",
			grade: "required"
		},
		messages : {
			title : "请填写名称",
			grade : "请填写级别"
		}
	})
}