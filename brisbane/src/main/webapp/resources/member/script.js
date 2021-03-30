function writeSave(){
	//alert("aaa")
	if(document.writeform.writer.value==""){
	  alert("작성자를 입력하십시요.");
	  document.writeform.writer.focus();
	  return false;
	}
	if(document.writeform.subject.value==""){
	  alert("제목을 입력하십시요.");
	  document.writeform.subject.focus();
	  return false;
	}
	
	if(document.writeform.content.value==""){
	  alert("내용을 입력하십시요.");
	  document.writeform.content.focus();
	  return false;
	}
        
	if(document.writeform.pw.value==""){
	  alert(" 비밀번호를 입력하십시요.");
	  document.writeform.pw.focus();
	  return false;
	}
	return true;
	//document.writeform.submit();
 }  
 
 		function mainCheck(){
			if($("#summernote").val==""){
				alert("글내용을 입력하세요.");
				$('#summernote').focus();
				return false;
			}
			return true;
		}  

	function loginCheck(){
		if(document.loginForm.id.value==''){
			alert("id를 입력하시오.");
			document.loginForm.id.focus();
			return false;
		}
		if(document.loginForm.pw.value==''){
			alert("pwd를 입력하시오.");
			document.loginForm.pw.focus();
			return false;
		}
		return true;
	}
	
	      
      
          $(document).ready(function(){
              $('#summernote').summernote({ // summernote를 사용하기 위한 선언
                  height: 300,
               callbacks: { // 콜백을 사용
                      // 이미지를 업로드할 경우 이벤트를 발생
                   onImageUpload: function(files, editor, welEditable) {
                      
                         sendFile(files[0], this);
                      
                   }

               }
            });
         });
      
         
          function sendFile(files, editor) {
              // 파일 전송을 위한 폼생성
             var data = new FormData();
              data.append("uploadFile", files);
              
              $.ajax({ // ajax를 통해 파일 업로드 처리
                  data : data,
                  type : "POST",
                  url : "mainImage.do",
                  cache : false,
                  contentType : false,
                  processData : false,
                  success : function(data) { // 처리가 성공할 경우
                  			
                            $(editor).summernote('editor.insertImage', data.url);

                  }
              });
          }   
	
	
	