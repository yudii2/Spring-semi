Kakao.init('478e845e80d3b693f2a53821b0866272');
function loginFormWithKakao() {
    Kakao.Auth.loginForm({
             success : function(authObj) {
                Kakao.Auth.login({
                         scope : 'profile_nickname,account_email',
                         success : function(e) {
                            Kakao.API.request({
                                     url : '/v2/user/me',
                                     success : function(res) {
										fetch('/member/kakao-login?userId=' + res.id, {
											method : 'GET',
										}).then(response => {
											if(response.ok){
												return response.text();
											}else{
												throw new Error(response.status);
											}
											
										}).then(text => {
											if(text == 'kakaoLogin'){
												location.href = '/';
											}else if(text == 'kakaoJoin'){	
												location.href = '/member/kakao-join?userId='+res.id;		
											}
										}).catch(error => {
											alert(error + '응답에 실패하였습니다.');
										})
										
	                                        
                                     },fail : function(error) {
                                        alert('login success, but failed to request user information: '
                                              + JSON.stringify(error))
                                     }
                                  })
                         },fail : function(error) {
                            console.dir(error)
                         },

                      })

             },fail : function(err) {
                showResult(JSON.stringify(err))
             },
          })
 }