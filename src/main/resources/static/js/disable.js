/** 입력값에 따른 버튼 활성화 
 * 
 */
 function Disabled()  {    
  const target = document.getElementById('team_name');
  if(target==''){
	target.disabled = false;
	}else{
		target.disabled=true;	}
  
}