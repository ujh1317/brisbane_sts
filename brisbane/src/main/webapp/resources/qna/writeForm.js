

		function check(){

			if($.trim($('#subject').val())==""){
				alert("제목을 입력하세요.");
				$('#subject').focus();
				return;
			}
			if($('#category').val()==""){
				alert("카테고리를 선택하세요.");
				$('#category').focus();
				return;
			}
			if($.trim($('#content').val())==""){
				alert("내용을 입력하세요.");
				$('#content').focus();
				return;
			}
			if($('#bounds').val()==""){
				alert("공개 범위를 선택하세요.");
				$('#bounds').focus();
				return;
			}

			$('#writeForm').submit();
		
		}//check()
	


