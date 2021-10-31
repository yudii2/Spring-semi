/**
 * 
 */
 
 let urlEncoder = paramObj => {
	
	let arr = [];
	
	for(key in paramObj){	//name=value 형태로 만들기 위함
		let param = key + '=' + encodeURIComponent(paramObj[key]);
		arr.push(param);	//[a=b, 가=나, t=q]
	}
	
	return arr.join('&');	//a=b&가=나&t=q
}