 (() => {
      let confirmId = '';
      document.querySelector('#idcheck').addEventListener('click',function(){
         
         let userId = document.querySelector('#userId').value;
         
         if(!userId){
            document.querySelector('#id-check').innerHTML = '아이디를 입력하지 않았습니다.';
            return;
         }
         
         fetch("/member/id-check?userid="  + userId)
         .then(response =>{
			if(response.ok){
				return response.text()
			}else{
				throw new Error(response.status);
			}
		})
         .then(text => {
            if(text == 'available'){
               confirmId = userId;
               document.querySelector('#id-check').innerHTML = '사용 가능한 아이디 입니다.';
            }else{
               
               document.querySelector('#id-check').innerHTML = '이미 존재하는 아이디 입니다.';
            }
         })
		.catch(error=>{
			 document.querySelector('#id-check').innerHTML = '응답에 실패했습니다. 상태코드 ' + error;
		})
      });

	let confirmNickname = '';
	 document.querySelector('#checkNickname').addEventListener('click',function(){
         
         let nickname = document.querySelector('#nickname').value;
         console.dir(nickname)
         if(!nickname){
            document.querySelector('#nickname2').innerHTML = '닉네임을 입력하지 않았습니다.';
            return;
         }
         
         fetch("/member/check-nickname?nickname="  + nickname)
         .then(response =>{
			if(response.ok){
				return response.text()
			}else{
				throw new Error(response.status);
			}
		})
         .then(text => {
            if(text == 'available'){
               confirmNickname = nickname;
               document.querySelector('#nickname2').innerHTML = '사용 가능한 닉네임 입니다.';
            }else{
               
               document.querySelector('#nickname2').innerHTML = '이미 존재하는 닉네임 입니다.';
            }
         })
		.catch(error=>{
			 document.querySelector('#nickname2').innerHTML = '응답에 실패했습니다. 상태코드 ' + error;
		})
      });

	
      document.querySelector('#frm_join').addEventListener('submit', function(e){
		 let userId = document.querySelector('#userId').value;
         let password = document.querySelector('#password').value;
         let tell = document.querySelector('#tell').value;
          
         let pwReg = /(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9])(?=.{8,})/;
         let tellReg = /^\d{9,11}$/;
         
         if(confirmId != userId){
            document.querySelector('#idCheck').innerHTML = '아이디 중복 검사를 하지 않았습니다.';
            document.querySelector('#userId').focus();
            e.preventDefault();
         }
         
      })

 })();

