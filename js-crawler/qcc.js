console.log("qcc爬虫")
const level = 2;
const download = false;
const send = true;

$(function () {


	// $("#start").click(function () {
	setTimeout(() => {
	// 	let data = [{ name: "aaa", "href": "http://www.aaa.top" }, { "name": "bbb", "href": "http://www.bbb.top" }]
	// 	$.ajax({
	// 		type: "post",
	// 		url: "http://localhost:8080/saveData",
	// 		data: JSON.stringify(data),
	// 		// dataType: "json",
	// 		contentType: "application/json",
	// 		success: function (response) {
	// 			console.log(response)
	// 		},
	// 		error: (a, b, c) => {
	// 			console.log(a, b, c)
	// 		}
	// 	});
		start()
		// console.log(getPageData())
		// goToNextPage()
		// getTotalPage();
	}, 1000);

	function getQueryVariable(variable) {
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i = 0; i < vars.length; i++) {
			var pair = vars[i].split("=");
			if (pair[0] == variable) { return pair[1]; }
		}
		return (false);
	}


	function getPageData(parent) {
		if (parent) {
			parent = decodeURI(parent)	
		}
		// let url = window.location.href;
		// // console.log(url)
		let array = new Array();
		// if (url.indexOf("firm") != -1 && url.indexOf(".html") != 0) {
		$(document).find(".touzi-partner-tdcoy").find("a[target='_blank']").css({ "color": "green", "border": "2px solid green" }).each((index, item) => {
			console.log(item.innerText + ":" + item.href);
			let obj = { "name": item.innerText, "href": item.href }
			if (parent) {
				obj["parent"] = parent
			}
			array.push(obj)
		})
		// }
		return array;
	}

	function gotoFirstSearchResult() {
		var url = $(".ntable-list").children(".frtrt").find('.maininfo')
			.children('.title')
			.css({ "color": "green", "border": "2px solid green" })
			.attr('href')

		if (url) {
			sessionStorage.setItem("status", "page")
			window.location.href = url
		}
	}

	function goToNextPage() {
		console.log("go to next page")
		$('nav .pagination')
			.find('li:contains(>) a')[0]
			// .css({ "color": "green", "border": "2px solid green" })
			.click();
	}

	function getTotalPage() {
		var total = $('nav .pagination').find('li:last').prev().children().eq(0).text();
		if (total.indexOf("...") != -1) {
			total = total.substring(3)
		}
		return total;
	}

	function hasNextPage() {
		if ($('nav .pagination')
			.find('li:contains(>) a')[0])
			return true;
		else
			return false;
	}

	function searchResult(searchText) {
		if (searchText) {
			sessionStorage.setItem("status", "result")
			$("#searchkey").val(searchText);
			$(".index-searchbtn").css({ "color": "green", "border": "2px solid green" }).click();
		}
	}

	function getRootSearchText() {
		return "深圳市引导基金投资有限公司";
	}

	const sleep = (milliseconds) => {
		return new Promise(resolve => setTimeout(resolve, milliseconds))
	}

	const start = async () => {
		try {
			let parent = getQueryVariable('parent')
			if (parent) {
				//todo
				console.log("current parent is " + parent)
				let pageData = getPageData(parent);
				let total = getTotalPage();
				for (let i = 0; i < total - 1; i++) {
					console.log("第" + (i + 1) + "页");
					goToNextPage();
					await sleep(500);
					console.log(i);
					// console.log(getPageData());
					// pageData.concat(...getPageData());
					// pageData = Object.assign(pageData, ...getPageData());
					pageData = [...pageData, ...getPageData(parent)];
					console.log(pageData.length);
				}
				downFlie(pageData, parent)
				let status = sessionStorage.getItem("status")
				sessionStorage.setItem("status", status + 1)
				sessionStorage.setItem("list", JSON.stringify(pageData))
				sessionStorage.setItem("parent", parent)
				window.location.href = "https://www.qcc.com";
				return;
			}

			let status = sessionStorage.getItem("status");
			if (!status) {
				sessionStorage.setItem("status", "search");
				window.location.reload()
			} else if (status == 'search') {
				let searchText = getRootSearchText();
				console.log("开始搜索：" + searchText)
				searchResult(searchText);
			} else if (status == 'result') {
				console.log("前往搜索结果第一家")
				gotoFirstSearchResult();
			} else if (status == 'page') {
				console.log("装载data中...")
				let pageData = getPageData(getRootSearchText());
				let total = getTotalPage();
				for (let i = 0; i < total - 1; i++) {
					console.log("第" + (i + 1) + "页");
					goToNextPage();
					await sleep(500);
					console.log(i);
					// console.log(getPageData());
					// pageData.concat(...getPageData());
					// pageData = Object.assign(pageData, ...getPageData());
					pageData = [...pageData, ...getPageData(getRootSearchText())];
					console.log(pageData.length);
				}
				downFlie(pageData, getRootSearchText())
				//todo save root data
				sessionStorage.setItem("status", "1")
				sessionStorage.setItem("list", JSON.stringify(pageData))
				sessionStorage.setItem("parent", getRootSearchText())
				window.location.href = "https://www.qcc.com"
			} else if (status < level) {
				let listStr = sessionStorage.getItem("list");
				let parent = sessionStorage.getItem("parent");
				if (listStr && parent) {
					let array = JSON.parse(listStr);
					for (let i = 0; i < array.length; i++) {
						const item = array[i];
						let win = window.open(item.href + "?parent=" + item.name)
						await sleep(10000);
						win.close()
						console.log(i);
					}
				}

			}
		} catch (error) {
			console.log(error)
			sessionStorage.setItem("status", "");
		}

	}

	function downFlie(data, filename) {
		filename = decodeURI(filename)
		if (send) {
			$.ajax({
				type: "post",
				url: "http://localhost:8080/saveData",
				data: JSON.stringify(data),
				// dataType: "json",
				contentType: "application/json",
				success: function (response) {
					console.log(response)
				},
				error: (a, b, c) => {
					console.log(a, b, c)
				}
			});
		}

		if (!download) {
			return;
		}
		
		// alert(filename)
		console.log(filename)
		// 创建a标签
		var elementA = document.createElement('a');

		//文件的名称为时间戳加文件名后缀
		elementA.download = "+" + filename + ".txt";
		elementA.style.display = 'none';

		//生成一个blob二进制数据，内容为json数据
		var blob = new Blob([JSON.stringify(data)]);

		//生成一个指向blob的URL地址，并赋值给a标签的href属性
		elementA.href = URL.createObjectURL(blob);
		document.body.appendChild(elementA);
		elementA.click();
		document.body.removeChild(elementA);
		// sessionStorage.setItem("status", "");

	}

})